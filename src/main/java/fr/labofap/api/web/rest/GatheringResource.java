package fr.labofap.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.labofap.api.domain.User;
import fr.labofap.api.repository.UserRepository;
import fr.labofap.api.security.SecurityUtils;
import fr.labofap.api.service.GatheringService;
import fr.labofap.api.service.MailService;
import fr.labofap.api.service.UserService;
import fr.labofap.api.web.rest.errors.BadRequestAlertException;
import fr.labofap.api.web.rest.util.HeaderUtil;
import fr.labofap.api.service.dto.GatheringDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Gathering.
 */
@RestController
@RequestMapping("/api")
public class GatheringResource {

    private final Logger log = LoggerFactory.getLogger(GatheringResource.class);

    private static final String ENTITY_NAME = "gathering";

    private final GatheringService gatheringService;

    private final MailService mailService;

    private final UserRepository userRepository;


    public GatheringResource(GatheringService gatheringService, MailService mailService, UserRepository userRepository) {
        this.gatheringService = gatheringService;
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /gatherings : Create a new gathering.
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new gatheringDTO, or with status 400 (Bad Request) if the gathering has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gatherings")
    @Timed
    public ResponseEntity<GatheringDTO> createGathering(@RequestBody GatheringDTO gatheringDTO) throws URISyntaxException {
        log.debug("REST request to create a request for Gathering ");

        GatheringDTO result = gatheringService.save(gatheringDTO);
        Optional<User> userFromDatabase = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if(userFromDatabase.isPresent()){
            mailService.sendGatheringRequestMail(userFromDatabase.get());
        }
        return ResponseEntity.created(new URI("/api/gatherings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gatherings : Updates an existing gathering.
     *
     * @param gatheringDTO the gatheringDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gatheringDTO,
     * or with status 400 (Bad Request) if the gatheringDTO is not valid,
     * or with status 500 (Internal Server Error) if the gatheringDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gatherings")
    @Timed
    public ResponseEntity<GatheringDTO> updateGathering(@RequestBody GatheringDTO gatheringDTO) throws URISyntaxException {
        log.debug("REST request to update Gathering : {}", gatheringDTO);
        GatheringDTO result = gatheringService.save(gatheringDTO);

        if(null != result){
            Optional<User> dentisteFromDatabase = userRepository.findOneByLogin(result.getCreatedBy());
            if(dentisteFromDatabase.isPresent()){
                User dentiste = dentisteFromDatabase.get();
                mailService.sendEmail(
                        dentiste.getEmail(),
                        "Demande de ramassage prise en compte",
                        getGatheringMessage(dentiste, result.getCreatedDate().toString()), false, false);
            }
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gatheringDTO.getId().toString()))
            .body(result);
    }

    private String getGatheringMessage(User dentiste, String creationDateAsInstant) {
        StringBuffer sb = new StringBuffer();
        sb.append("Bonjour ").append(dentiste.getFirstName()).append(" ").append(dentiste.getLastName()).append("\n");
        sb.append("Votre demande de ramassage du ").append(creationDateAsInstant).append(" a bien été prise en charge !").append("\n");
        sb.append("Bien Cordialement,").append("\n");
        sb.append("L'équipe LaboFap");

        return sb.toString();
    }

    /**
     * GET  /gatherings : get all the gatherings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gatherings in body
     */
    @GetMapping("/gatherings")
    @Timed
    public List<GatheringDTO> getAllGatherings() {
        log.debug("REST request to get all Gatherings");
        return gatheringService.findAll();
        }

    /**
     * GET  /gatherings/:id : get the "id" gathering.
     *
     * @param id the id of the gatheringDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gatheringDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gatherings/{id}")
    @Timed
    public ResponseEntity<GatheringDTO> getGathering(@PathVariable Long id) {
        log.debug("REST request to get Gathering : {}", id);
        GatheringDTO gatheringDTO = gatheringService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gatheringDTO));
    }

    /**
     * DELETE  /gatherings/:id : delete the "id" gathering.
     *
     * @param id the id of the gatheringDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gatherings/{id}")
    @Timed
    public ResponseEntity<Void> deleteGathering(@PathVariable Long id) {
        log.debug("REST request to delete Gathering : {}", id);
        gatheringService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

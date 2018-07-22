package fr.labofap.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.labofap.api.domain.User;
import fr.labofap.api.domain.enumeration.CommandStatus;
import fr.labofap.api.repository.UserRepository;
import fr.labofap.api.service.CommandService;
import fr.labofap.api.service.CommentService;
import fr.labofap.api.service.MailService;
import fr.labofap.api.service.dto.OrderInputDTO;
import fr.labofap.api.service.dto.CommentDTO;
import fr.labofap.api.service.dto.OrderDTO;
import fr.labofap.api.service.dto.OrderPatchDTO;
import fr.labofap.api.web.rest.util.HeaderUtil;
import fr.labofap.api.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Command.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    private final CommandService commandService;

    private final CommentService commentService;

    private final MailService mailService;

    private final UserRepository userRepository;

    public OrderResource(CommandService commandService, CommentService commentService, MailService mailService, UserRepository userRepository) {
        this.commandService = commandService;
        this.commentService = commentService;
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /commands : Create a new command.
     *
     * @param orderInputDto the orderInputDto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderInputDto, or with status 400 (Bad Request) if the command has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orders")
    @Timed
    public ResponseEntity<OrderDTO> createCommand(@RequestBody OrderInputDTO orderInputDto) throws URISyntaxException {
        log.debug("REST request to save Command : {}", orderInputDto);

        OrderDTO result = commandService.save(orderInputDto);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commands : Updates an existing command.
     *
     * @param orderDTO the commandDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commandDTO,
     * or with status 400 (Bad Request) if the commandDTO is not valid,
     * or with status 500 (Internal Server Error) if the commandDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orders")
    @Timed
    public ResponseEntity<OrderDTO> updateCommand(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to update Command : {}", orderDTO);
        if (orderDTO.getId() == null) {
            OrderInputDTO orderInputDto = new OrderInputDTO();
            orderInputDto.setAmount(orderDTO.getAmount());
            orderInputDto.setStatus(orderDTO.getStatus());
            orderInputDto.setTitle(orderDTO.getTitle());

            return createCommand(orderInputDto);
        }
        OrderDTO result = commandService.save(orderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commands : get all the commands.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commands in body
     */
    @GetMapping("/orders")
    @Timed
    public ResponseEntity<List<OrderDTO>> getAllCommands(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Commands");
        Page<OrderDTO> page = commandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commands/:id : get the "id" command.
     *
     * @param id the id of the commandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commandDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orders/{id}")
    @Timed
    public ResponseEntity<OrderDTO> getCommand(@PathVariable Long id) {
        log.debug("REST request to get Command : {}", id);
        OrderDTO orderDTO = commandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderDTO));
    }

    /**
     * GET  /commands/:id : get the "id" command.
     *
     * @param id the id of the commandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commandDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orders/{id}/comments")
    @Timed
    public ResponseEntity<List<CommentDTO>> getCommentsByCommandId(@PathVariable Long id) {
        log.debug("REST request to get Command : {}", id);
        OrderDTO orderDTO = commandService.findOne(id);
        List<CommentDTO> commentDTOS = commentService.findAll(orderDTO);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentDTOS));
    }

    /**
     * DELETE  /commands/:id : delete the "id" command.
     *
     * @param id the id of the commandDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommand(@PathVariable Long id) {
        log.debug("REST request to delete Command : {}", id);
        commandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * Assign  /commands/:id : to the current user
     *
     * @param orderId the id of the commandDTO to assign to the current user
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/orders/{orderId}/auto-assign")
    @Timed
    public ResponseEntity<Void> autoAssignCommand(@PathVariable Long orderId) {
        log.debug("REST request to auto assign Command : {}", orderId);
        OrderDTO result = commandService.autoAssign(orderId);

        sendOrderStatusMail(result);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderId.toString())).build();
    }

    /**
     * PUT  /commands : Updates an existing command.
     *
     * @param orderPatchDTO the orderPatchDTO data to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commandDTO,
     * or with status 400 (Bad Request) if the commandDTO is not valid,
     * or with status 500 (Internal Server Error) if the commandDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PatchMapping("/orders/{id}")
    @Timed
    public ResponseEntity<OrderDTO> patchCommand(@RequestBody OrderPatchDTO orderPatchDTO, @PathVariable Long id) throws URISyntaxException {

        OrderDTO result = commandService.patch(orderPatchDTO, id);

        sendOrderStatusMail(result);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    private void sendOrderStatusMail(OrderDTO result) {
        Optional<User> dentisteFromDatabase = userRepository.findOneByLogin(result.getCreatedBy());
        if(dentisteFromDatabase.isPresent()){
            User dentiste = dentisteFromDatabase.get();
            mailService.sendEmail(
                    dentiste.getEmail(),
                    "Status de votre commande",
                    getOrderStatusMessage(dentiste, result.getTitle() ,result.getCreatedDate().toString(), result.getStatus()), false, false);
        }
    }

    private String getOrderStatusMessage(User dentiste, String orderDescription, String creationDateAsInstant, CommandStatus commandStatus) {
        StringBuffer sb = new StringBuffer();
        sb.append("Bonjour ").append(dentiste.getFirstName()).append(" ").append(dentiste.getLastName()).append("\n");

        String orderStatusDescription = "";
        switch (commandStatus){
            case IN_PROGRESS:
                orderStatusDescription = "Votre commande : "+orderDescription+" du "+creationDateAsInstant+" est en cours de traitement !";
            case NEED_CLARIFICATION:
                orderStatusDescription = "Votre commande : "+orderDescription+" du "+creationDateAsInstant+" nécessite des clarifications !";
            case PROCCESS_PAYMENT:
                orderStatusDescription = "Votre commande : "+orderDescription+" du "+creationDateAsInstant+" est en attente de paiement !";
            case DONE:
                orderStatusDescription = "Votre commande : "+orderDescription+" du "+creationDateAsInstant+" est prête !";

        }
        sb.append(orderStatusDescription).append("\n");
        sb.append("Bien Cordialement,").append("\n");
        sb.append("L'équipe LaboFap");

        return sb.toString();
    }
}

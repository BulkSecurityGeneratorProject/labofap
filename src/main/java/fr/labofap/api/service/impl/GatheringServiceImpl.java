package fr.labofap.api.service.impl;

import fr.labofap.api.domain.Gathering;
import fr.labofap.api.repository.GatheringRepository;
import fr.labofap.api.service.GatheringService;
import fr.labofap.api.service.dto.GatheringDTO;
import fr.labofap.api.service.mapper.GatheringMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.labofap.api.security.AuthoritiesConstants.DENTISTE;
import static fr.labofap.api.security.SecurityUtils.getCurrentUserLogin;
import static fr.labofap.api.security.SecurityUtils.isCurrentUserInRole;

/**
 * Service Implementation for managing Gathering.
 */
@Service
@Transactional
public class GatheringServiceImpl implements GatheringService {

    private final Logger log = LoggerFactory.getLogger(GatheringServiceImpl.class);

    private final GatheringRepository gatheringRepository;

    private final GatheringMapper gatheringMapper;

    public GatheringServiceImpl(GatheringRepository gatheringRepository, GatheringMapper gatheringMapper) {
        this.gatheringRepository = gatheringRepository;
        this.gatheringMapper = gatheringMapper;
    }

    /**
     * Save a gathering.
     *
     * @param gatheringDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GatheringDTO save(GatheringDTO gatheringDTO) {
        log.debug("Request to save Gathering : {}", gatheringDTO);
        Gathering gathering = gatheringMapper.toEntity(gatheringDTO);
        gathering = gatheringRepository.save(gathering);
        return gatheringMapper.toDto(gathering);
    }

    /**
     * Get all the gatherings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GatheringDTO> findAll() {
        log.debug("Request to get all Gatherings");

        if (isCurrentUserInRole(DENTISTE)) {

            return gatheringRepository.findAllByCreatedBy(getCurrentUserLogin()).stream()
                    .map(gatheringMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));

        } else {

            return gatheringRepository.findAll().stream()
                    .map(gatheringMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }

    }

    /**
     * Get one gathering by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GatheringDTO findOne(Long id) {
        log.debug("Request to get Gathering : {}", id);
        Gathering gathering = gatheringRepository.findOne(id);
        return gatheringMapper.toDto(gathering);
    }

    /**
     * Delete the  gathering by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gathering : {}", id);
        gatheringRepository.delete(id);
    }
}

package fr.labofap.api.service;

import fr.labofap.api.service.dto.GatheringDTO;
import java.util.List;

/**
 * Service Interface for managing Gathering.
 */
public interface GatheringService {

    /**
     * Save a gathering.
     *
     * @param gatheringDTO the entity to save
     * @return the persisted entity
     */
    GatheringDTO save(GatheringDTO gatheringDTO);

    /**
     *  Get all the gatherings.
     *
     *  @return the list of entities
     */
    List<GatheringDTO> findAll();

    /**
     *  Get the "id" gathering.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GatheringDTO findOne(Long id);

    /**
     *  Delete the "id" gathering.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

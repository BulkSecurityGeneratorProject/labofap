package fr.labofap.api.service;

import fr.labofap.api.service.dto.OrderDTO;
import fr.labofap.api.service.dto.OrderInputDTO;
import fr.labofap.api.service.dto.OrderPatchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Command.
 */
public interface CommandService {

    /**
     * Save a command.
     *
     * @param orderDTO the entity to save
     * @return the persisted entity
     */
    OrderDTO save(OrderDTO orderDTO);

    /**
     *  Get all the commands.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" command.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderDTO findOne(Long id);

    /**
     *  Delete the "id" command.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    OrderDTO autoAssign(Long commandId);

    OrderDTO patch(OrderPatchDTO orderPatchDTO, Long id);

    OrderDTO save(OrderInputDTO orderInputDto);
}

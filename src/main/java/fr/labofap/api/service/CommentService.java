package fr.labofap.api.service;

import fr.labofap.api.service.dto.OrderDTO;
import fr.labofap.api.service.dto.CommentDTO;
import fr.labofap.api.service.dto.CommentInputDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Comment.
 */
public interface CommentService {

    /**
     * Save a comment.
     *
     * @param commentInputDTO the entity to save
     * @return the persisted entity
     */
    CommentDTO save(CommentInputDTO commentInputDTO);

    /**
     *  Get all the comments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CommentDTO> findAll(Pageable pageable);

    List<CommentDTO> findAll(OrderDTO orderDTO);

    /**
     *  Get the "id" comment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CommentDTO findOne(Long id);

    /**
     *  Delete the "id" comment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    CommentDTO save(CommentDTO commentDTO);
}

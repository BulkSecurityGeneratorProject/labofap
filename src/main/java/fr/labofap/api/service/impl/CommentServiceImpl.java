package fr.labofap.api.service.impl;

import fr.labofap.api.service.CommentService;
import fr.labofap.api.domain.Comment;
import fr.labofap.api.repository.CommentRepository;
import fr.labofap.api.service.dto.OrderDTO;
import fr.labofap.api.service.dto.CommentDTO;
import fr.labofap.api.service.dto.CommentInputDTO;
import fr.labofap.api.service.mapper.CommandMapper;
import fr.labofap.api.service.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Comment.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final CommandMapper commandMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, CommandMapper commandMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.commandMapper = commandMapper;
    }

    /**
     * Save a comment.
     *
     * @param commentInputDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommentDTO save(CommentInputDTO commentInputDTO) {
        log.debug("Request to save Comment : {}", commentInputDTO);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setOrderId(commentInputDTO.getOrderId());
        commentDTO.setContent(commentInputDTO.getContent());

        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     *  Get all the comments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentRepository.findAll(pageable)
            .map(commentMapper::toDto);
    }

    @Override
    public List<CommentDTO> findAll(OrderDTO orderDTO) {

        return commentMapper.toDto(commentRepository.findByCommand(commandMapper.toEntity(orderDTO)));
    }

    /**
     *  Get one comment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CommentDTO findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        Comment comment = commentRepository.findOne(id);
        return commentMapper.toDto(comment);
    }

    /**
     *  Delete the  comment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.delete(id);
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        log.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
}

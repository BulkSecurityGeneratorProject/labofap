package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.*;
import fr.labofap.api.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    CommentDTO toDto(Comment comment);

    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}

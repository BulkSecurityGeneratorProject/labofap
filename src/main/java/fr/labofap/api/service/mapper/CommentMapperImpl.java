package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.Command;

import fr.labofap.api.domain.Comment;

import fr.labofap.api.service.dto.CommentDTO;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


@Component
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private CommandMapper commandMapper;

    @Override

    public List<Comment> toEntity(List<CommentDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Comment> list = new ArrayList<Comment>();

        for ( CommentDTO commentDTO : dtoList ) {

            list.add( toEntity( commentDTO ) );
        }

        return list;
    }

    @Override

    public List<CommentDTO> toDto(List<Comment> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>();

        for ( Comment comment : entityList ) {

            list.add( toDto( comment ) );
        }

        return list;
    }

    @Override

    public CommentDTO toDto(Comment comment) {

        if ( comment == null ) {

            return null;
        }

        CommentDTO commentDTO_ = new CommentDTO();

        commentDTO_.setOrderId( commentCommandId( comment ) );

        commentDTO_.setId( comment.getId() );

        commentDTO_.setContent( comment.getContent() );

        // for Audit
        commentDTO_.setCreatedBy(comment.getCreatedBy());
        commentDTO_.setCreatedDate(comment.getCreatedDate());
        commentDTO_.setLastModifiedBy(comment.getLastModifiedBy());
        commentDTO_.setLastModifiedDate(comment.getLastModifiedDate());

        return commentDTO_;
    }

    @Override

    public Comment toEntity(CommentDTO commentDTO) {

        if ( commentDTO == null ) {

            return null;
        }

        Comment comment_ = new Comment();

        comment_.setCommand( commandMapper.fromId( commentDTO.getOrderId() ) );

        comment_.setId( commentDTO.getId() );

        comment_.setContent( commentDTO.getContent() );

        return comment_;
    }

    private Long commentCommandId(Comment comment) {

        if ( comment == null ) {

            return null;
        }

        Command command = comment.getCommand();

        if ( command == null ) {

            return null;
        }

        Long id = command.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


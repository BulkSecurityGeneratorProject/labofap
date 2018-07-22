package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.Comment;

import fr.labofap.api.domain.Media;

import fr.labofap.api.service.dto.MediaDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class MediaMapperImpl implements MediaMapper {

    @Autowired

    private CommentMapper commentMapper;

    @Override

    public List<Media> toEntity(List<MediaDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Media> list = new ArrayList<Media>();

        for ( MediaDTO mediaDTO : dtoList ) {

            list.add( toEntity( mediaDTO ) );
        }

        return list;
    }

    @Override

    public List<MediaDTO> toDto(List<Media> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<MediaDTO> list = new ArrayList<MediaDTO>();

        for ( Media media : entityList ) {

            list.add( toDto( media ) );
        }

        return list;
    }

    @Override

    public MediaDTO toDto(Media media) {

        if ( media == null ) {

            return null;
        }

        MediaDTO mediaDTO_ = new MediaDTO();

        mediaDTO_.setCommentId( mediaCommentId( media ) );

        mediaDTO_.setId( media.getId() );

        mediaDTO_.setTitle( media.getTitle() );

        mediaDTO_.setMimeType( media.getMimeType() );

        mediaDTO_.setContent( media.getContent() );

        return mediaDTO_;
    }

    @Override

    public Media toEntity(MediaDTO mediaDTO) {

        if ( mediaDTO == null ) {

            return null;
        }

        Media media_ = new Media();

        media_.setComment( commentMapper.fromId( mediaDTO.getCommentId() ) );

        media_.setId( mediaDTO.getId() );

        media_.setTitle( mediaDTO.getTitle() );

        media_.setMimeType( mediaDTO.getMimeType() );

        media_.setContent( mediaDTO.getContent() );

        return media_;
    }

    private Long mediaCommentId(Media media) {

        if ( media == null ) {

            return null;
        }

        Comment comment = media.getComment();

        if ( comment == null ) {

            return null;
        }

        Long id = comment.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.*;
import fr.labofap.api.service.dto.MediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Media and its DTO MediaDTO.
 */
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {

    MediaDTO toDto(Media media);

    Media toEntity(MediaDTO mediaDTO);

    default Media fromId(Long id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
}

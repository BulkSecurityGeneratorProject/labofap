package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.*;
import fr.labofap.api.service.dto.GatheringDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gathering and its DTO GatheringDTO.
 */
public interface GatheringMapper extends EntityMapper<GatheringDTO, Gathering> {





    default Gathering fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gathering gathering = new Gathering();
        gathering.setId(id);
        return gathering;
    }
}

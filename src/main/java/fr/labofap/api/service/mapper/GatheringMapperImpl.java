package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.Gathering;

import fr.labofap.api.service.dto.GatheringDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Component
public class GatheringMapperImpl implements GatheringMapper {

    @Override

    public Gathering toEntity(GatheringDTO dto) {

        if ( dto == null ) {

            return null;
        }

        Gathering gathering = new Gathering();

        gathering.setId( dto.getId() );

        gathering.setNote( dto.getNote() );

        return gathering;
    }

    @Override

    public GatheringDTO toDto(Gathering entity) {

        if ( entity == null ) {

            return null;
        }

        GatheringDTO gatheringDTO = new GatheringDTO();

        gatheringDTO.setId( entity.getId() );

        gatheringDTO.setNote( entity.getNote() );

        // for Audit
        gatheringDTO.setCreatedBy(entity.getCreatedBy());
        gatheringDTO.setCreatedDate(entity.getCreatedDate());
        gatheringDTO.setLastModifiedBy(entity.getLastModifiedBy());
        gatheringDTO.setLastModifiedDate(entity.getLastModifiedDate());

        return gatheringDTO;
    }

    @Override

    public List<Gathering> toEntity(List<GatheringDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Gathering> list = new ArrayList<Gathering>();

        for ( GatheringDTO gatheringDTO : dtoList ) {

            list.add( toEntity( gatheringDTO ) );
        }

        return list;
    }

    @Override

    public List<GatheringDTO> toDto(List<Gathering> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<GatheringDTO> list = new ArrayList<GatheringDTO>();

        for ( Gathering gathering : entityList ) {

            list.add( toDto( gathering ) );
        }

        return list;
    }
}


package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.Command;

import fr.labofap.api.service.dto.OrderDTO;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class CommandMapperImpl implements CommandMapper {

    @Override

    public OrderDTO toDto(Command entity) {

        if ( entity == null ) {

            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( entity.getId() );

        orderDTO.setTitle( entity.getTitle() );

        orderDTO.setStatus( entity.getStatus() );

        orderDTO.setAmount(entity.getAmount());

        // for Audit
        orderDTO.setCreatedBy(entity.getCreatedBy());
        orderDTO.setCreatedDate(entity.getCreatedDate());
        orderDTO.setLastModifiedBy(entity.getLastModifiedBy());
        orderDTO.setLastModifiedDate(entity.getLastModifiedDate());

        return orderDTO;
    }

    @Override

    public List<Command> toEntity(List<OrderDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Command> list = new ArrayList<Command>();

        for ( OrderDTO orderDTO : dtoList ) {

            list.add( toEntity(orderDTO) );
        }

        return list;
    }

    @Override

    public List<OrderDTO> toDto(List<Command> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>();

        for ( Command command : entityList ) {

            list.add( toDto( command ) );
        }

        return list;
    }

    @Override

    public Command toEntity(OrderDTO orderDTO) {

        if ( orderDTO == null ) {

            return null;
        }

        Command command_ = new Command();

        command_.setId( orderDTO.getId() );

        command_.setTitle( orderDTO.getTitle() );

        command_.setStatus( orderDTO.getStatus() );

        return command_;
    }
}


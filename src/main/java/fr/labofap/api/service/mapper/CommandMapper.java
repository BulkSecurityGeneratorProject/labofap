package fr.labofap.api.service.mapper;

import fr.labofap.api.domain.*;
import fr.labofap.api.service.dto.OrderDTO;

/**
 * Mapper for the entity Command and its DTO OrderDTO.
 */
public interface CommandMapper extends EntityMapper<OrderDTO, Command> {



    Command toEntity(OrderDTO orderDTO);

    default Command fromId(Long id) {
        if (id == null) {
            return null;
        }
        Command command = new Command();
        command.setId(id);
        return command;
    }
}

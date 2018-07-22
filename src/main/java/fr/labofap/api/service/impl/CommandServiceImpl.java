package fr.labofap.api.service.impl;

import fr.labofap.api.domain.Command;
import fr.labofap.api.repository.CommandRepository;
import fr.labofap.api.service.CommandService;
import fr.labofap.api.service.dto.OrderDTO;
import fr.labofap.api.service.dto.OrderInputDTO;
import fr.labofap.api.service.dto.OrderPatchDTO;
import fr.labofap.api.service.mapper.CommandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static fr.labofap.api.domain.enumeration.CommandStatus.IN_PROGRESS;
import static fr.labofap.api.security.AuthoritiesConstants.DENTISTE;
import static fr.labofap.api.security.AuthoritiesConstants.PROTHESISTE;
import static fr.labofap.api.security.SecurityUtils.getCurrentUserLogin;
import static fr.labofap.api.security.SecurityUtils.isCurrentUserInRole;


/**
 * Service Implementation for managing Command.
 */
@Service
@Transactional
public class CommandServiceImpl implements CommandService {

    private final Logger log = LoggerFactory.getLogger(CommandServiceImpl.class);

    private final CommandRepository commandRepository;

    private final CommandMapper commandMapper;

    public CommandServiceImpl(CommandRepository commandRepository, CommandMapper commandMapper) {
        this.commandRepository = commandRepository;
        this.commandMapper = commandMapper;
    }

    /**
     * Save a command.
     *
     * @param orderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Command : {}", orderDTO);
        Command command = commandMapper.toEntity(orderDTO);
        command = commandRepository.save(command);
        return commandMapper.toDto(command);
    }

    /**
     * Get all the commands.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commands");

        if (isCurrentUserInRole(DENTISTE)) {

            return commandRepository.findAllByCreatedBy(getCurrentUserLogin(), pageable)
                .map(commandMapper::toDto);

        } else if (isCurrentUserInRole(PROTHESISTE)) {

            return commandRepository.findAllByAssignedToOrAssignedToIsNull(getCurrentUserLogin(), pageable)
                .map(commandMapper::toDto);

        } else {

            return commandRepository.findAll(pageable)
                .map(commandMapper::toDto);
        }
    }

    /**
     * Get one command by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrderDTO findOne(Long id) {
        log.debug("Request to get Command : {}", id);
        Command command = commandRepository.findOne(id);
        return commandMapper.toDto(command);
    }

    /**
     * Delete the  command by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Command : {}", id);
        commandRepository.delete(id);
    }

    @Override
    public OrderDTO autoAssign(Long commandId) {
        //commandRepository.setAssignedTo(getCurrentUserLogin(),commandId);
        Command command = commandRepository.findOne(commandId);
        command.setAssignedTo(getCurrentUserLogin());
        command.setStatus(IN_PROGRESS);

        commandRepository.save(command);

        return commandMapper.toDto(command);
    }

    @Override
    public OrderDTO patch(OrderPatchDTO orderPatchDTO, Long id) {

        Command command = commandRepository.findOne(id);
        if(command == null){
            throw new OrderNotFoundException();
        }
        if (null != orderPatchDTO.getStatus()){
            command.setStatus(orderPatchDTO.getStatus());
        }
        if (null != orderPatchDTO.getAmount()){
            command.setAmount(orderPatchDTO.getAmount());
        }

        command = commandRepository.save(command);
        return commandMapper.toDto(command);
    }

    @Override
    public OrderDTO save(OrderInputDTO orderInputDto) {
        log.debug("Request to save Command : {}", orderInputDto);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAmount(orderInputDto.getAmount());
        orderDTO.setStatus(orderInputDto.getStatus());
        orderDTO.setTitle(orderInputDto.getTitle());

        Command command = commandMapper.toEntity(orderDTO);
        command = commandRepository.save(command);
        return commandMapper.toDto(command);
    }
}

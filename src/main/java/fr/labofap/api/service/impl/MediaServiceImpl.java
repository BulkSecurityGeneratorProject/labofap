package fr.labofap.api.service.impl;

import fr.labofap.api.service.MediaService;
import fr.labofap.api.domain.Media;
import fr.labofap.api.repository.MediaRepository;
import fr.labofap.api.service.dto.CommentDTO;
import fr.labofap.api.service.dto.MediaDTO;
import fr.labofap.api.service.mapper.CommentMapper;
import fr.labofap.api.service.mapper.MediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Media.
 */
@Service
@Transactional
public class MediaServiceImpl implements MediaService{

    private final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    private final CommentMapper commentMapper;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper, CommentMapper commentMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.commentMapper = commentMapper;
    }

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MediaDTO save(MediaDTO mediaDTO) {
        log.debug("Request to save Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        return mediaMapper.toDto(media);
    }

    /**
     *  Get all the media.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MediaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Media");
        return mediaRepository.findAll(pageable)
            .map(mediaMapper::toDto);
    }

    /**
     *  Get one media by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MediaDTO findOne(Long id) {
        log.debug("Request to get Media : {}", id);
        Media media = mediaRepository.findOne(id);
        return mediaMapper.toDto(media);
    }

    /**
     *  Delete the  media by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Media : {}", id);
        mediaRepository.delete(id);
    }

    @Override
    public List<MediaDTO> findAll(CommentDTO commentDTO) {

        return mediaMapper.toDto(mediaRepository.findByComment(commentMapper.toEntity(commentDTO)));
    }
}

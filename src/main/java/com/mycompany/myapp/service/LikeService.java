package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Like;
import com.mycompany.myapp.repository.LikeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Like}.
 */
@Service
@Transactional
public class LikeService {

    private final Logger log = LoggerFactory.getLogger(LikeService.class);

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * Save a like.
     *
     * @param like the entity to save.
     * @return the persisted entity.
     */
    public Like save(Like like) {
        log.debug("Request to save Like : {}", like);
        return likeRepository.save(like);
    }

    /**
     * Update a like.
     *
     * @param like the entity to save.
     * @return the persisted entity.
     */
    public Like update(Like like) {
        log.debug("Request to update Like : {}", like);
        return likeRepository.save(like);
    }

    /**
     * Partially update a like.
     *
     * @param like the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Like> partialUpdate(Like like) {
        log.debug("Request to partially update Like : {}", like);

        return likeRepository.findById(like.getId()).map(likeRepository::save);
    }

    /**
     * Get all the likes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Like> findAll() {
        log.debug("Request to get all Likes");
        return likeRepository.findAll();
    }

    /**
     * Get all the likes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Like> findAllWithEagerRelationships(Pageable pageable) {
        return likeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one like by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Like> findOne(Long id) {
        log.debug("Request to get Like : {}", id);
        return likeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the like by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Like : {}", id);
        likeRepository.deleteById(id);
    }
}

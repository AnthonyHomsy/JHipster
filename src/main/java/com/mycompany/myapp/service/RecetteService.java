package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Recette;
import com.mycompany.myapp.repository.RecetteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Recette}.
 */
@Service
@Transactional
public class RecetteService {

    private final Logger log = LoggerFactory.getLogger(RecetteService.class);

    private final RecetteRepository recetteRepository;

    public RecetteService(RecetteRepository recetteRepository) {
        this.recetteRepository = recetteRepository;
    }

    /**
     * Save a recette.
     *
     * @param recette the entity to save.
     * @return the persisted entity.
     */
    public Recette save(Recette recette) {
        log.debug("Request to save Recette : {}", recette);
        return recetteRepository.save(recette);
    }

    /**
     * Update a recette.
     *
     * @param recette the entity to save.
     * @return the persisted entity.
     */
    public Recette update(Recette recette) {
        log.debug("Request to update Recette : {}", recette);
        return recetteRepository.save(recette);
    }

    /**
     * Partially update a recette.
     *
     * @param recette the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Recette> partialUpdate(Recette recette) {
        log.debug("Request to partially update Recette : {}", recette);

        return recetteRepository
            .findById(recette.getId())
            .map(existingRecette -> {
                if (recette.getTitle() != null) {
                    existingRecette.setTitle(recette.getTitle());
                }
                if (recette.getDescription() != null) {
                    existingRecette.setDescription(recette.getDescription());
                }

                return existingRecette;
            })
            .map(recetteRepository::save);
    }

    /**
     * Get all the recettes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Recette> findAll() {
        log.debug("Request to get all Recettes");
        return recetteRepository.findAll();
    }

    /**
     * Get all the recettes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Recette> findAllWithEagerRelationships(Pageable pageable) {
        return recetteRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one recette by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Recette> findOne(Long id) {
        log.debug("Request to get Recette : {}", id);
        return recetteRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the recette by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recette : {}", id);
        recetteRepository.deleteById(id);
    }
}

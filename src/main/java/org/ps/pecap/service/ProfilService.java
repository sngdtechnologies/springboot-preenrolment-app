package org.ps.pecap.service;

import java.util.Optional;
import org.ps.pecap.domain.Profil;
import org.ps.pecap.repository.ProfilRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Profil}.
 */
@Service
@Transactional
public class ProfilService {

    private final Logger log = LoggerFactory.getLogger(ProfilService.class);

    private final ProfilRepository profilRepository;

    public ProfilService(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    /**
     * Save a profil.
     *
     * @param profil the entity to save.
     * @return the persisted entity.
     */
    public Profil save(Profil profil) {
        log.debug("Request to save Profil : {}", profil);
        return profilRepository.save(profil);
    }

    /**
     * Update a profil.
     *
     * @param profil the entity to save.
     * @return the persisted entity.
     */
    public Profil update(Profil profil) {
        log.debug("Request to update Profil : {}", profil);
        return profilRepository.save(profil);
    }

    /**
     * Partially update a profil.
     *
     * @param profil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Profil> partialUpdate(Profil profil) {
        log.debug("Request to partially update Profil : {}", profil);

        return profilRepository
            .findById(profil.getId())
            .map(existingProfil -> {
                if (profil.getNom() != null) {
                    existingProfil.setNom(profil.getNom());
                }

                return existingProfil;
            })
            .map(profilRepository::save);
    }

    /**
     * Get all the profils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Profil> findAll(Pageable pageable) {
        log.debug("Request to get all Profils");
        return profilRepository.findAll(pageable);
    }

    /**
     * Get all the profils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Profil> findAllWithEagerRelationships(Pageable pageable) {
        return profilRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one profil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Profil> findOne(Long id) {
        log.debug("Request to get Profil : {}", id);
        return profilRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the profil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profil : {}", id);
        profilRepository.deleteById(id);
    }
}

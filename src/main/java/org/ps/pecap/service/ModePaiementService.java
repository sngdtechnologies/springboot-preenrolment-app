package org.ps.pecap.service;

import java.util.Optional;
import org.ps.pecap.domain.ModePaiement;
import org.ps.pecap.repository.ModePaiementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ModePaiement}.
 */
@Service
@Transactional
public class ModePaiementService {

    private final Logger log = LoggerFactory.getLogger(ModePaiementService.class);

    private final ModePaiementRepository modePaiementRepository;

    public ModePaiementService(ModePaiementRepository modePaiementRepository) {
        this.modePaiementRepository = modePaiementRepository;
    }

    /**
     * Save a modePaiement.
     *
     * @param modePaiement the entity to save.
     * @return the persisted entity.
     */
    public ModePaiement save(ModePaiement modePaiement) {
        log.debug("Request to save ModePaiement : {}", modePaiement);
        return modePaiementRepository.save(modePaiement);
    }

    /**
     * Update a modePaiement.
     *
     * @param modePaiement the entity to save.
     * @return the persisted entity.
     */
    public ModePaiement update(ModePaiement modePaiement) {
        log.debug("Request to update ModePaiement : {}", modePaiement);
        return modePaiementRepository.save(modePaiement);
    }

    /**
     * Partially update a modePaiement.
     *
     * @param modePaiement the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ModePaiement> partialUpdate(ModePaiement modePaiement) {
        log.debug("Request to partially update ModePaiement : {}", modePaiement);

        return modePaiementRepository
            .findById(modePaiement.getId())
            .map(existingModePaiement -> {
                if (modePaiement.getNom() != null) {
                    existingModePaiement.setNom(modePaiement.getNom());
                }

                return existingModePaiement;
            })
            .map(modePaiementRepository::save);
    }

    /**
     * Get all the modePaiements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModePaiement> findAll(Pageable pageable) {
        log.debug("Request to get all ModePaiements");
        return modePaiementRepository.findAll(pageable);
    }

    /**
     * Get one modePaiement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModePaiement> findOne(Long id) {
        log.debug("Request to get ModePaiement : {}", id);
        return modePaiementRepository.findById(id);
    }

    /**
     * Delete the modePaiement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ModePaiement : {}", id);
        modePaiementRepository.deleteById(id);
    }
}

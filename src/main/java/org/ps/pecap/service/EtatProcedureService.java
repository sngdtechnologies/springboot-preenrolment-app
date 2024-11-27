package org.ps.pecap.service;

import java.util.Optional;
import org.ps.pecap.domain.EtatProcedure;
import org.ps.pecap.repository.EtatProcedureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EtatProcedure}.
 */
@Service
@Transactional
public class EtatProcedureService {

    private final Logger log = LoggerFactory.getLogger(EtatProcedureService.class);

    private final EtatProcedureRepository etatProcedureRepository;

    public EtatProcedureService(EtatProcedureRepository etatProcedureRepository) {
        this.etatProcedureRepository = etatProcedureRepository;
    }

    /**
     * Save a etatProcedure.
     *
     * @param etatProcedure the entity to save.
     * @return the persisted entity.
     */
    public EtatProcedure save(EtatProcedure etatProcedure) {
        log.debug("Request to save EtatProcedure : {}", etatProcedure);
        return etatProcedureRepository.save(etatProcedure);
    }

    /**
     * Update a etatProcedure.
     *
     * @param etatProcedure the entity to save.
     * @return the persisted entity.
     */
    public EtatProcedure update(EtatProcedure etatProcedure) {
        log.debug("Request to update EtatProcedure : {}", etatProcedure);
        return etatProcedureRepository.save(etatProcedure);
    }

    /**
     * Partially update a etatProcedure.
     *
     * @param etatProcedure the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EtatProcedure> partialUpdate(EtatProcedure etatProcedure) {
        log.debug("Request to partially update EtatProcedure : {}", etatProcedure);

        return etatProcedureRepository
            .findById(etatProcedure.getId())
            .map(existingEtatProcedure -> {
                if (etatProcedure.getEtatPreEnrole() != null) {
                    existingEtatProcedure.setEtatPreEnrole(etatProcedure.getEtatPreEnrole());
                }
                if (etatProcedure.getEtatEnrole() != null) {
                    existingEtatProcedure.setEtatEnrole(etatProcedure.getEtatEnrole());
                }
                if (etatProcedure.getEtatRetrait() != null) {
                    existingEtatProcedure.setEtatRetrait(etatProcedure.getEtatRetrait());
                }

                return existingEtatProcedure;
            })
            .map(etatProcedureRepository::save);
    }

    /**
     * Get all the etatProcedures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtatProcedure> findAll(Pageable pageable) {
        log.debug("Request to get all EtatProcedures");
        return etatProcedureRepository.findAll(pageable);
    }

    /**
     * Get one etatProcedure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtatProcedure> findOne(Long id) {
        log.debug("Request to get EtatProcedure : {}", id);
        return etatProcedureRepository.findById(id);
    }

    /**
     * Delete the etatProcedure by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EtatProcedure : {}", id);
        etatProcedureRepository.deleteById(id);
    }
}

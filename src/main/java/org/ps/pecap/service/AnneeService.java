package org.ps.pecap.service;

import java.util.Optional;
import org.ps.pecap.domain.Annee;
import org.ps.pecap.repository.AnneeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Annee}.
 */
@Service
@Transactional
public class AnneeService {

    private final Logger log = LoggerFactory.getLogger(AnneeService.class);

    private final AnneeRepository anneeRepository;

    public AnneeService(AnneeRepository anneeRepository) {
        this.anneeRepository = anneeRepository;
    }

    /**
     * Save a annee.
     *
     * @param annee the entity to save.
     * @return the persisted entity.
     */
    public Annee save(Annee annee) {
        log.debug("Request to save Annee : {}", annee);
        return anneeRepository.save(annee);
    }

    /**
     * Update a annee.
     *
     * @param annee the entity to save.
     * @return the persisted entity.
     */
    public Annee update(Annee annee) {
        log.debug("Request to update Annee : {}", annee);
        return anneeRepository.save(annee);
    }

    /**
     * Partially update a annee.
     *
     * @param annee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Annee> partialUpdate(Annee annee) {
        log.debug("Request to partially update Annee : {}", annee);

        return anneeRepository
            .findById(annee.getId())
            .map(existingAnnee -> {
                if (annee.getNom() != null) {
                    existingAnnee.setNom(annee.getNom());
                }
                if (annee.getDateDebut() != null) {
                    existingAnnee.setDateDebut(annee.getDateDebut());
                }
                if (annee.getDateFin() != null) {
                    existingAnnee.setDateFin(annee.getDateFin());
                }
                if (annee.getIsVerrouiller() != null) {
                    existingAnnee.setIsVerrouiller(annee.getIsVerrouiller());
                }
                if (annee.getIsCloturer() != null) {
                    existingAnnee.setIsCloturer(annee.getIsCloturer());
                }

                return existingAnnee;
            })
            .map(anneeRepository::save);
    }

    /**
     * Get all the annees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Annee> findAll(Pageable pageable) {
        log.debug("Request to get all Annees");
        return anneeRepository.findAll(pageable);
    }

    /**
     * Get one annee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Annee> findOne(Long id) {
        log.debug("Request to get Annee : {}", id);
        return anneeRepository.findById(id);
    }

    /**
     * Delete the annee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Annee : {}", id);
        anneeRepository.deleteById(id);
    }
}

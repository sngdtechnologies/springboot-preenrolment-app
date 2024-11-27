package org.ps.pecap.service;

import java.util.Optional;
import org.ps.pecap.domain.Acces;
import org.ps.pecap.repository.AccesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Acces}.
 */
@Service
@Transactional
public class AccesService {

    private final Logger log = LoggerFactory.getLogger(AccesService.class);

    private final AccesRepository accesRepository;

    public AccesService(AccesRepository accesRepository) {
        this.accesRepository = accesRepository;
    }

    /**
     * Save a acces.
     *
     * @param acces the entity to save.
     * @return the persisted entity.
     */
    public Acces save(Acces acces) {
        log.debug("Request to save Acces : {}", acces);
        return accesRepository.save(acces);
    }

    /**
     * Update a acces.
     *
     * @param acces the entity to save.
     * @return the persisted entity.
     */
    public Acces update(Acces acces) {
        log.debug("Request to update Acces : {}", acces);
        return accesRepository.save(acces);
    }

    /**
     * Partially update a acces.
     *
     * @param acces the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Acces> partialUpdate(Acces acces) {
        log.debug("Request to partially update Acces : {}", acces);

        return accesRepository
            .findById(acces.getId())
            .map(existingAcces -> {
                if (acces.getNom() != null) {
                    existingAcces.setNom(acces.getNom());
                }
                if (acces.getCode() != null) {
                    existingAcces.setCode(acces.getCode());
                }

                return existingAcces;
            })
            .map(accesRepository::save);
    }

    /**
     * Get all the acces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Acces> findAll(Pageable pageable) {
        log.debug("Request to get all Acces");
        return accesRepository.findAll(pageable);
    }

    /**
     * Get one acces by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Acces> findOne(Long id) {
        log.debug("Request to get Acces : {}", id);
        return accesRepository.findById(id);
    }

    /**
     * Delete the acces by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Acces : {}", id);
        accesRepository.deleteById(id);
    }
}

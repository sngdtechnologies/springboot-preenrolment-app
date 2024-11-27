package org.ps.pecap.service;

import java.util.Optional;
import org.ps.pecap.domain.TypePassport;
import org.ps.pecap.repository.TypePassportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypePassport}.
 */
@Service
@Transactional
public class TypePassportService {

    private final Logger log = LoggerFactory.getLogger(TypePassportService.class);

    private final TypePassportRepository typePassportRepository;

    public TypePassportService(TypePassportRepository typePassportRepository) {
        this.typePassportRepository = typePassportRepository;
    }

    /**
     * Save a typePassport.
     *
     * @param typePassport the entity to save.
     * @return the persisted entity.
     */
    public TypePassport save(TypePassport typePassport) {
        log.debug("Request to save TypePassport : {}", typePassport);
        return typePassportRepository.save(typePassport);
    }

    /**
     * Update a typePassport.
     *
     * @param typePassport the entity to save.
     * @return the persisted entity.
     */
    public TypePassport update(TypePassport typePassport) {
        log.debug("Request to update TypePassport : {}", typePassport);
        return typePassportRepository.save(typePassport);
    }

    /**
     * Partially update a typePassport.
     *
     * @param typePassport the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypePassport> partialUpdate(TypePassport typePassport) {
        log.debug("Request to partially update TypePassport : {}", typePassport);

        return typePassportRepository
            .findById(typePassport.getId())
            .map(existingTypePassport -> {
                if (typePassport.getNom() != null) {
                    existingTypePassport.setNom(typePassport.getNom());
                }

                return existingTypePassport;
            })
            .map(typePassportRepository::save);
    }

    /**
     * Get all the typePassports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypePassport> findAll(Pageable pageable) {
        log.debug("Request to get all TypePassports");
        return typePassportRepository.findAll(pageable);
    }

    /**
     * Get one typePassport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypePassport> findOne(Long id) {
        log.debug("Request to get TypePassport : {}", id);
        return typePassportRepository.findById(id);
    }

    /**
     * Delete the typePassport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePassport : {}", id);
        typePassportRepository.deleteById(id);
    }
}

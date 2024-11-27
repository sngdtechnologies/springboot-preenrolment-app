package org.ps.pecap.service;

import java.util.List;
import java.util.Optional;
import org.ps.pecap.domain.LogSystem;
import org.ps.pecap.repository.LogSystemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LogSystem}.
 */
@Service
@Transactional
public class LogSystemService {

    private final Logger log = LoggerFactory.getLogger(LogSystemService.class);

    private final LogSystemRepository logSystemRepository;

    public LogSystemService(LogSystemRepository logSystemRepository) {
        this.logSystemRepository = logSystemRepository;
    }

    /**
     * Save a logSystem.
     *
     * @param logSystem the entity to save.
     * @return the persisted entity.
     */
    public LogSystem save(LogSystem logSystem) {
        log.debug("Request to save LogSystem : {}", logSystem);
        return logSystemRepository.save(logSystem);
    }

    /**
     * Update a logSystem.
     *
     * @param logSystem the entity to save.
     * @return the persisted entity.
     */
    public LogSystem update(LogSystem logSystem) {
        log.debug("Request to update LogSystem : {}", logSystem);
        return logSystemRepository.save(logSystem);
    }

    /**
     * Partially update a logSystem.
     *
     * @param logSystem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LogSystem> partialUpdate(LogSystem logSystem) {
        log.debug("Request to partially update LogSystem : {}", logSystem);

        return logSystemRepository
            .findById(logSystem.getId())
            .map(existingLogSystem -> {
                if (logSystem.getEventDate() != null) {
                    existingLogSystem.setEventDate(logSystem.getEventDate());
                }
                if (logSystem.getLogin() != null) {
                    existingLogSystem.setLogin(logSystem.getLogin());
                }
                if (logSystem.getMessage() != null) {
                    existingLogSystem.setMessage(logSystem.getMessage());
                }

                return existingLogSystem;
            })
            .map(logSystemRepository::save);
    }

    /**
     * Get all the logSystems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LogSystem> findAll() {
        log.debug("Request to get all LogSystems");
        return logSystemRepository.findAll();
    }

    /**
     * Get one logSystem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LogSystem> findOne(Long id) {
        log.debug("Request to get LogSystem : {}", id);
        return logSystemRepository.findById(id);
    }

    /**
     * Delete the logSystem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LogSystem : {}", id);
        logSystemRepository.deleteById(id);
    }
}

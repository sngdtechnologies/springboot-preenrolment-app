package org.ps.pecap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.ps.pecap.domain.LogSystem;
import org.ps.pecap.repository.LogSystemRepository;
import org.ps.pecap.service.LogSystemService;
import org.ps.pecap.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.ps.pecap.domain.LogSystem}.
 */
@RestController
@RequestMapping("/api")
public class LogSystemResource {

    private final Logger log = LoggerFactory.getLogger(LogSystemResource.class);

    private static final String ENTITY_NAME = "logSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogSystemService logSystemService;

    private final LogSystemRepository logSystemRepository;

    public LogSystemResource(LogSystemService logSystemService, LogSystemRepository logSystemRepository) {
        this.logSystemService = logSystemService;
        this.logSystemRepository = logSystemRepository;
    }

    /**
     * {@code POST  /log-systems} : Create a new logSystem.
     *
     * @param logSystem the logSystem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logSystem, or with status {@code 400 (Bad Request)} if the logSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-systems")
    public ResponseEntity<LogSystem> createLogSystem(@Valid @RequestBody LogSystem logSystem) throws URISyntaxException {
        log.debug("REST request to save LogSystem : {}", logSystem);
        if (logSystem.getId() != null) {
            throw new BadRequestAlertException("A new logSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogSystem result = logSystemService.save(logSystem);
        return ResponseEntity
            .created(new URI("/api/log-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-systems/:id} : Updates an existing logSystem.
     *
     * @param id the id of the logSystem to save.
     * @param logSystem the logSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logSystem,
     * or with status {@code 400 (Bad Request)} if the logSystem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-systems/{id}")
    public ResponseEntity<LogSystem> updateLogSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LogSystem logSystem
    ) throws URISyntaxException {
        log.debug("REST request to update LogSystem : {}, {}", id, logSystem);
        if (logSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, logSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LogSystem result = logSystemService.update(logSystem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logSystem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /log-systems/:id} : Partial updates given fields of an existing logSystem, field will ignore if it is null
     *
     * @param id the id of the logSystem to save.
     * @param logSystem the logSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logSystem,
     * or with status {@code 400 (Bad Request)} if the logSystem is not valid,
     * or with status {@code 404 (Not Found)} if the logSystem is not found,
     * or with status {@code 500 (Internal Server Error)} if the logSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/log-systems/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LogSystem> partialUpdateLogSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LogSystem logSystem
    ) throws URISyntaxException {
        log.debug("REST request to partial update LogSystem partially : {}, {}", id, logSystem);
        if (logSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, logSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LogSystem> result = logSystemService.partialUpdate(logSystem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logSystem.getId().toString())
        );
    }

    /**
     * {@code GET  /log-systems} : get all the logSystems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logSystems in body.
     */
    @GetMapping("/log-systems")
    public List<LogSystem> getAllLogSystems() {
        log.debug("REST request to get all LogSystems");
        return logSystemService.findAll();
    }

    /**
     * {@code GET  /log-systems/:id} : get the "id" logSystem.
     *
     * @param id the id of the logSystem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logSystem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-systems/{id}")
    public ResponseEntity<LogSystem> getLogSystem(@PathVariable Long id) {
        log.debug("REST request to get LogSystem : {}", id);
        Optional<LogSystem> logSystem = logSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logSystem);
    }

    /**
     * {@code DELETE  /log-systems/:id} : delete the "id" logSystem.
     *
     * @param id the id of the logSystem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-systems/{id}")
    public ResponseEntity<Void> deleteLogSystem(@PathVariable Long id) {
        log.debug("REST request to delete LogSystem : {}", id);
        logSystemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

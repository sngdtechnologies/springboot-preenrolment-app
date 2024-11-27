package org.ps.pecap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.ps.pecap.domain.EtatProcedure;
import org.ps.pecap.repository.EtatProcedureRepository;
import org.ps.pecap.service.EtatProcedureService;
import org.ps.pecap.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.ps.pecap.domain.EtatProcedure}.
 */
@RestController
@RequestMapping("/api")
public class EtatProcedureResource {

    private final Logger log = LoggerFactory.getLogger(EtatProcedureResource.class);

    private static final String ENTITY_NAME = "etatProcedure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatProcedureService etatProcedureService;

    private final EtatProcedureRepository etatProcedureRepository;

    public EtatProcedureResource(EtatProcedureService etatProcedureService, EtatProcedureRepository etatProcedureRepository) {
        this.etatProcedureService = etatProcedureService;
        this.etatProcedureRepository = etatProcedureRepository;
    }

    /**
     * {@code POST  /etat-procedures} : Create a new etatProcedure.
     *
     * @param etatProcedure the etatProcedure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatProcedure, or with status {@code 400 (Bad Request)} if the etatProcedure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-procedures")
    public ResponseEntity<EtatProcedure> createEtatProcedure(@Valid @RequestBody EtatProcedure etatProcedure) throws URISyntaxException {
        log.debug("REST request to save EtatProcedure : {}", etatProcedure);
        if (etatProcedure.getId() != null) {
            throw new BadRequestAlertException("A new etatProcedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatProcedure result = etatProcedureService.save(etatProcedure);
        return ResponseEntity
            .created(new URI("/api/etat-procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-procedures/:id} : Updates an existing etatProcedure.
     *
     * @param id the id of the etatProcedure to save.
     * @param etatProcedure the etatProcedure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatProcedure,
     * or with status {@code 400 (Bad Request)} if the etatProcedure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatProcedure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-procedures/{id}")
    public ResponseEntity<EtatProcedure> updateEtatProcedure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EtatProcedure etatProcedure
    ) throws URISyntaxException {
        log.debug("REST request to update EtatProcedure : {}, {}", id, etatProcedure);
        if (etatProcedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etatProcedure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etatProcedureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtatProcedure result = etatProcedureService.update(etatProcedure);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatProcedure.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etat-procedures/:id} : Partial updates given fields of an existing etatProcedure, field will ignore if it is null
     *
     * @param id the id of the etatProcedure to save.
     * @param etatProcedure the etatProcedure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatProcedure,
     * or with status {@code 400 (Bad Request)} if the etatProcedure is not valid,
     * or with status {@code 404 (Not Found)} if the etatProcedure is not found,
     * or with status {@code 500 (Internal Server Error)} if the etatProcedure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etat-procedures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtatProcedure> partialUpdateEtatProcedure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EtatProcedure etatProcedure
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtatProcedure partially : {}, {}", id, etatProcedure);
        if (etatProcedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etatProcedure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etatProcedureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtatProcedure> result = etatProcedureService.partialUpdate(etatProcedure);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatProcedure.getId().toString())
        );
    }

    /**
     * {@code GET  /etat-procedures} : get all the etatProcedures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatProcedures in body.
     */
    @GetMapping("/etat-procedures")
    public ResponseEntity<List<EtatProcedure>> getAllEtatProcedures(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EtatProcedures");
        Page<EtatProcedure> page = etatProcedureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-procedures/:id} : get the "id" etatProcedure.
     *
     * @param id the id of the etatProcedure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatProcedure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-procedures/{id}")
    public ResponseEntity<EtatProcedure> getEtatProcedure(@PathVariable Long id) {
        log.debug("REST request to get EtatProcedure : {}", id);
        Optional<EtatProcedure> etatProcedure = etatProcedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatProcedure);
    }

    /**
     * {@code DELETE  /etat-procedures/:id} : delete the "id" etatProcedure.
     *
     * @param id the id of the etatProcedure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-procedures/{id}")
    public ResponseEntity<Void> deleteEtatProcedure(@PathVariable Long id) {
        log.debug("REST request to delete EtatProcedure : {}", id);
        etatProcedureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package org.ps.pecap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.ps.pecap.domain.ModePaiement;
import org.ps.pecap.repository.ModePaiementRepository;
import org.ps.pecap.service.ModePaiementService;
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
 * REST controller for managing {@link org.ps.pecap.domain.ModePaiement}.
 */
@RestController
@RequestMapping("/api")
public class ModePaiementResource {

    private final Logger log = LoggerFactory.getLogger(ModePaiementResource.class);

    private static final String ENTITY_NAME = "modePaiement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModePaiementService modePaiementService;

    private final ModePaiementRepository modePaiementRepository;

    public ModePaiementResource(ModePaiementService modePaiementService, ModePaiementRepository modePaiementRepository) {
        this.modePaiementService = modePaiementService;
        this.modePaiementRepository = modePaiementRepository;
    }

    /**
     * {@code POST  /mode-paiements} : Create a new modePaiement.
     *
     * @param modePaiement the modePaiement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modePaiement, or with status {@code 400 (Bad Request)} if the modePaiement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mode-paiements")
    public ResponseEntity<ModePaiement> createModePaiement(@Valid @RequestBody ModePaiement modePaiement) throws URISyntaxException {
        log.debug("REST request to save ModePaiement : {}", modePaiement);
        if (modePaiement.getId() != null) {
            throw new BadRequestAlertException("A new modePaiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModePaiement result = modePaiementService.save(modePaiement);
        return ResponseEntity
            .created(new URI("/api/mode-paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mode-paiements/:id} : Updates an existing modePaiement.
     *
     * @param id the id of the modePaiement to save.
     * @param modePaiement the modePaiement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiement,
     * or with status {@code 400 (Bad Request)} if the modePaiement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modePaiement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mode-paiements/{id}")
    public ResponseEntity<ModePaiement> updateModePaiement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ModePaiement modePaiement
    ) throws URISyntaxException {
        log.debug("REST request to update ModePaiement : {}, {}", id, modePaiement);
        if (modePaiement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModePaiement result = modePaiementService.update(modePaiement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modePaiement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mode-paiements/:id} : Partial updates given fields of an existing modePaiement, field will ignore if it is null
     *
     * @param id the id of the modePaiement to save.
     * @param modePaiement the modePaiement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiement,
     * or with status {@code 400 (Bad Request)} if the modePaiement is not valid,
     * or with status {@code 404 (Not Found)} if the modePaiement is not found,
     * or with status {@code 500 (Internal Server Error)} if the modePaiement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mode-paiements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModePaiement> partialUpdateModePaiement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ModePaiement modePaiement
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModePaiement partially : {}, {}", id, modePaiement);
        if (modePaiement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModePaiement> result = modePaiementService.partialUpdate(modePaiement);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modePaiement.getId().toString())
        );
    }

    /**
     * {@code GET  /mode-paiements} : get all the modePaiements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modePaiements in body.
     */
    @GetMapping("/mode-paiements")
    public ResponseEntity<List<ModePaiement>> getAllModePaiements(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ModePaiements");
        Page<ModePaiement> page = modePaiementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mode-paiements/:id} : get the "id" modePaiement.
     *
     * @param id the id of the modePaiement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modePaiement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mode-paiements/{id}")
    public ResponseEntity<ModePaiement> getModePaiement(@PathVariable Long id) {
        log.debug("REST request to get ModePaiement : {}", id);
        Optional<ModePaiement> modePaiement = modePaiementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modePaiement);
    }

    /**
     * {@code DELETE  /mode-paiements/:id} : delete the "id" modePaiement.
     *
     * @param id the id of the modePaiement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mode-paiements/{id}")
    public ResponseEntity<Void> deleteModePaiement(@PathVariable Long id) {
        log.debug("REST request to delete ModePaiement : {}", id);
        modePaiementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

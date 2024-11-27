package org.ps.pecap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.ps.pecap.domain.TypePassport;
import org.ps.pecap.repository.TypePassportRepository;
import org.ps.pecap.service.TypePassportService;
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
 * REST controller for managing {@link org.ps.pecap.domain.TypePassport}.
 */
@RestController
@RequestMapping("/api")
public class TypePassportResource {

    private final Logger log = LoggerFactory.getLogger(TypePassportResource.class);

    private static final String ENTITY_NAME = "typePassport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePassportService typePassportService;

    private final TypePassportRepository typePassportRepository;

    public TypePassportResource(TypePassportService typePassportService, TypePassportRepository typePassportRepository) {
        this.typePassportService = typePassportService;
        this.typePassportRepository = typePassportRepository;
    }

    /**
     * {@code POST  /type-passports} : Create a new typePassport.
     *
     * @param typePassport the typePassport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePassport, or with status {@code 400 (Bad Request)} if the typePassport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-passports")
    public ResponseEntity<TypePassport> createTypePassport(@Valid @RequestBody TypePassport typePassport) throws URISyntaxException {
        log.debug("REST request to save TypePassport : {}", typePassport);
        if (typePassport.getId() != null) {
            throw new BadRequestAlertException("A new typePassport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePassport result = typePassportService.save(typePassport);
        return ResponseEntity
            .created(new URI("/api/type-passports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-passports/:id} : Updates an existing typePassport.
     *
     * @param id the id of the typePassport to save.
     * @param typePassport the typePassport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePassport,
     * or with status {@code 400 (Bad Request)} if the typePassport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePassport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-passports/{id}")
    public ResponseEntity<TypePassport> updateTypePassport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypePassport typePassport
    ) throws URISyntaxException {
        log.debug("REST request to update TypePassport : {}, {}", id, typePassport);
        if (typePassport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typePassport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typePassportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypePassport result = typePassportService.update(typePassport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePassport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-passports/:id} : Partial updates given fields of an existing typePassport, field will ignore if it is null
     *
     * @param id the id of the typePassport to save.
     * @param typePassport the typePassport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePassport,
     * or with status {@code 400 (Bad Request)} if the typePassport is not valid,
     * or with status {@code 404 (Not Found)} if the typePassport is not found,
     * or with status {@code 500 (Internal Server Error)} if the typePassport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-passports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypePassport> partialUpdateTypePassport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypePassport typePassport
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypePassport partially : {}, {}", id, typePassport);
        if (typePassport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typePassport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typePassportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypePassport> result = typePassportService.partialUpdate(typePassport);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePassport.getId().toString())
        );
    }

    /**
     * {@code GET  /type-passports} : get all the typePassports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePassports in body.
     */
    @GetMapping("/type-passports")
    public ResponseEntity<List<TypePassport>> getAllTypePassports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TypePassports");
        Page<TypePassport> page = typePassportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-passports/:id} : get the "id" typePassport.
     *
     * @param id the id of the typePassport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePassport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-passports/{id}")
    public ResponseEntity<TypePassport> getTypePassport(@PathVariable Long id) {
        log.debug("REST request to get TypePassport : {}", id);
        Optional<TypePassport> typePassport = typePassportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePassport);
    }

    /**
     * {@code DELETE  /type-passports/:id} : delete the "id" typePassport.
     *
     * @param id the id of the typePassport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-passports/{id}")
    public ResponseEntity<Void> deleteTypePassport(@PathVariable Long id) {
        log.debug("REST request to delete TypePassport : {}", id);
        typePassportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

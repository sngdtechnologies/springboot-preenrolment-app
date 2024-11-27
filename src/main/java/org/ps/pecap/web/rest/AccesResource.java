package org.ps.pecap.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.ps.pecap.domain.Acces;
import org.ps.pecap.repository.AccesRepository;
import org.ps.pecap.service.AccesService;
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
 * REST controller for managing {@link org.ps.pecap.domain.Acces}.
 */
@RestController
@RequestMapping("/api")
public class AccesResource {

    private final Logger log = LoggerFactory.getLogger(AccesResource.class);

    private final AccesService accesService;

    private final AccesRepository accesRepository;

    public AccesResource(AccesService accesService, AccesRepository accesRepository) {
        this.accesService = accesService;
        this.accesRepository = accesRepository;
    }

    /**
     * {@code GET  /acces} : get all the acces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acces in body.
     */
    @GetMapping("/acces")
    public ResponseEntity<List<Acces>> getAllAcces(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Acces");
        Page<Acces> page = accesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acces/:id} : get the "id" acces.
     *
     * @param id the id of the acces to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acces, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acces/{id}")
    public ResponseEntity<Acces> getAcces(@PathVariable Long id) {
        log.debug("REST request to get Acces : {}", id);
        Optional<Acces> acces = accesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acces);
    }
}

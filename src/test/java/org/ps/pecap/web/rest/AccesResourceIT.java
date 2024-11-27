package org.ps.pecap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ps.pecap.IntegrationTest;
import org.ps.pecap.domain.Acces;
import org.ps.pecap.domain.Annee;
import org.ps.pecap.repository.AccesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccesResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/acces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccesRepository accesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccesMockMvc;

    private Acces acces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acces createEntity(EntityManager em) {
        Acces acces = new Acces().nom(DEFAULT_NOM).code(DEFAULT_CODE);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        acces.setAnnee(annee);
        return acces;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acces createUpdatedEntity(EntityManager em) {
        Acces acces = new Acces().nom(UPDATED_NOM).code(UPDATED_CODE);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createUpdatedEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        acces.setAnnee(annee);
        return acces;
    }

    @BeforeEach
    public void initTest() {
        acces = createEntity(em);
    }

    @Test
    @Transactional
    void getAllAcces() throws Exception {
        // Initialize the database
        accesRepository.saveAndFlush(acces);

        // Get all the accesList
        restAccesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acces.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getAcces() throws Exception {
        // Initialize the database
        accesRepository.saveAndFlush(acces);

        // Get the acces
        restAccesMockMvc
            .perform(get(ENTITY_API_URL_ID, acces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acces.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingAcces() throws Exception {
        // Get the acces
        restAccesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}

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
import org.ps.pecap.domain.Annee;
import org.ps.pecap.domain.Passport;
import org.ps.pecap.domain.TypePassport;
import org.ps.pecap.repository.TypePassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TypePassportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypePassportResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-passports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypePassportRepository typePassportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypePassportMockMvc;

    private TypePassport typePassport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePassport createEntity(EntityManager em) {
        TypePassport typePassport = new TypePassport().nom(DEFAULT_NOM);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        typePassport.setAnnee(annee);
        // Add required entity
        Passport passport;
        if (TestUtil.findAll(em, Passport.class).isEmpty()) {
            passport = PassportResourceIT.createEntity(em);
            em.persist(passport);
            em.flush();
        } else {
            passport = TestUtil.findAll(em, Passport.class).get(0);
        }
        typePassport.setPassport(passport);
        return typePassport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePassport createUpdatedEntity(EntityManager em) {
        TypePassport typePassport = new TypePassport().nom(UPDATED_NOM);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createUpdatedEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        typePassport.setAnnee(annee);
        // Add required entity
        Passport passport;
        if (TestUtil.findAll(em, Passport.class).isEmpty()) {
            passport = PassportResourceIT.createUpdatedEntity(em);
            em.persist(passport);
            em.flush();
        } else {
            passport = TestUtil.findAll(em, Passport.class).get(0);
        }
        typePassport.setPassport(passport);
        return typePassport;
    }

    @BeforeEach
    public void initTest() {
        typePassport = createEntity(em);
    }

    @Test
    @Transactional
    void createTypePassport() throws Exception {
        int databaseSizeBeforeCreate = typePassportRepository.findAll().size();
        // Create the TypePassport
        restTypePassportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typePassport)))
            .andExpect(status().isCreated());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeCreate + 1);
        TypePassport testTypePassport = typePassportList.get(typePassportList.size() - 1);
        assertThat(testTypePassport.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void createTypePassportWithExistingId() throws Exception {
        // Create the TypePassport with an existing ID
        typePassport.setId(1L);

        int databaseSizeBeforeCreate = typePassportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePassportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typePassport)))
            .andExpect(status().isBadRequest());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypePassports() throws Exception {
        // Initialize the database
        typePassportRepository.saveAndFlush(typePassport);

        // Get all the typePassportList
        restTypePassportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePassport.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    void getTypePassport() throws Exception {
        // Initialize the database
        typePassportRepository.saveAndFlush(typePassport);

        // Get the typePassport
        restTypePassportMockMvc
            .perform(get(ENTITY_API_URL_ID, typePassport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePassport.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingTypePassport() throws Exception {
        // Get the typePassport
        restTypePassportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypePassport() throws Exception {
        // Initialize the database
        typePassportRepository.saveAndFlush(typePassport);

        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();

        // Update the typePassport
        TypePassport updatedTypePassport = typePassportRepository.findById(typePassport.getId()).get();
        // Disconnect from session so that the updates on updatedTypePassport are not directly saved in db
        em.detach(updatedTypePassport);
        updatedTypePassport.nom(UPDATED_NOM);

        restTypePassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypePassport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypePassport))
            )
            .andExpect(status().isOk());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
        TypePassport testTypePassport = typePassportList.get(typePassportList.size() - 1);
        assertThat(testTypePassport.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void putNonExistingTypePassport() throws Exception {
        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();
        typePassport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typePassport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typePassport))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypePassport() throws Exception {
        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();
        typePassport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typePassport))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypePassport() throws Exception {
        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();
        typePassport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePassportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typePassport)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypePassportWithPatch() throws Exception {
        // Initialize the database
        typePassportRepository.saveAndFlush(typePassport);

        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();

        // Update the typePassport using partial update
        TypePassport partialUpdatedTypePassport = new TypePassport();
        partialUpdatedTypePassport.setId(typePassport.getId());

        restTypePassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypePassport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypePassport))
            )
            .andExpect(status().isOk());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
        TypePassport testTypePassport = typePassportList.get(typePassportList.size() - 1);
        assertThat(testTypePassport.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void fullUpdateTypePassportWithPatch() throws Exception {
        // Initialize the database
        typePassportRepository.saveAndFlush(typePassport);

        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();

        // Update the typePassport using partial update
        TypePassport partialUpdatedTypePassport = new TypePassport();
        partialUpdatedTypePassport.setId(typePassport.getId());

        partialUpdatedTypePassport.nom(UPDATED_NOM);

        restTypePassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypePassport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypePassport))
            )
            .andExpect(status().isOk());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
        TypePassport testTypePassport = typePassportList.get(typePassportList.size() - 1);
        assertThat(testTypePassport.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void patchNonExistingTypePassport() throws Exception {
        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();
        typePassport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typePassport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typePassport))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypePassport() throws Exception {
        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();
        typePassport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typePassport))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypePassport() throws Exception {
        int databaseSizeBeforeUpdate = typePassportRepository.findAll().size();
        typePassport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePassportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typePassport))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypePassport in the database
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypePassport() throws Exception {
        // Initialize the database
        typePassportRepository.saveAndFlush(typePassport);

        int databaseSizeBeforeDelete = typePassportRepository.findAll().size();

        // Delete the typePassport
        restTypePassportMockMvc
            .perform(delete(ENTITY_API_URL_ID, typePassport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePassport> typePassportList = typePassportRepository.findAll();
        assertThat(typePassportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

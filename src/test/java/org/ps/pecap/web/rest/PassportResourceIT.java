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
import org.ps.pecap.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PassportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PassportResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/passports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassportMockMvc;

    private Passport passport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passport createEntity(EntityManager em) {
        Passport passport = new Passport().nom(DEFAULT_NOM);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        passport.setAnnee(annee);
        return passport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passport createUpdatedEntity(EntityManager em) {
        Passport passport = new Passport().nom(UPDATED_NOM);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createUpdatedEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        passport.setAnnee(annee);
        return passport;
    }

    @BeforeEach
    public void initTest() {
        passport = createEntity(em);
    }

    @Test
    @Transactional
    void createPassport() throws Exception {
        int databaseSizeBeforeCreate = passportRepository.findAll().size();
        // Create the Passport
        restPassportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passport)))
            .andExpect(status().isCreated());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate + 1);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void createPassportWithExistingId() throws Exception {
        // Create the Passport with an existing ID
        passport.setId(1L);

        int databaseSizeBeforeCreate = passportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passport)))
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPassports() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList
        restPassportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passport.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    void getPassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get the passport
        restPassportMockMvc
            .perform(get(ENTITY_API_URL_ID, passport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passport.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingPassport() throws Exception {
        // Get the passport
        restPassportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport
        Passport updatedPassport = passportRepository.findById(passport.getId()).get();
        // Disconnect from session so that the updates on updatedPassport are not directly saved in db
        em.detach(updatedPassport);
        updatedPassport.nom(UPDATED_NOM);

        restPassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPassport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPassport))
            )
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void putNonExistingPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passport))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passport))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passport)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePassportWithPatch() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport using partial update
        Passport partialUpdatedPassport = new Passport();
        partialUpdatedPassport.setId(passport.getId());

        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassport))
            )
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void fullUpdatePassportWithPatch() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport using partial update
        Passport partialUpdatedPassport = new Passport();
        partialUpdatedPassport.setId(passport.getId());

        partialUpdatedPassport.nom(UPDATED_NOM);

        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassport))
            )
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void patchNonExistingPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, passport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passport))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passport))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(passport)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeDelete = passportRepository.findAll().size();

        // Delete the passport
        restPassportMockMvc
            .perform(delete(ENTITY_API_URL_ID, passport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

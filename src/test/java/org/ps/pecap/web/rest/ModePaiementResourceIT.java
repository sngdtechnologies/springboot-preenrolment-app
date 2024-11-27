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
import org.ps.pecap.domain.Client;
import org.ps.pecap.domain.ModePaiement;
import org.ps.pecap.domain.Passport;
import org.ps.pecap.repository.ModePaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ModePaiementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModePaiementResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mode-paiements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModePaiementRepository modePaiementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModePaiementMockMvc;

    private ModePaiement modePaiement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiement createEntity(EntityManager em) {
        ModePaiement modePaiement = new ModePaiement().nom(DEFAULT_NOM);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        modePaiement.setAnnee(annee);
        // Add required entity
        Passport passport;
        if (TestUtil.findAll(em, Passport.class).isEmpty()) {
            passport = PassportResourceIT.createEntity(em);
            em.persist(passport);
            em.flush();
        } else {
            passport = TestUtil.findAll(em, Passport.class).get(0);
        }
        modePaiement.setPassport(passport);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        modePaiement.setClient(client);
        return modePaiement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiement createUpdatedEntity(EntityManager em) {
        ModePaiement modePaiement = new ModePaiement().nom(UPDATED_NOM);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createUpdatedEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        modePaiement.setAnnee(annee);
        // Add required entity
        Passport passport;
        if (TestUtil.findAll(em, Passport.class).isEmpty()) {
            passport = PassportResourceIT.createUpdatedEntity(em);
            em.persist(passport);
            em.flush();
        } else {
            passport = TestUtil.findAll(em, Passport.class).get(0);
        }
        modePaiement.setPassport(passport);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        modePaiement.setClient(client);
        return modePaiement;
    }

    @BeforeEach
    public void initTest() {
        modePaiement = createEntity(em);
    }

    @Test
    @Transactional
    void createModePaiement() throws Exception {
        int databaseSizeBeforeCreate = modePaiementRepository.findAll().size();
        // Create the ModePaiement
        restModePaiementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modePaiement)))
            .andExpect(status().isCreated());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeCreate + 1);
        ModePaiement testModePaiement = modePaiementList.get(modePaiementList.size() - 1);
        assertThat(testModePaiement.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void createModePaiementWithExistingId() throws Exception {
        // Create the ModePaiement with an existing ID
        modePaiement.setId(1L);

        int databaseSizeBeforeCreate = modePaiementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModePaiementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modePaiement)))
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = modePaiementRepository.findAll().size();
        // set the field null
        modePaiement.setNom(null);

        // Create the ModePaiement, which fails.

        restModePaiementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modePaiement)))
            .andExpect(status().isBadRequest());

        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllModePaiements() throws Exception {
        // Initialize the database
        modePaiementRepository.saveAndFlush(modePaiement);

        // Get all the modePaiementList
        restModePaiementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modePaiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    void getModePaiement() throws Exception {
        // Initialize the database
        modePaiementRepository.saveAndFlush(modePaiement);

        // Get the modePaiement
        restModePaiementMockMvc
            .perform(get(ENTITY_API_URL_ID, modePaiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modePaiement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingModePaiement() throws Exception {
        // Get the modePaiement
        restModePaiementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModePaiement() throws Exception {
        // Initialize the database
        modePaiementRepository.saveAndFlush(modePaiement);

        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();

        // Update the modePaiement
        ModePaiement updatedModePaiement = modePaiementRepository.findById(modePaiement.getId()).get();
        // Disconnect from session so that the updates on updatedModePaiement are not directly saved in db
        em.detach(updatedModePaiement);
        updatedModePaiement.nom(UPDATED_NOM);

        restModePaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModePaiement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedModePaiement))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
        ModePaiement testModePaiement = modePaiementList.get(modePaiementList.size() - 1);
        assertThat(testModePaiement.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void putNonExistingModePaiement() throws Exception {
        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();
        modePaiement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modePaiement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modePaiement))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModePaiement() throws Exception {
        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();
        modePaiement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modePaiement))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModePaiement() throws Exception {
        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();
        modePaiement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modePaiement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModePaiementWithPatch() throws Exception {
        // Initialize the database
        modePaiementRepository.saveAndFlush(modePaiement);

        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();

        // Update the modePaiement using partial update
        ModePaiement partialUpdatedModePaiement = new ModePaiement();
        partialUpdatedModePaiement.setId(modePaiement.getId());

        partialUpdatedModePaiement.nom(UPDATED_NOM);

        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModePaiement))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
        ModePaiement testModePaiement = modePaiementList.get(modePaiementList.size() - 1);
        assertThat(testModePaiement.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void fullUpdateModePaiementWithPatch() throws Exception {
        // Initialize the database
        modePaiementRepository.saveAndFlush(modePaiement);

        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();

        // Update the modePaiement using partial update
        ModePaiement partialUpdatedModePaiement = new ModePaiement();
        partialUpdatedModePaiement.setId(modePaiement.getId());

        partialUpdatedModePaiement.nom(UPDATED_NOM);

        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModePaiement))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
        ModePaiement testModePaiement = modePaiementList.get(modePaiementList.size() - 1);
        assertThat(testModePaiement.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void patchNonExistingModePaiement() throws Exception {
        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();
        modePaiement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modePaiement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modePaiement))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModePaiement() throws Exception {
        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();
        modePaiement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modePaiement))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModePaiement() throws Exception {
        int databaseSizeBeforeUpdate = modePaiementRepository.findAll().size();
        modePaiement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(modePaiement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiement in the database
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModePaiement() throws Exception {
        // Initialize the database
        modePaiementRepository.saveAndFlush(modePaiement);

        int databaseSizeBeforeDelete = modePaiementRepository.findAll().size();

        // Delete the modePaiement
        restModePaiementMockMvc
            .perform(delete(ENTITY_API_URL_ID, modePaiement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        assertThat(modePaiementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

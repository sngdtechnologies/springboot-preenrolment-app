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
import org.ps.pecap.domain.EtatProcedure;
import org.ps.pecap.repository.EtatProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EtatProcedureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtatProcedureResourceIT {

    private static final String DEFAULT_ETAT_PRE_ENROLE = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_PRE_ENROLE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT_ENROLE = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_ENROLE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT_RETRAIT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_RETRAIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etat-procedures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtatProcedureRepository etatProcedureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtatProcedureMockMvc;

    private EtatProcedure etatProcedure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatProcedure createEntity(EntityManager em) {
        EtatProcedure etatProcedure = new EtatProcedure()
            .etatPreEnrole(DEFAULT_ETAT_PRE_ENROLE)
            .etatEnrole(DEFAULT_ETAT_ENROLE)
            .etatRetrait(DEFAULT_ETAT_RETRAIT);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        etatProcedure.setAnnee(annee);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        etatProcedure.setClient(client);
        return etatProcedure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatProcedure createUpdatedEntity(EntityManager em) {
        EtatProcedure etatProcedure = new EtatProcedure()
            .etatPreEnrole(UPDATED_ETAT_PRE_ENROLE)
            .etatEnrole(UPDATED_ETAT_ENROLE)
            .etatRetrait(UPDATED_ETAT_RETRAIT);
        // Add required entity
        Annee annee;
        if (TestUtil.findAll(em, Annee.class).isEmpty()) {
            annee = AnneeResourceIT.createUpdatedEntity(em);
            em.persist(annee);
            em.flush();
        } else {
            annee = TestUtil.findAll(em, Annee.class).get(0);
        }
        etatProcedure.setAnnee(annee);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        etatProcedure.setClient(client);
        return etatProcedure;
    }

    @BeforeEach
    public void initTest() {
        etatProcedure = createEntity(em);
    }

    @Test
    @Transactional
    void createEtatProcedure() throws Exception {
        int databaseSizeBeforeCreate = etatProcedureRepository.findAll().size();
        // Create the EtatProcedure
        restEtatProcedureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatProcedure)))
            .andExpect(status().isCreated());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeCreate + 1);
        EtatProcedure testEtatProcedure = etatProcedureList.get(etatProcedureList.size() - 1);
        assertThat(testEtatProcedure.getEtatPreEnrole()).isEqualTo(DEFAULT_ETAT_PRE_ENROLE);
        assertThat(testEtatProcedure.getEtatEnrole()).isEqualTo(DEFAULT_ETAT_ENROLE);
        assertThat(testEtatProcedure.getEtatRetrait()).isEqualTo(DEFAULT_ETAT_RETRAIT);
    }

    @Test
    @Transactional
    void createEtatProcedureWithExistingId() throws Exception {
        // Create the EtatProcedure with an existing ID
        etatProcedure.setId(1L);

        int databaseSizeBeforeCreate = etatProcedureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatProcedureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtatProcedures() throws Exception {
        // Initialize the database
        etatProcedureRepository.saveAndFlush(etatProcedure);

        // Get all the etatProcedureList
        restEtatProcedureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].etatPreEnrole").value(hasItem(DEFAULT_ETAT_PRE_ENROLE)))
            .andExpect(jsonPath("$.[*].etatEnrole").value(hasItem(DEFAULT_ETAT_ENROLE)))
            .andExpect(jsonPath("$.[*].etatRetrait").value(hasItem(DEFAULT_ETAT_RETRAIT)));
    }

    @Test
    @Transactional
    void getEtatProcedure() throws Exception {
        // Initialize the database
        etatProcedureRepository.saveAndFlush(etatProcedure);

        // Get the etatProcedure
        restEtatProcedureMockMvc
            .perform(get(ENTITY_API_URL_ID, etatProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatProcedure.getId().intValue()))
            .andExpect(jsonPath("$.etatPreEnrole").value(DEFAULT_ETAT_PRE_ENROLE))
            .andExpect(jsonPath("$.etatEnrole").value(DEFAULT_ETAT_ENROLE))
            .andExpect(jsonPath("$.etatRetrait").value(DEFAULT_ETAT_RETRAIT));
    }

    @Test
    @Transactional
    void getNonExistingEtatProcedure() throws Exception {
        // Get the etatProcedure
        restEtatProcedureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEtatProcedure() throws Exception {
        // Initialize the database
        etatProcedureRepository.saveAndFlush(etatProcedure);

        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();

        // Update the etatProcedure
        EtatProcedure updatedEtatProcedure = etatProcedureRepository.findById(etatProcedure.getId()).get();
        // Disconnect from session so that the updates on updatedEtatProcedure are not directly saved in db
        em.detach(updatedEtatProcedure);
        updatedEtatProcedure.etatPreEnrole(UPDATED_ETAT_PRE_ENROLE).etatEnrole(UPDATED_ETAT_ENROLE).etatRetrait(UPDATED_ETAT_RETRAIT);

        restEtatProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtatProcedure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtatProcedure))
            )
            .andExpect(status().isOk());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
        EtatProcedure testEtatProcedure = etatProcedureList.get(etatProcedureList.size() - 1);
        assertThat(testEtatProcedure.getEtatPreEnrole()).isEqualTo(UPDATED_ETAT_PRE_ENROLE);
        assertThat(testEtatProcedure.getEtatEnrole()).isEqualTo(UPDATED_ETAT_ENROLE);
        assertThat(testEtatProcedure.getEtatRetrait()).isEqualTo(UPDATED_ETAT_RETRAIT);
    }

    @Test
    @Transactional
    void putNonExistingEtatProcedure() throws Exception {
        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();
        etatProcedure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etatProcedure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatProcedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtatProcedure() throws Exception {
        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();
        etatProcedure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatProcedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtatProcedure() throws Exception {
        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();
        etatProcedure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatProcedureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatProcedure)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtatProcedureWithPatch() throws Exception {
        // Initialize the database
        etatProcedureRepository.saveAndFlush(etatProcedure);

        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();

        // Update the etatProcedure using partial update
        EtatProcedure partialUpdatedEtatProcedure = new EtatProcedure();
        partialUpdatedEtatProcedure.setId(etatProcedure.getId());

        partialUpdatedEtatProcedure.etatPreEnrole(UPDATED_ETAT_PRE_ENROLE).etatEnrole(UPDATED_ETAT_ENROLE);

        restEtatProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatProcedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatProcedure))
            )
            .andExpect(status().isOk());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
        EtatProcedure testEtatProcedure = etatProcedureList.get(etatProcedureList.size() - 1);
        assertThat(testEtatProcedure.getEtatPreEnrole()).isEqualTo(UPDATED_ETAT_PRE_ENROLE);
        assertThat(testEtatProcedure.getEtatEnrole()).isEqualTo(UPDATED_ETAT_ENROLE);
        assertThat(testEtatProcedure.getEtatRetrait()).isEqualTo(DEFAULT_ETAT_RETRAIT);
    }

    @Test
    @Transactional
    void fullUpdateEtatProcedureWithPatch() throws Exception {
        // Initialize the database
        etatProcedureRepository.saveAndFlush(etatProcedure);

        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();

        // Update the etatProcedure using partial update
        EtatProcedure partialUpdatedEtatProcedure = new EtatProcedure();
        partialUpdatedEtatProcedure.setId(etatProcedure.getId());

        partialUpdatedEtatProcedure
            .etatPreEnrole(UPDATED_ETAT_PRE_ENROLE)
            .etatEnrole(UPDATED_ETAT_ENROLE)
            .etatRetrait(UPDATED_ETAT_RETRAIT);

        restEtatProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatProcedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatProcedure))
            )
            .andExpect(status().isOk());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
        EtatProcedure testEtatProcedure = etatProcedureList.get(etatProcedureList.size() - 1);
        assertThat(testEtatProcedure.getEtatPreEnrole()).isEqualTo(UPDATED_ETAT_PRE_ENROLE);
        assertThat(testEtatProcedure.getEtatEnrole()).isEqualTo(UPDATED_ETAT_ENROLE);
        assertThat(testEtatProcedure.getEtatRetrait()).isEqualTo(UPDATED_ETAT_RETRAIT);
    }

    @Test
    @Transactional
    void patchNonExistingEtatProcedure() throws Exception {
        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();
        etatProcedure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etatProcedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatProcedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtatProcedure() throws Exception {
        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();
        etatProcedure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatProcedure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtatProcedure() throws Exception {
        int databaseSizeBeforeUpdate = etatProcedureRepository.findAll().size();
        etatProcedure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etatProcedure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatProcedure in the database
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtatProcedure() throws Exception {
        // Initialize the database
        etatProcedureRepository.saveAndFlush(etatProcedure);

        int databaseSizeBeforeDelete = etatProcedureRepository.findAll().size();

        // Delete the etatProcedure
        restEtatProcedureMockMvc
            .perform(delete(ENTITY_API_URL_ID, etatProcedure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatProcedure> etatProcedureList = etatProcedureRepository.findAll();
        assertThat(etatProcedureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

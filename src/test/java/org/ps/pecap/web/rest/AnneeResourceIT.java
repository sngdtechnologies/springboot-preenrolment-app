package org.ps.pecap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ps.pecap.IntegrationTest;
import org.ps.pecap.domain.Annee;
import org.ps.pecap.repository.AnneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AnneeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnneeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_VERROUILLER = false;
    private static final Boolean UPDATED_IS_VERROUILLER = true;

    private static final Boolean DEFAULT_IS_CLOTURER = false;
    private static final Boolean UPDATED_IS_CLOTURER = true;

    private static final String ENTITY_API_URL = "/api/annees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnneeRepository anneeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnneeMockMvc;

    private Annee annee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annee createEntity(EntityManager em) {
        Annee annee = new Annee()
            .nom(DEFAULT_NOM)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .isVerrouiller(DEFAULT_IS_VERROUILLER)
            .isCloturer(DEFAULT_IS_CLOTURER);
        return annee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annee createUpdatedEntity(EntityManager em) {
        Annee annee = new Annee()
            .nom(UPDATED_NOM)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .isVerrouiller(UPDATED_IS_VERROUILLER)
            .isCloturer(UPDATED_IS_CLOTURER);
        return annee;
    }

    @BeforeEach
    public void initTest() {
        annee = createEntity(em);
    }

    @Test
    @Transactional
    void createAnnee() throws Exception {
        int databaseSizeBeforeCreate = anneeRepository.findAll().size();
        // Create the Annee
        restAnneeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annee)))
            .andExpect(status().isCreated());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeCreate + 1);
        Annee testAnnee = anneeList.get(anneeList.size() - 1);
        assertThat(testAnnee.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAnnee.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAnnee.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAnnee.getIsVerrouiller()).isEqualTo(DEFAULT_IS_VERROUILLER);
        assertThat(testAnnee.getIsCloturer()).isEqualTo(DEFAULT_IS_CLOTURER);
    }

    @Test
    @Transactional
    void createAnneeWithExistingId() throws Exception {
        // Create the Annee with an existing ID
        annee.setId(1L);

        int databaseSizeBeforeCreate = anneeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnneeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annee)))
            .andExpect(status().isBadRequest());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = anneeRepository.findAll().size();
        // set the field null
        annee.setNom(null);

        // Create the Annee, which fails.

        restAnneeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annee)))
            .andExpect(status().isBadRequest());

        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsVerrouillerIsRequired() throws Exception {
        int databaseSizeBeforeTest = anneeRepository.findAll().size();
        // set the field null
        annee.setIsVerrouiller(null);

        // Create the Annee, which fails.

        restAnneeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annee)))
            .andExpect(status().isBadRequest());

        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsCloturerIsRequired() throws Exception {
        int databaseSizeBeforeTest = anneeRepository.findAll().size();
        // set the field null
        annee.setIsCloturer(null);

        // Create the Annee, which fails.

        restAnneeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annee)))
            .andExpect(status().isBadRequest());

        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnnees() throws Exception {
        // Initialize the database
        anneeRepository.saveAndFlush(annee);

        // Get all the anneeList
        restAnneeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annee.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].isVerrouiller").value(hasItem(DEFAULT_IS_VERROUILLER.booleanValue())))
            .andExpect(jsonPath("$.[*].isCloturer").value(hasItem(DEFAULT_IS_CLOTURER.booleanValue())));
    }

    @Test
    @Transactional
    void getAnnee() throws Exception {
        // Initialize the database
        anneeRepository.saveAndFlush(annee);

        // Get the annee
        restAnneeMockMvc
            .perform(get(ENTITY_API_URL_ID, annee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(annee.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.isVerrouiller").value(DEFAULT_IS_VERROUILLER.booleanValue()))
            .andExpect(jsonPath("$.isCloturer").value(DEFAULT_IS_CLOTURER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnnee() throws Exception {
        // Get the annee
        restAnneeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnnee() throws Exception {
        // Initialize the database
        anneeRepository.saveAndFlush(annee);

        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();

        // Update the annee
        Annee updatedAnnee = anneeRepository.findById(annee.getId()).get();
        // Disconnect from session so that the updates on updatedAnnee are not directly saved in db
        em.detach(updatedAnnee);
        updatedAnnee
            .nom(UPDATED_NOM)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .isVerrouiller(UPDATED_IS_VERROUILLER)
            .isCloturer(UPDATED_IS_CLOTURER);

        restAnneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnnee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnnee))
            )
            .andExpect(status().isOk());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
        Annee testAnnee = anneeList.get(anneeList.size() - 1);
        assertThat(testAnnee.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAnnee.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAnnee.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAnnee.getIsVerrouiller()).isEqualTo(UPDATED_IS_VERROUILLER);
        assertThat(testAnnee.getIsCloturer()).isEqualTo(UPDATED_IS_CLOTURER);
    }

    @Test
    @Transactional
    void putNonExistingAnnee() throws Exception {
        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();
        annee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, annee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(annee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnnee() throws Exception {
        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();
        annee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(annee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnnee() throws Exception {
        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();
        annee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnneeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnneeWithPatch() throws Exception {
        // Initialize the database
        anneeRepository.saveAndFlush(annee);

        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();

        // Update the annee using partial update
        Annee partialUpdatedAnnee = new Annee();
        partialUpdatedAnnee.setId(annee.getId());

        partialUpdatedAnnee.nom(UPDATED_NOM).dateDebut(UPDATED_DATE_DEBUT);

        restAnneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnee))
            )
            .andExpect(status().isOk());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
        Annee testAnnee = anneeList.get(anneeList.size() - 1);
        assertThat(testAnnee.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAnnee.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAnnee.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAnnee.getIsVerrouiller()).isEqualTo(DEFAULT_IS_VERROUILLER);
        assertThat(testAnnee.getIsCloturer()).isEqualTo(DEFAULT_IS_CLOTURER);
    }

    @Test
    @Transactional
    void fullUpdateAnneeWithPatch() throws Exception {
        // Initialize the database
        anneeRepository.saveAndFlush(annee);

        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();

        // Update the annee using partial update
        Annee partialUpdatedAnnee = new Annee();
        partialUpdatedAnnee.setId(annee.getId());

        partialUpdatedAnnee
            .nom(UPDATED_NOM)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .isVerrouiller(UPDATED_IS_VERROUILLER)
            .isCloturer(UPDATED_IS_CLOTURER);

        restAnneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnee))
            )
            .andExpect(status().isOk());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
        Annee testAnnee = anneeList.get(anneeList.size() - 1);
        assertThat(testAnnee.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAnnee.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAnnee.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAnnee.getIsVerrouiller()).isEqualTo(UPDATED_IS_VERROUILLER);
        assertThat(testAnnee.getIsCloturer()).isEqualTo(UPDATED_IS_CLOTURER);
    }

    @Test
    @Transactional
    void patchNonExistingAnnee() throws Exception {
        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();
        annee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, annee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(annee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnnee() throws Exception {
        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();
        annee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(annee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnnee() throws Exception {
        int databaseSizeBeforeUpdate = anneeRepository.findAll().size();
        annee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnneeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(annee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annee in the database
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnnee() throws Exception {
        // Initialize the database
        anneeRepository.saveAndFlush(annee);

        int databaseSizeBeforeDelete = anneeRepository.findAll().size();

        // Delete the annee
        restAnneeMockMvc
            .perform(delete(ENTITY_API_URL_ID, annee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Annee> anneeList = anneeRepository.findAll();
        assertThat(anneeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

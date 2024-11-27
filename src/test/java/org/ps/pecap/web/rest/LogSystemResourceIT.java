package org.ps.pecap.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ps.pecap.IntegrationTest;
import org.ps.pecap.domain.LogSystem;
import org.ps.pecap.repository.LogSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LogSystemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LogSystemResourceIT {

    private static final Instant DEFAULT_EVENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EVENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/log-systems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LogSystemRepository logSystemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogSystemMockMvc;

    private LogSystem logSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogSystem createEntity(EntityManager em) {
        LogSystem logSystem = new LogSystem().eventDate(DEFAULT_EVENT_DATE).login(DEFAULT_LOGIN).message(DEFAULT_MESSAGE);
        return logSystem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogSystem createUpdatedEntity(EntityManager em) {
        LogSystem logSystem = new LogSystem().eventDate(UPDATED_EVENT_DATE).login(UPDATED_LOGIN).message(UPDATED_MESSAGE);
        return logSystem;
    }

    @BeforeEach
    public void initTest() {
        logSystem = createEntity(em);
    }

    @Test
    @Transactional
    void createLogSystem() throws Exception {
        int databaseSizeBeforeCreate = logSystemRepository.findAll().size();
        // Create the LogSystem
        restLogSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logSystem)))
            .andExpect(status().isCreated());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeCreate + 1);
        LogSystem testLogSystem = logSystemList.get(logSystemList.size() - 1);
        assertThat(testLogSystem.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testLogSystem.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testLogSystem.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createLogSystemWithExistingId() throws Exception {
        // Create the LogSystem with an existing ID
        logSystem.setId(1L);

        int databaseSizeBeforeCreate = logSystemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logSystem)))
            .andExpect(status().isBadRequest());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEventDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = logSystemRepository.findAll().size();
        // set the field null
        logSystem.setEventDate(null);

        // Create the LogSystem, which fails.

        restLogSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logSystem)))
            .andExpect(status().isBadRequest());

        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = logSystemRepository.findAll().size();
        // set the field null
        logSystem.setLogin(null);

        // Create the LogSystem, which fails.

        restLogSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logSystem)))
            .andExpect(status().isBadRequest());

        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = logSystemRepository.findAll().size();
        // set the field null
        logSystem.setMessage(null);

        // Create the LogSystem, which fails.

        restLogSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logSystem)))
            .andExpect(status().isBadRequest());

        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLogSystems() throws Exception {
        // Initialize the database
        logSystemRepository.saveAndFlush(logSystem);

        // Get all the logSystemList
        restLogSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getLogSystem() throws Exception {
        // Initialize the database
        logSystemRepository.saveAndFlush(logSystem);

        // Get the logSystem
        restLogSystemMockMvc
            .perform(get(ENTITY_API_URL_ID, logSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logSystem.getId().intValue()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingLogSystem() throws Exception {
        // Get the logSystem
        restLogSystemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLogSystem() throws Exception {
        // Initialize the database
        logSystemRepository.saveAndFlush(logSystem);

        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();

        // Update the logSystem
        LogSystem updatedLogSystem = logSystemRepository.findById(logSystem.getId()).get();
        // Disconnect from session so that the updates on updatedLogSystem are not directly saved in db
        em.detach(updatedLogSystem);
        updatedLogSystem.eventDate(UPDATED_EVENT_DATE).login(UPDATED_LOGIN).message(UPDATED_MESSAGE);

        restLogSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLogSystem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLogSystem))
            )
            .andExpect(status().isOk());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
        LogSystem testLogSystem = logSystemList.get(logSystemList.size() - 1);
        assertThat(testLogSystem.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testLogSystem.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testLogSystem.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingLogSystem() throws Exception {
        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();
        logSystem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, logSystem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLogSystem() throws Exception {
        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();
        logSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(logSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLogSystem() throws Exception {
        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();
        logSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogSystemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(logSystem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLogSystemWithPatch() throws Exception {
        // Initialize the database
        logSystemRepository.saveAndFlush(logSystem);

        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();

        // Update the logSystem using partial update
        LogSystem partialUpdatedLogSystem = new LogSystem();
        partialUpdatedLogSystem.setId(logSystem.getId());

        partialUpdatedLogSystem.eventDate(UPDATED_EVENT_DATE).login(UPDATED_LOGIN);

        restLogSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogSystem))
            )
            .andExpect(status().isOk());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
        LogSystem testLogSystem = logSystemList.get(logSystemList.size() - 1);
        assertThat(testLogSystem.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testLogSystem.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testLogSystem.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateLogSystemWithPatch() throws Exception {
        // Initialize the database
        logSystemRepository.saveAndFlush(logSystem);

        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();

        // Update the logSystem using partial update
        LogSystem partialUpdatedLogSystem = new LogSystem();
        partialUpdatedLogSystem.setId(logSystem.getId());

        partialUpdatedLogSystem.eventDate(UPDATED_EVENT_DATE).login(UPDATED_LOGIN).message(UPDATED_MESSAGE);

        restLogSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLogSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLogSystem))
            )
            .andExpect(status().isOk());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
        LogSystem testLogSystem = logSystemList.get(logSystemList.size() - 1);
        assertThat(testLogSystem.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testLogSystem.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testLogSystem.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingLogSystem() throws Exception {
        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();
        logSystem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, logSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLogSystem() throws Exception {
        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();
        logSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(logSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLogSystem() throws Exception {
        int databaseSizeBeforeUpdate = logSystemRepository.findAll().size();
        logSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLogSystemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(logSystem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LogSystem in the database
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLogSystem() throws Exception {
        // Initialize the database
        logSystemRepository.saveAndFlush(logSystem);

        int databaseSizeBeforeDelete = logSystemRepository.findAll().size();

        // Delete the logSystem
        restLogSystemMockMvc
            .perform(delete(ENTITY_API_URL_ID, logSystem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogSystem> logSystemList = logSystemRepository.findAll();
        assertThat(logSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

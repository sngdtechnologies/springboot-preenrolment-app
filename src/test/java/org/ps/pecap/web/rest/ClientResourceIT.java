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
import org.ps.pecap.domain.Client;
import org.ps.pecap.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ANNEE_NAISS = "AAAA";
    private static final String UPDATED_ANNEE_NAISS = "BBBB";

    private static final String DEFAULT_LIEU_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISS = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_DEMANDE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DEMANDE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DEST_VOYAGE_P = "AAAAAAAAAA";
    private static final String UPDATED_DEST_VOYAGE_P = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF_DEPLACEMENT = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_DEPLACEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAISS = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTE_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTE_NAISS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_RUE = "AAAAAAAAAA";
    private static final String UPDATED_RUE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MERE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MERE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MERE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_PERE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_PERE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PERE = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT_CNI = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT_CNI = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_CNI = 1;
    private static final Integer UPDATED_NUMERO_CNI = 2;

    private static final LocalDate DEFAULT_DATE_DELIV_CNI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELIV_CNI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXP_CNI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXP_CNI = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .photoUrl(DEFAULT_PHOTO_URL)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .anneeNaiss(DEFAULT_ANNEE_NAISS)
            .lieuNaiss(DEFAULT_LIEU_NAISS)
            .genre(DEFAULT_GENRE)
            .typeDemande(DEFAULT_TYPE_DEMANDE)
            .email(DEFAULT_EMAIL)
            .destVoyageP(DEFAULT_DEST_VOYAGE_P)
            .motifDeplacement(DEFAULT_MOTIF_DEPLACEMENT)
            .paysNaissance(DEFAULT_PAYS_NAISSANCE)
            .regionNaiss(DEFAULT_REGION_NAISS)
            .departeNaiss(DEFAULT_DEPARTE_NAISS)
            .telephone(DEFAULT_TELEPHONE)
            .pays(DEFAULT_PAYS)
            .region(DEFAULT_REGION)
            .departement(DEFAULT_DEPARTEMENT)
            .lieu(DEFAULT_LIEU)
            .rue(DEFAULT_RUE)
            .profession(DEFAULT_PROFESSION)
            .prenomMere(DEFAULT_PRENOM_MERE)
            .nomMere(DEFAULT_NOM_MERE)
            .prenomPere(DEFAULT_PRENOM_PERE)
            .nomPere(DEFAULT_NOM_PERE)
            .formatCni(DEFAULT_FORMAT_CNI)
            .numeroCni(DEFAULT_NUMERO_CNI)
            .dateDelivCni(DEFAULT_DATE_DELIV_CNI)
            .dateExpCni(DEFAULT_DATE_EXP_CNI);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photoUrl(UPDATED_PHOTO_URL)
            .dateNaiss(UPDATED_DATE_NAISS)
            .anneeNaiss(UPDATED_ANNEE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .genre(UPDATED_GENRE)
            .typeDemande(UPDATED_TYPE_DEMANDE)
            .email(UPDATED_EMAIL)
            .destVoyageP(UPDATED_DEST_VOYAGE_P)
            .motifDeplacement(UPDATED_MOTIF_DEPLACEMENT)
            .paysNaissance(UPDATED_PAYS_NAISSANCE)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departeNaiss(UPDATED_DEPARTE_NAISS)
            .telephone(UPDATED_TELEPHONE)
            .pays(UPDATED_PAYS)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .lieu(UPDATED_LIEU)
            .rue(UPDATED_RUE)
            .profession(UPDATED_PROFESSION)
            .prenomMere(UPDATED_PRENOM_MERE)
            .nomMere(UPDATED_NOM_MERE)
            .prenomPere(UPDATED_PRENOM_PERE)
            .nomPere(UPDATED_NOM_PERE)
            .formatCni(UPDATED_FORMAT_CNI)
            .numeroCni(UPDATED_NUMERO_CNI)
            .dateDelivCni(UPDATED_DATE_DELIV_CNI)
            .dateExpCni(UPDATED_DATE_EXP_CNI);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testClient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testClient.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testClient.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testClient.getAnneeNaiss()).isEqualTo(DEFAULT_ANNEE_NAISS);
        assertThat(testClient.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testClient.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testClient.getTypeDemande()).isEqualTo(DEFAULT_TYPE_DEMANDE);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getDestVoyageP()).isEqualTo(DEFAULT_DEST_VOYAGE_P);
        assertThat(testClient.getMotifDeplacement()).isEqualTo(DEFAULT_MOTIF_DEPLACEMENT);
        assertThat(testClient.getPaysNaissance()).isEqualTo(DEFAULT_PAYS_NAISSANCE);
        assertThat(testClient.getRegionNaiss()).isEqualTo(DEFAULT_REGION_NAISS);
        assertThat(testClient.getDeparteNaiss()).isEqualTo(DEFAULT_DEPARTE_NAISS);
        assertThat(testClient.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testClient.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testClient.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testClient.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testClient.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testClient.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testClient.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testClient.getPrenomMere()).isEqualTo(DEFAULT_PRENOM_MERE);
        assertThat(testClient.getNomMere()).isEqualTo(DEFAULT_NOM_MERE);
        assertThat(testClient.getPrenomPere()).isEqualTo(DEFAULT_PRENOM_PERE);
        assertThat(testClient.getNomPere()).isEqualTo(DEFAULT_NOM_PERE);
        assertThat(testClient.getFormatCni()).isEqualTo(DEFAULT_FORMAT_CNI);
        assertThat(testClient.getNumeroCni()).isEqualTo(DEFAULT_NUMERO_CNI);
        assertThat(testClient.getDateDelivCni()).isEqualTo(DEFAULT_DATE_DELIV_CNI);
        assertThat(testClient.getDateExpCni()).isEqualTo(DEFAULT_DATE_EXP_CNI);
    }

    @Test
    @Transactional
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId(1L);

        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setNom(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPrenom(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateNaissIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDateNaiss(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLieuNaissIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setLieuNaiss(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setGenre(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeDemandeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTypeDemande(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setEmail(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDestVoyagePIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDestVoyageP(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMotifDeplacementIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setMotifDeplacement(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaysNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPaysNaissance(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRegionNaissIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setRegionNaiss(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeparteNaissIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDeparteNaiss(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTelephone(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPays(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setRegion(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartementIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDepartement(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLieuIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setLieu(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRueIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setRue(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProfessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setProfession(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomMereIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPrenomMere(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomMereIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setNomMere(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomPereIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPrenomPere(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomPereIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setNomPere(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormatCniIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setFormatCni(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroCniIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setNumeroCni(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDelivCniIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDateDelivCni(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateExpCniIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDateExpCni(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].anneeNaiss").value(hasItem(DEFAULT_ANNEE_NAISS)))
            .andExpect(jsonPath("$.[*].lieuNaiss").value(hasItem(DEFAULT_LIEU_NAISS)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].typeDemande").value(hasItem(DEFAULT_TYPE_DEMANDE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].destVoyageP").value(hasItem(DEFAULT_DEST_VOYAGE_P)))
            .andExpect(jsonPath("$.[*].motifDeplacement").value(hasItem(DEFAULT_MOTIF_DEPLACEMENT)))
            .andExpect(jsonPath("$.[*].paysNaissance").value(hasItem(DEFAULT_PAYS_NAISSANCE)))
            .andExpect(jsonPath("$.[*].regionNaiss").value(hasItem(DEFAULT_REGION_NAISS)))
            .andExpect(jsonPath("$.[*].departeNaiss").value(hasItem(DEFAULT_DEPARTE_NAISS)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT)))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU)))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE)))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].prenomMere").value(hasItem(DEFAULT_PRENOM_MERE)))
            .andExpect(jsonPath("$.[*].nomMere").value(hasItem(DEFAULT_NOM_MERE)))
            .andExpect(jsonPath("$.[*].prenomPere").value(hasItem(DEFAULT_PRENOM_PERE)))
            .andExpect(jsonPath("$.[*].nomPere").value(hasItem(DEFAULT_NOM_PERE)))
            .andExpect(jsonPath("$.[*].formatCni").value(hasItem(DEFAULT_FORMAT_CNI)))
            .andExpect(jsonPath("$.[*].numeroCni").value(hasItem(DEFAULT_NUMERO_CNI)))
            .andExpect(jsonPath("$.[*].dateDelivCni").value(hasItem(DEFAULT_DATE_DELIV_CNI.toString())))
            .andExpect(jsonPath("$.[*].dateExpCni").value(hasItem(DEFAULT_DATE_EXP_CNI.toString())));
    }

    @Test
    @Transactional
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.anneeNaiss").value(DEFAULT_ANNEE_NAISS))
            .andExpect(jsonPath("$.lieuNaiss").value(DEFAULT_LIEU_NAISS))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.typeDemande").value(DEFAULT_TYPE_DEMANDE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.destVoyageP").value(DEFAULT_DEST_VOYAGE_P))
            .andExpect(jsonPath("$.motifDeplacement").value(DEFAULT_MOTIF_DEPLACEMENT))
            .andExpect(jsonPath("$.paysNaissance").value(DEFAULT_PAYS_NAISSANCE))
            .andExpect(jsonPath("$.regionNaiss").value(DEFAULT_REGION_NAISS))
            .andExpect(jsonPath("$.departeNaiss").value(DEFAULT_DEPARTE_NAISS))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU))
            .andExpect(jsonPath("$.rue").value(DEFAULT_RUE))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.prenomMere").value(DEFAULT_PRENOM_MERE))
            .andExpect(jsonPath("$.nomMere").value(DEFAULT_NOM_MERE))
            .andExpect(jsonPath("$.prenomPere").value(DEFAULT_PRENOM_PERE))
            .andExpect(jsonPath("$.nomPere").value(DEFAULT_NOM_PERE))
            .andExpect(jsonPath("$.formatCni").value(DEFAULT_FORMAT_CNI))
            .andExpect(jsonPath("$.numeroCni").value(DEFAULT_NUMERO_CNI))
            .andExpect(jsonPath("$.dateDelivCni").value(DEFAULT_DATE_DELIV_CNI.toString()))
            .andExpect(jsonPath("$.dateExpCni").value(DEFAULT_DATE_EXP_CNI.toString()));
    }

    @Test
    @Transactional
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photoUrl(UPDATED_PHOTO_URL)
            .dateNaiss(UPDATED_DATE_NAISS)
            .anneeNaiss(UPDATED_ANNEE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .genre(UPDATED_GENRE)
            .typeDemande(UPDATED_TYPE_DEMANDE)
            .email(UPDATED_EMAIL)
            .destVoyageP(UPDATED_DEST_VOYAGE_P)
            .motifDeplacement(UPDATED_MOTIF_DEPLACEMENT)
            .paysNaissance(UPDATED_PAYS_NAISSANCE)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departeNaiss(UPDATED_DEPARTE_NAISS)
            .telephone(UPDATED_TELEPHONE)
            .pays(UPDATED_PAYS)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .lieu(UPDATED_LIEU)
            .rue(UPDATED_RUE)
            .profession(UPDATED_PROFESSION)
            .prenomMere(UPDATED_PRENOM_MERE)
            .nomMere(UPDATED_NOM_MERE)
            .prenomPere(UPDATED_PRENOM_PERE)
            .nomPere(UPDATED_NOM_PERE)
            .formatCni(UPDATED_FORMAT_CNI)
            .numeroCni(UPDATED_NUMERO_CNI)
            .dateDelivCni(UPDATED_DATE_DELIV_CNI)
            .dateExpCni(UPDATED_DATE_EXP_CNI);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testClient.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testClient.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testClient.getAnneeNaiss()).isEqualTo(UPDATED_ANNEE_NAISS);
        assertThat(testClient.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testClient.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testClient.getTypeDemande()).isEqualTo(UPDATED_TYPE_DEMANDE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getDestVoyageP()).isEqualTo(UPDATED_DEST_VOYAGE_P);
        assertThat(testClient.getMotifDeplacement()).isEqualTo(UPDATED_MOTIF_DEPLACEMENT);
        assertThat(testClient.getPaysNaissance()).isEqualTo(UPDATED_PAYS_NAISSANCE);
        assertThat(testClient.getRegionNaiss()).isEqualTo(UPDATED_REGION_NAISS);
        assertThat(testClient.getDeparteNaiss()).isEqualTo(UPDATED_DEPARTE_NAISS);
        assertThat(testClient.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testClient.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testClient.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testClient.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testClient.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testClient.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testClient.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testClient.getPrenomMere()).isEqualTo(UPDATED_PRENOM_MERE);
        assertThat(testClient.getNomMere()).isEqualTo(UPDATED_NOM_MERE);
        assertThat(testClient.getPrenomPere()).isEqualTo(UPDATED_PRENOM_PERE);
        assertThat(testClient.getNomPere()).isEqualTo(UPDATED_NOM_PERE);
        assertThat(testClient.getFormatCni()).isEqualTo(UPDATED_FORMAT_CNI);
        assertThat(testClient.getNumeroCni()).isEqualTo(UPDATED_NUMERO_CNI);
        assertThat(testClient.getDateDelivCni()).isEqualTo(UPDATED_DATE_DELIV_CNI);
        assertThat(testClient.getDateExpCni()).isEqualTo(UPDATED_DATE_EXP_CNI);
    }

    @Test
    @Transactional
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photoUrl(UPDATED_PHOTO_URL)
            .genre(UPDATED_GENRE)
            .email(UPDATED_EMAIL)
            .destVoyageP(UPDATED_DEST_VOYAGE_P)
            .motifDeplacement(UPDATED_MOTIF_DEPLACEMENT)
            .paysNaissance(UPDATED_PAYS_NAISSANCE)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departeNaiss(UPDATED_DEPARTE_NAISS)
            .region(UPDATED_REGION)
            .rue(UPDATED_RUE)
            .profession(UPDATED_PROFESSION)
            .prenomPere(UPDATED_PRENOM_PERE)
            .nomPere(UPDATED_NOM_PERE)
            .formatCni(UPDATED_FORMAT_CNI)
            .dateDelivCni(UPDATED_DATE_DELIV_CNI)
            .dateExpCni(UPDATED_DATE_EXP_CNI);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testClient.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testClient.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testClient.getAnneeNaiss()).isEqualTo(DEFAULT_ANNEE_NAISS);
        assertThat(testClient.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testClient.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testClient.getTypeDemande()).isEqualTo(DEFAULT_TYPE_DEMANDE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getDestVoyageP()).isEqualTo(UPDATED_DEST_VOYAGE_P);
        assertThat(testClient.getMotifDeplacement()).isEqualTo(UPDATED_MOTIF_DEPLACEMENT);
        assertThat(testClient.getPaysNaissance()).isEqualTo(UPDATED_PAYS_NAISSANCE);
        assertThat(testClient.getRegionNaiss()).isEqualTo(UPDATED_REGION_NAISS);
        assertThat(testClient.getDeparteNaiss()).isEqualTo(UPDATED_DEPARTE_NAISS);
        assertThat(testClient.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testClient.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testClient.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testClient.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testClient.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testClient.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testClient.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testClient.getPrenomMere()).isEqualTo(DEFAULT_PRENOM_MERE);
        assertThat(testClient.getNomMere()).isEqualTo(DEFAULT_NOM_MERE);
        assertThat(testClient.getPrenomPere()).isEqualTo(UPDATED_PRENOM_PERE);
        assertThat(testClient.getNomPere()).isEqualTo(UPDATED_NOM_PERE);
        assertThat(testClient.getFormatCni()).isEqualTo(UPDATED_FORMAT_CNI);
        assertThat(testClient.getNumeroCni()).isEqualTo(DEFAULT_NUMERO_CNI);
        assertThat(testClient.getDateDelivCni()).isEqualTo(UPDATED_DATE_DELIV_CNI);
        assertThat(testClient.getDateExpCni()).isEqualTo(UPDATED_DATE_EXP_CNI);
    }

    @Test
    @Transactional
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photoUrl(UPDATED_PHOTO_URL)
            .dateNaiss(UPDATED_DATE_NAISS)
            .anneeNaiss(UPDATED_ANNEE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .genre(UPDATED_GENRE)
            .typeDemande(UPDATED_TYPE_DEMANDE)
            .email(UPDATED_EMAIL)
            .destVoyageP(UPDATED_DEST_VOYAGE_P)
            .motifDeplacement(UPDATED_MOTIF_DEPLACEMENT)
            .paysNaissance(UPDATED_PAYS_NAISSANCE)
            .regionNaiss(UPDATED_REGION_NAISS)
            .departeNaiss(UPDATED_DEPARTE_NAISS)
            .telephone(UPDATED_TELEPHONE)
            .pays(UPDATED_PAYS)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .lieu(UPDATED_LIEU)
            .rue(UPDATED_RUE)
            .profession(UPDATED_PROFESSION)
            .prenomMere(UPDATED_PRENOM_MERE)
            .nomMere(UPDATED_NOM_MERE)
            .prenomPere(UPDATED_PRENOM_PERE)
            .nomPere(UPDATED_NOM_PERE)
            .formatCni(UPDATED_FORMAT_CNI)
            .numeroCni(UPDATED_NUMERO_CNI)
            .dateDelivCni(UPDATED_DATE_DELIV_CNI)
            .dateExpCni(UPDATED_DATE_EXP_CNI);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testClient.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testClient.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testClient.getAnneeNaiss()).isEqualTo(UPDATED_ANNEE_NAISS);
        assertThat(testClient.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testClient.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testClient.getTypeDemande()).isEqualTo(UPDATED_TYPE_DEMANDE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getDestVoyageP()).isEqualTo(UPDATED_DEST_VOYAGE_P);
        assertThat(testClient.getMotifDeplacement()).isEqualTo(UPDATED_MOTIF_DEPLACEMENT);
        assertThat(testClient.getPaysNaissance()).isEqualTo(UPDATED_PAYS_NAISSANCE);
        assertThat(testClient.getRegionNaiss()).isEqualTo(UPDATED_REGION_NAISS);
        assertThat(testClient.getDeparteNaiss()).isEqualTo(UPDATED_DEPARTE_NAISS);
        assertThat(testClient.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testClient.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testClient.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testClient.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testClient.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testClient.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testClient.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testClient.getPrenomMere()).isEqualTo(UPDATED_PRENOM_MERE);
        assertThat(testClient.getNomMere()).isEqualTo(UPDATED_NOM_MERE);
        assertThat(testClient.getPrenomPere()).isEqualTo(UPDATED_PRENOM_PERE);
        assertThat(testClient.getNomPere()).isEqualTo(UPDATED_NOM_PERE);
        assertThat(testClient.getFormatCni()).isEqualTo(UPDATED_FORMAT_CNI);
        assertThat(testClient.getNumeroCni()).isEqualTo(UPDATED_NUMERO_CNI);
        assertThat(testClient.getDateDelivCni()).isEqualTo(UPDATED_DATE_DELIV_CNI);
        assertThat(testClient.getDateExpCni()).isEqualTo(UPDATED_DATE_EXP_CNI);
    }

    @Test
    @Transactional
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, client.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

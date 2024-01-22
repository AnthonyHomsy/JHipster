package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Recette;
import com.mycompany.myapp.repository.RecetteRepository;
import com.mycompany.myapp.service.RecetteService;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RecetteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RecetteResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recettes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecetteRepository recetteRepository;

    @Mock
    private RecetteRepository recetteRepositoryMock;

    @Mock
    private RecetteService recetteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecetteMockMvc;

    private Recette recette;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recette createEntity(EntityManager em) {
        Recette recette = new Recette().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION);
        return recette;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recette createUpdatedEntity(EntityManager em) {
        Recette recette = new Recette().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
        return recette;
    }

    @BeforeEach
    public void initTest() {
        recette = createEntity(em);
    }

    @Test
    @Transactional
    void createRecette() throws Exception {
        int databaseSizeBeforeCreate = recetteRepository.findAll().size();
        // Create the Recette
        restRecetteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recette)))
            .andExpect(status().isCreated());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeCreate + 1);
        Recette testRecette = recetteList.get(recetteList.size() - 1);
        assertThat(testRecette.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRecette.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRecetteWithExistingId() throws Exception {
        // Create the Recette with an existing ID
        recette.setId(1L);

        int databaseSizeBeforeCreate = recetteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecetteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recette)))
            .andExpect(status().isBadRequest());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = recetteRepository.findAll().size();
        // set the field null
        recette.setTitle(null);

        // Create the Recette, which fails.

        restRecetteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recette)))
            .andExpect(status().isBadRequest());

        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recetteRepository.findAll().size();
        // set the field null
        recette.setDescription(null);

        // Create the Recette, which fails.

        restRecetteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recette)))
            .andExpect(status().isBadRequest());

        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecettes() throws Exception {
        // Initialize the database
        recetteRepository.saveAndFlush(recette);

        // Get all the recetteList
        restRecetteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recette.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecettesWithEagerRelationshipsIsEnabled() throws Exception {
        when(recetteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecetteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(recetteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecettesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(recetteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecetteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(recetteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRecette() throws Exception {
        // Initialize the database
        recetteRepository.saveAndFlush(recette);

        // Get the recette
        restRecetteMockMvc
            .perform(get(ENTITY_API_URL_ID, recette.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recette.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRecette() throws Exception {
        // Get the recette
        restRecetteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecette() throws Exception {
        // Initialize the database
        recetteRepository.saveAndFlush(recette);

        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();

        // Update the recette
        Recette updatedRecette = recetteRepository.findById(recette.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecette are not directly saved in db
        em.detach(updatedRecette);
        updatedRecette.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restRecetteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecette.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecette))
            )
            .andExpect(status().isOk());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
        Recette testRecette = recetteList.get(recetteList.size() - 1);
        assertThat(testRecette.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRecette.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRecette() throws Exception {
        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();
        recette.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecetteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recette.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recette))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecette() throws Exception {
        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();
        recette.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recette))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecette() throws Exception {
        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();
        recette.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recette)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecetteWithPatch() throws Exception {
        // Initialize the database
        recetteRepository.saveAndFlush(recette);

        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();

        // Update the recette using partial update
        Recette partialUpdatedRecette = new Recette();
        partialUpdatedRecette.setId(recette.getId());

        partialUpdatedRecette.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restRecetteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecette))
            )
            .andExpect(status().isOk());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
        Recette testRecette = recetteList.get(recetteList.size() - 1);
        assertThat(testRecette.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRecette.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRecetteWithPatch() throws Exception {
        // Initialize the database
        recetteRepository.saveAndFlush(recette);

        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();

        // Update the recette using partial update
        Recette partialUpdatedRecette = new Recette();
        partialUpdatedRecette.setId(recette.getId());

        partialUpdatedRecette.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restRecetteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecette))
            )
            .andExpect(status().isOk());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
        Recette testRecette = recetteList.get(recetteList.size() - 1);
        assertThat(testRecette.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRecette.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRecette() throws Exception {
        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();
        recette.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecetteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recette))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecette() throws Exception {
        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();
        recette.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recette))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecette() throws Exception {
        int databaseSizeBeforeUpdate = recetteRepository.findAll().size();
        recette.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(recette)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recette in the database
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecette() throws Exception {
        // Initialize the database
        recetteRepository.saveAndFlush(recette);

        int databaseSizeBeforeDelete = recetteRepository.findAll().size();

        // Delete the recette
        restRecetteMockMvc
            .perform(delete(ENTITY_API_URL_ID, recette.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recette> recetteList = recetteRepository.findAll();
        assertThat(recetteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

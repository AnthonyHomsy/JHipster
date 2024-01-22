package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.LikeTestSamples.*;
import static com.mycompany.myapp.domain.RecetteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LikeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Like.class);
        Like like1 = getLikeSample1();
        Like like2 = new Like();
        assertThat(like1).isNotEqualTo(like2);

        like2.setId(like1.getId());
        assertThat(like1).isEqualTo(like2);

        like2 = getLikeSample2();
        assertThat(like1).isNotEqualTo(like2);
    }

    @Test
    void recetteTest() throws Exception {
        Like like = getLikeRandomSampleGenerator();
        Recette recetteBack = getRecetteRandomSampleGenerator();

        like.setRecette(recetteBack);
        assertThat(like.getRecette()).isEqualTo(recetteBack);

        like.recette(null);
        assertThat(like.getRecette()).isNull();
    }
}

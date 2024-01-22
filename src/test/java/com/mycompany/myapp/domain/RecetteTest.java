package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CommentTestSamples.*;
import static com.mycompany.myapp.domain.LikeTestSamples.*;
import static com.mycompany.myapp.domain.RecetteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RecetteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recette.class);
        Recette recette1 = getRecetteSample1();
        Recette recette2 = new Recette();
        assertThat(recette1).isNotEqualTo(recette2);

        recette2.setId(recette1.getId());
        assertThat(recette1).isEqualTo(recette2);

        recette2 = getRecetteSample2();
        assertThat(recette1).isNotEqualTo(recette2);
    }

    @Test
    void commentTest() throws Exception {
        Recette recette = getRecetteRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        recette.addComment(commentBack);
        assertThat(recette.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getRecette()).isEqualTo(recette);

        recette.removeComment(commentBack);
        assertThat(recette.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getRecette()).isNull();

        recette.comments(new HashSet<>(Set.of(commentBack)));
        assertThat(recette.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getRecette()).isEqualTo(recette);

        recette.setComments(new HashSet<>());
        assertThat(recette.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getRecette()).isNull();
    }

    @Test
    void likeTest() throws Exception {
        Recette recette = getRecetteRandomSampleGenerator();
        Like likeBack = getLikeRandomSampleGenerator();

        recette.addLike(likeBack);
        assertThat(recette.getLikes()).containsOnly(likeBack);
        assertThat(likeBack.getRecette()).isEqualTo(recette);

        recette.removeLike(likeBack);
        assertThat(recette.getLikes()).doesNotContain(likeBack);
        assertThat(likeBack.getRecette()).isNull();

        recette.likes(new HashSet<>(Set.of(likeBack)));
        assertThat(recette.getLikes()).containsOnly(likeBack);
        assertThat(likeBack.getRecette()).isEqualTo(recette);

        recette.setLikes(new HashSet<>());
        assertThat(recette.getLikes()).doesNotContain(likeBack);
        assertThat(likeBack.getRecette()).isNull();
    }
}

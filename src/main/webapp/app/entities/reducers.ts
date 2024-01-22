import recette from 'app/entities/recette/recette.reducer';
import comment from 'app/entities/comment/comment.reducer';
import like from 'app/entities/like/like.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  recette,
  comment,
  like,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

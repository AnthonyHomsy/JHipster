import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recette.reducer';

export const RecetteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recetteEntity = useAppSelector(state => state.recette.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recetteDetailsHeading">Recette</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{recetteEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{recetteEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{recetteEntity.description}</dd>
          <dt>User</dt>
          <dd>{recetteEntity.user ? recetteEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/recette" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recette/${recetteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecetteDetail;

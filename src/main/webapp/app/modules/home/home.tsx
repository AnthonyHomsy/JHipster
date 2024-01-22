import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.recettes);

  console.log(account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <h1 className="display-4">Bienvenue sur Quizzine ! </h1>
        <p className="lead">Premier site de recette en France !</p>
        {account?.login ? (
          <div>
            <Alert color="success">Vous etes maintenant en ligne &quot;{account.login}&quot;.</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              Si vous voulez vous
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                connecter
              </Link>
              , vous pouvez utiliser les comptes par defaut generer par l'equipe IAY
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
              password=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              Vous n'avez pas de compte
              <Link to="/account/register" className="alert-link">
                Creer un compte
              </Link>
            </Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;

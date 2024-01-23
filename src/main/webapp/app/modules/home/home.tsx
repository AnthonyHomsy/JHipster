import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import ListRecette from 'app/modules/Recette/ListRecette';
import AddIcon from '@mui/icons-material/Add';
import { Fab } from '@mui/material';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  console.log(account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        {/*<h1 className="display-4">Welcome, Java Hipster!</h1>*/}
        {/*<p className="lead">This is your homepage</p>*/}
        {account?.login ? (
          <div>
            {/*<Alert color="success">You are logged in as user &quot;{account.login}&quot;.</Alert>*/}
            <ListRecette />
          </div>
        ) : (
          <div>
            <Alert color="warning">
              If you want to
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                sign in
              </Link>
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
              password=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              You don&apos;t have an account yet?&nbsp;
              <Link to="/account/register" className="alert-link">
                Register a new account
              </Link>
            </Alert>
          </div>
        )}
      </Col>
      <div>
        <Fab color="primary" aria-label="add">
          <AddIcon />
        </Fab>
      </div>
    </Row>
  );
};

export default Home;

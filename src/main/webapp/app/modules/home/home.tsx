import './home.scss';

import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { Button } from 'primereact/button';
import { START_PREENROLE } from 'app/shared/const/route.const';

export const Home = () => {
  const navigate = useNavigate();
  const account = useAppSelector(state => state.authentication.account);

  const handleClickBegin = () => {
    navigate(START_PREENROLE);
  };

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9" className="py-4">
        <h2>
          <Translate contentKey="home.title">Bienvenue sur le portail de pré-enrôlement du passeport électronique du Cameroun</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">Ceci est notre page d'accueil</Translate>
        </p>
        <p>
          <Translate contentKey="home.question">Si vous avez des questions à propos de Pecap, contactez le service client : </Translate>
        </p>
        <ul>
          <li>
            <a href="tel:+237653035632" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.phone">+(237) 6 53 03 56 32</Translate>
            </a>
          </li>
          <li>
            <a href="mailto:sngdtechnologies@gmail.com" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.mail">sngdtechnologies@gmail.com</Translate>
            </a>
          </li>
          <li>
            <a href="#" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.info">Plus d'info sur nous</Translate>
            </a>
          </li>
        </ul>
        <Button label="Commencer" raised onClick={handleClickBegin} />
      </Col>
    </Row>
  );
};

export default Home;

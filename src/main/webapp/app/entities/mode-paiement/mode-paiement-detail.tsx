import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mode-paiement.reducer';

export const ModePaiementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const modePaiementEntity = useAppSelector(state => state.modePaiement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="modePaiementDetailsHeading">
          <Translate contentKey="pecapApp.modePaiement.detail.title">ModePaiement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{modePaiementEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="pecapApp.modePaiement.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{modePaiementEntity.nom}</dd>
          <dt>
            <Translate contentKey="pecapApp.modePaiement.annee">Annee</Translate>
          </dt>
          <dd>{modePaiementEntity.annee ? modePaiementEntity.annee.id : ''}</dd>
          <dt>
            <Translate contentKey="pecapApp.modePaiement.passport">Passport</Translate>
          </dt>
          <dd>{modePaiementEntity.passport ? modePaiementEntity.passport.id : ''}</dd>
          <dt>
            <Translate contentKey="pecapApp.modePaiement.client">Client</Translate>
          </dt>
          <dd>{modePaiementEntity.client ? modePaiementEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/mode-paiement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mode-paiement/${modePaiementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ModePaiementDetail;

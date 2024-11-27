import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './etat-procedure.reducer';

export const EtatProcedureDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const etatProcedureEntity = useAppSelector(state => state.etatProcedure.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="etatProcedureDetailsHeading">
          <Translate contentKey="pecapApp.etatProcedure.detail.title">EtatProcedure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{etatProcedureEntity.id}</dd>
          <dt>
            <span id="etatPreEnrole">
              <Translate contentKey="pecapApp.etatProcedure.etatPreEnrole">Etat Pre Enrole</Translate>
            </span>
          </dt>
          <dd>{etatProcedureEntity.etatPreEnrole}</dd>
          <dt>
            <span id="etatEnrole">
              <Translate contentKey="pecapApp.etatProcedure.etatEnrole">Etat Enrole</Translate>
            </span>
          </dt>
          <dd>{etatProcedureEntity.etatEnrole}</dd>
          <dt>
            <span id="etatRetrait">
              <Translate contentKey="pecapApp.etatProcedure.etatRetrait">Etat Retrait</Translate>
            </span>
          </dt>
          <dd>{etatProcedureEntity.etatRetrait}</dd>
          <dt>
            <Translate contentKey="pecapApp.etatProcedure.annee">Annee</Translate>
          </dt>
          <dd>{etatProcedureEntity.annee ? etatProcedureEntity.annee.id : ''}</dd>
          <dt>
            <Translate contentKey="pecapApp.etatProcedure.client">Client</Translate>
          </dt>
          <dd>{etatProcedureEntity.client ? etatProcedureEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/etat-procedure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etat-procedure/${etatProcedureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EtatProcedureDetail;

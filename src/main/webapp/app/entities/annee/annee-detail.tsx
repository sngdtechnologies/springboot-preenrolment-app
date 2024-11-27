import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './annee.reducer';

export const AnneeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const anneeEntity = useAppSelector(state => state.annee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="anneeDetailsHeading">
          <Translate contentKey="pecapApp.annee.detail.title">Annee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{anneeEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="pecapApp.annee.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{anneeEntity.nom}</dd>
          <dt>
            <span id="dateDebut">
              <Translate contentKey="pecapApp.annee.dateDebut">Date Debut</Translate>
            </span>
          </dt>
          <dd>{anneeEntity.dateDebut ? <TextFormat value={anneeEntity.dateDebut} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="dateFin">
              <Translate contentKey="pecapApp.annee.dateFin">Date Fin</Translate>
            </span>
          </dt>
          <dd>{anneeEntity.dateFin ? <TextFormat value={anneeEntity.dateFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="isVerrouiller">
              <Translate contentKey="pecapApp.annee.isVerrouiller">Is Verrouiller</Translate>
            </span>
          </dt>
          <dd>{anneeEntity.isVerrouiller ? 'true' : 'false'}</dd>
          <dt>
            <span id="isCloturer">
              <Translate contentKey="pecapApp.annee.isCloturer">Is Cloturer</Translate>
            </span>
          </dt>
          <dd>{anneeEntity.isCloturer ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/annee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/annee/${anneeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnneeDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './acces.reducer';

export const AccesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const accesEntity = useAppSelector(state => state.acces.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accesDetailsHeading">
          <Translate contentKey="pecapApp.acces.detail.title">Acces</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accesEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="pecapApp.acces.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{accesEntity.nom}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="pecapApp.acces.code">Code</Translate>
            </span>
          </dt>
          <dd>{accesEntity.code}</dd>
          <dt>
            <Translate contentKey="pecapApp.acces.annee">Annee</Translate>
          </dt>
          <dd>{accesEntity.annee ? accesEntity.annee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/acces" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/acces/${accesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccesDetail;

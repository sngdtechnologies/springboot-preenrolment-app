import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './type-passport.reducer';

export const TypePassportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const typePassportEntity = useAppSelector(state => state.typePassport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="typePassportDetailsHeading">
          <Translate contentKey="pecapApp.typePassport.detail.title">TypePassport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{typePassportEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="pecapApp.typePassport.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{typePassportEntity.nom}</dd>
          <dt>
            <Translate contentKey="pecapApp.typePassport.annee">Annee</Translate>
          </dt>
          <dd>{typePassportEntity.annee ? typePassportEntity.annee.id : ''}</dd>
          <dt>
            <Translate contentKey="pecapApp.typePassport.passport">Passport</Translate>
          </dt>
          <dd>{typePassportEntity.passport ? typePassportEntity.passport.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/type-passport" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/type-passport/${typePassportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TypePassportDetail;

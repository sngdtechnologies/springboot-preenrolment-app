import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profil.reducer';

export const ProfilDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profilEntity = useAppSelector(state => state.profil.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profilDetailsHeading">
          <Translate contentKey="pecapApp.profil.detail.title">Profil</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profilEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="pecapApp.profil.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{profilEntity.nom}</dd>
          <dt>
            <Translate contentKey="pecapApp.profil.acces">Acces</Translate>
          </dt>
          <dd>
            {profilEntity.acces
              ? profilEntity.acces.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nom}</a>
                    {profilEntity.acces && i === profilEntity.acces.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="pecapApp.profil.annee">Annee</Translate>
          </dt>
          <dd>{profilEntity.annee ? profilEntity.annee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/profil" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profil/${profilEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfilDetail;

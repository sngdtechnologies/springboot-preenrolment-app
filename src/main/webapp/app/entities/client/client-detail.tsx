import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client.reducer';

export const ClientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">
          <Translate contentKey="pecapApp.client.detail.title">Client</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="pecapApp.client.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{clientEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="pecapApp.client.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{clientEntity.prenom}</dd>
          <dt>
            <span id="photoUrl">
              <Translate contentKey="pecapApp.client.photoUrl">Photo Url</Translate>
            </span>
          </dt>
          <dd>{clientEntity.photoUrl}</dd>
          <dt>
            <span id="dateNaiss">
              <Translate contentKey="pecapApp.client.dateNaiss">Date Naiss</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.dateNaiss ? <TextFormat value={clientEntity.dateNaiss} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="anneeNaiss">
              <Translate contentKey="pecapApp.client.anneeNaiss">Annee Naiss</Translate>
            </span>
          </dt>
          <dd>{clientEntity.anneeNaiss}</dd>
          <dt>
            <span id="lieuNaiss">
              <Translate contentKey="pecapApp.client.lieuNaiss">Lieu Naiss</Translate>
            </span>
          </dt>
          <dd>{clientEntity.lieuNaiss}</dd>
          <dt>
            <span id="genre">
              <Translate contentKey="pecapApp.client.genre">Genre</Translate>
            </span>
          </dt>
          <dd>{clientEntity.genre}</dd>
          <dt>
            <span id="typeDemande">
              <Translate contentKey="pecapApp.client.typeDemande">Type Demande</Translate>
            </span>
          </dt>
          <dd>{clientEntity.typeDemande}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="pecapApp.client.email">Email</Translate>
            </span>
          </dt>
          <dd>{clientEntity.email}</dd>
          <dt>
            <span id="destVoyageP">
              <Translate contentKey="pecapApp.client.destVoyageP">Dest Voyage P</Translate>
            </span>
          </dt>
          <dd>{clientEntity.destVoyageP}</dd>
          <dt>
            <span id="motifDeplacement">
              <Translate contentKey="pecapApp.client.motifDeplacement">Motif Deplacement</Translate>
            </span>
          </dt>
          <dd>{clientEntity.motifDeplacement}</dd>
          <dt>
            <span id="paysNaissance">
              <Translate contentKey="pecapApp.client.paysNaissance">Pays Naissance</Translate>
            </span>
          </dt>
          <dd>{clientEntity.paysNaissance}</dd>
          <dt>
            <span id="regionNaiss">
              <Translate contentKey="pecapApp.client.regionNaiss">Region Naiss</Translate>
            </span>
          </dt>
          <dd>{clientEntity.regionNaiss}</dd>
          <dt>
            <span id="departeNaiss">
              <Translate contentKey="pecapApp.client.departeNaiss">Departe Naiss</Translate>
            </span>
          </dt>
          <dd>{clientEntity.departeNaiss}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="pecapApp.client.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{clientEntity.telephone}</dd>
          <dt>
            <span id="pays">
              <Translate contentKey="pecapApp.client.pays">Pays</Translate>
            </span>
          </dt>
          <dd>{clientEntity.pays}</dd>
          <dt>
            <span id="region">
              <Translate contentKey="pecapApp.client.region">Region</Translate>
            </span>
          </dt>
          <dd>{clientEntity.region}</dd>
          <dt>
            <span id="departement">
              <Translate contentKey="pecapApp.client.departement">Departement</Translate>
            </span>
          </dt>
          <dd>{clientEntity.departement}</dd>
          <dt>
            <span id="lieu">
              <Translate contentKey="pecapApp.client.lieu">Lieu</Translate>
            </span>
          </dt>
          <dd>{clientEntity.lieu}</dd>
          <dt>
            <span id="rue">
              <Translate contentKey="pecapApp.client.rue">Rue</Translate>
            </span>
          </dt>
          <dd>{clientEntity.rue}</dd>
          <dt>
            <span id="profession">
              <Translate contentKey="pecapApp.client.profession">Profession</Translate>
            </span>
          </dt>
          <dd>{clientEntity.profession}</dd>
          <dt>
            <span id="prenomMere">
              <Translate contentKey="pecapApp.client.prenomMere">Prenom Mere</Translate>
            </span>
          </dt>
          <dd>{clientEntity.prenomMere}</dd>
          <dt>
            <span id="nomMere">
              <Translate contentKey="pecapApp.client.nomMere">Nom Mere</Translate>
            </span>
          </dt>
          <dd>{clientEntity.nomMere}</dd>
          <dt>
            <span id="prenomPere">
              <Translate contentKey="pecapApp.client.prenomPere">Prenom Pere</Translate>
            </span>
          </dt>
          <dd>{clientEntity.prenomPere}</dd>
          <dt>
            <span id="nomPere">
              <Translate contentKey="pecapApp.client.nomPere">Nom Pere</Translate>
            </span>
          </dt>
          <dd>{clientEntity.nomPere}</dd>
          <dt>
            <span id="formatCni">
              <Translate contentKey="pecapApp.client.formatCni">Format Cni</Translate>
            </span>
          </dt>
          <dd>{clientEntity.formatCni}</dd>
          <dt>
            <span id="numeroCni">
              <Translate contentKey="pecapApp.client.numeroCni">Numero Cni</Translate>
            </span>
          </dt>
          <dd>{clientEntity.numeroCni}</dd>
          <dt>
            <span id="dateDelivCni">
              <Translate contentKey="pecapApp.client.dateDelivCni">Date Deliv Cni</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.dateDelivCni ? <TextFormat value={clientEntity.dateDelivCni} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dateExpCni">
              <Translate contentKey="pecapApp.client.dateExpCni">Date Exp Cni</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.dateExpCni ? <TextFormat value={clientEntity.dateExpCni} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;

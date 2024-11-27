import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { getEntity, updateEntity, createEntity, reset } from './client.reducer';

export const ClientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientEntity = useAppSelector(state => state.client.entity);
  const loading = useAppSelector(state => state.client.loading);
  const updating = useAppSelector(state => state.client.updating);
  const updateSuccess = useAppSelector(state => state.client.updateSuccess);

  const handleClose = () => {
    navigate('/client' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clientEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...clientEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pecapApp.client.home.createOrEditLabel" data-cy="ClientCreateUpdateHeading">
            <Translate contentKey="pecapApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="client-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pecapApp.client.nom')}
                id="client-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.prenom')}
                id="client-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.photoUrl')}
                id="client-photoUrl"
                name="photoUrl"
                data-cy="photoUrl"
                type="text"
              />
              <ValidatedField
                label={translate('pecapApp.client.dateNaiss')}
                id="client-dateNaiss"
                name="dateNaiss"
                data-cy="dateNaiss"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.anneeNaiss')}
                id="client-anneeNaiss"
                name="anneeNaiss"
                data-cy="anneeNaiss"
                type="text"
                validate={{
                  maxLength: { value: 4, message: translate('entity.validation.maxlength', { max: 4 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.lieuNaiss')}
                id="client-lieuNaiss"
                name="lieuNaiss"
                data-cy="lieuNaiss"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.genre')}
                id="client-genre"
                name="genre"
                data-cy="genre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.typeDemande')}
                id="client-typeDemande"
                name="typeDemande"
                data-cy="typeDemande"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.email')}
                id="client-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.destVoyageP')}
                id="client-destVoyageP"
                name="destVoyageP"
                data-cy="destVoyageP"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.motifDeplacement')}
                id="client-motifDeplacement"
                name="motifDeplacement"
                data-cy="motifDeplacement"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.paysNaissance')}
                id="client-paysNaissance"
                name="paysNaissance"
                data-cy="paysNaissance"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.regionNaiss')}
                id="client-regionNaiss"
                name="regionNaiss"
                data-cy="regionNaiss"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.departeNaiss')}
                id="client-departeNaiss"
                name="departeNaiss"
                data-cy="departeNaiss"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.telephone')}
                id="client-telephone"
                name="telephone"
                data-cy="telephone"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 20, message: translate('entity.validation.maxlength', { max: 20 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.pays')}
                id="client-pays"
                name="pays"
                data-cy="pays"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.region')}
                id="client-region"
                name="region"
                data-cy="region"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.departement')}
                id="client-departement"
                name="departement"
                data-cy="departement"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.lieu')}
                id="client-lieu"
                name="lieu"
                data-cy="lieu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.rue')}
                id="client-rue"
                name="rue"
                data-cy="rue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.profession')}
                id="client-profession"
                name="profession"
                data-cy="profession"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.prenomMere')}
                id="client-prenomMere"
                name="prenomMere"
                data-cy="prenomMere"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.nomMere')}
                id="client-nomMere"
                name="nomMere"
                data-cy="nomMere"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.prenomPere')}
                id="client-prenomPere"
                name="prenomPere"
                data-cy="prenomPere"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.nomPere')}
                id="client-nomPere"
                name="nomPere"
                data-cy="nomPere"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.formatCni')}
                id="client-formatCni"
                name="formatCni"
                data-cy="formatCni"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.numeroCni')}
                id="client-numeroCni"
                name="numeroCni"
                data-cy="numeroCni"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.dateDelivCni')}
                id="client-dateDelivCni"
                name="dateDelivCni"
                data-cy="dateDelivCni"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.dateExpCni')}
                id="client-dateExpCni"
                name="dateExpCni"
                data-cy="dateExpCni"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ClientUpdate;

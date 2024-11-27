import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnnee } from 'app/shared/model/annee.model';
import { getEntities as getAnnees } from 'app/entities/annee/annee.reducer';
import { IPassport } from 'app/shared/model/passport.model';
import { getEntities as getPassports } from 'app/entities/passport/passport.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IModePaiement } from 'app/shared/model/mode-paiement.model';
import { getEntity, updateEntity, createEntity, reset } from './mode-paiement.reducer';

export const ModePaiementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const annees = useAppSelector(state => state.annee.entities);
  const passports = useAppSelector(state => state.passport.entities);
  const clients = useAppSelector(state => state.client.entities);
  const modePaiementEntity = useAppSelector(state => state.modePaiement.entity);
  const loading = useAppSelector(state => state.modePaiement.loading);
  const updating = useAppSelector(state => state.modePaiement.updating);
  const updateSuccess = useAppSelector(state => state.modePaiement.updateSuccess);

  const handleClose = () => {
    navigate('/mode-paiement' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAnnees({}));
    dispatch(getPassports({}));
    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...modePaiementEntity,
      ...values,
      annee: annees.find(it => it.id.toString() === values.annee.toString()),
      passport: passports.find(it => it.id.toString() === values.passport.toString()),
      client: clients.find(it => it.id.toString() === values.client.toString()),
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
          ...modePaiementEntity,
          annee: modePaiementEntity?.annee?.id,
          passport: modePaiementEntity?.passport?.id,
          client: modePaiementEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pecapApp.modePaiement.home.createOrEditLabel" data-cy="ModePaiementCreateUpdateHeading">
            <Translate contentKey="pecapApp.modePaiement.home.createOrEditLabel">Create or edit a ModePaiement</Translate>
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
                  id="mode-paiement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pecapApp.modePaiement.nom')}
                id="mode-paiement-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                id="mode-paiement-annee"
                name="annee"
                data-cy="annee"
                label={translate('pecapApp.modePaiement.annee')}
                type="select"
                required
              >
                <option value="" key="0" />
                {annees
                  ? annees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="mode-paiement-passport"
                name="passport"
                data-cy="passport"
                label={translate('pecapApp.modePaiement.passport')}
                type="select"
                required
              >
                <option value="" key="0" />
                {passports
                  ? passports.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="mode-paiement-client"
                name="client"
                data-cy="client"
                label={translate('pecapApp.modePaiement.client')}
                type="select"
                required
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mode-paiement" replace color="info">
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

export default ModePaiementUpdate;

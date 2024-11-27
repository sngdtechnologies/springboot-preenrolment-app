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
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IEtatProcedure } from 'app/shared/model/etat-procedure.model';
import { getEntity, updateEntity, createEntity, reset } from './etat-procedure.reducer';

export const EtatProcedureUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const annees = useAppSelector(state => state.annee.entities);
  const clients = useAppSelector(state => state.client.entities);
  const etatProcedureEntity = useAppSelector(state => state.etatProcedure.entity);
  const loading = useAppSelector(state => state.etatProcedure.loading);
  const updating = useAppSelector(state => state.etatProcedure.updating);
  const updateSuccess = useAppSelector(state => state.etatProcedure.updateSuccess);

  const handleClose = () => {
    navigate('/etat-procedure' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAnnees({}));
    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...etatProcedureEntity,
      ...values,
      annee: annees.find(it => it.id.toString() === values.annee.toString()),
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
          ...etatProcedureEntity,
          annee: etatProcedureEntity?.annee?.id,
          client: etatProcedureEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pecapApp.etatProcedure.home.createOrEditLabel" data-cy="EtatProcedureCreateUpdateHeading">
            <Translate contentKey="pecapApp.etatProcedure.home.createOrEditLabel">Create or edit a EtatProcedure</Translate>
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
                  id="etat-procedure-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pecapApp.etatProcedure.etatPreEnrole')}
                id="etat-procedure-etatPreEnrole"
                name="etatPreEnrole"
                data-cy="etatPreEnrole"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.etatProcedure.etatEnrole')}
                id="etat-procedure-etatEnrole"
                name="etatEnrole"
                data-cy="etatEnrole"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.etatProcedure.etatRetrait')}
                id="etat-procedure-etatRetrait"
                name="etatRetrait"
                data-cy="etatRetrait"
                type="text"
                validate={{
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                id="etat-procedure-annee"
                name="annee"
                data-cy="annee"
                label={translate('pecapApp.etatProcedure.annee')}
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
                id="etat-procedure-client"
                name="client"
                data-cy="client"
                label={translate('pecapApp.etatProcedure.client')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/etat-procedure" replace color="info">
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

export default EtatProcedureUpdate;

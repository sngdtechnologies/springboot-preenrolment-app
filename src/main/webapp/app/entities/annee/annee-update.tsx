import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnnee } from 'app/shared/model/annee.model';
import { getEntity, updateEntity, createEntity, reset } from './annee.reducer';

export const AnneeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const anneeEntity = useAppSelector(state => state.annee.entity);
  const loading = useAppSelector(state => state.annee.loading);
  const updating = useAppSelector(state => state.annee.updating);
  const updateSuccess = useAppSelector(state => state.annee.updateSuccess);

  const handleClose = () => {
    navigate('/annee');
  };

  useEffect(() => {
    if (!isNew) {
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
      ...anneeEntity,
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
          ...anneeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pecapApp.annee.home.createOrEditLabel" data-cy="AnneeCreateUpdateHeading">
            <Translate contentKey="pecapApp.annee.home.createOrEditLabel">Create or edit a Annee</Translate>
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
                  id="annee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pecapApp.annee.nom')}
                id="annee-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.annee.dateDebut')}
                id="annee-dateDebut"
                name="dateDebut"
                data-cy="dateDebut"
                type="date"
              />
              <ValidatedField label={translate('pecapApp.annee.dateFin')} id="annee-dateFin" name="dateFin" data-cy="dateFin" type="date" />
              <ValidatedField
                label={translate('pecapApp.annee.isVerrouiller')}
                id="annee-isVerrouiller"
                name="isVerrouiller"
                data-cy="isVerrouiller"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('pecapApp.annee.isCloturer')}
                id="annee-isCloturer"
                name="isCloturer"
                data-cy="isCloturer"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/annee" replace color="info">
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

export default AnneeUpdate;

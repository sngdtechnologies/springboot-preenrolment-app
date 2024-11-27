import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAcces } from 'app/shared/model/acces.model';
import { getEntities as getAcces } from 'app/entities/acces/acces.reducer';
import { IAnnee } from 'app/shared/model/annee.model';
import { getEntities as getAnnees } from 'app/entities/annee/annee.reducer';
import { IProfil } from 'app/shared/model/profil.model';
import { getEntity, updateEntity, createEntity, reset } from './profil.reducer';

export const ProfilUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const acces = useAppSelector(state => state.acces.entities);
  const annees = useAppSelector(state => state.annee.entities);
  const profilEntity = useAppSelector(state => state.profil.entity);
  const loading = useAppSelector(state => state.profil.loading);
  const updating = useAppSelector(state => state.profil.updating);
  const updateSuccess = useAppSelector(state => state.profil.updateSuccess);

  const handleClose = () => {
    navigate('/profil');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAcces({}));
    dispatch(getAnnees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...profilEntity,
      ...values,
      acces: mapIdList(values.acces),
      annee: annees.find(it => it.id.toString() === values.annee.toString()),
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
          ...profilEntity,
          acces: profilEntity?.acces?.map(e => e.id.toString()),
          annee: profilEntity?.annee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pecapApp.profil.home.createOrEditLabel" data-cy="ProfilCreateUpdateHeading">
            <Translate contentKey="pecapApp.profil.home.createOrEditLabel">Create or edit a Profil</Translate>
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
                  id="profil-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pecapApp.profil.nom')}
                id="profil-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('pecapApp.profil.acces')}
                id="profil-acces"
                data-cy="acces"
                type="select"
                multiple
                name="acces"
              >
                <option value="" key="0" />
                {acces
                  ? acces.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nom}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="profil-annee"
                name="annee"
                data-cy="annee"
                label={translate('pecapApp.profil.annee')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/profil" replace color="info">
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

export default ProfilUpdate;

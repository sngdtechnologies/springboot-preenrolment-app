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
import { ITypePassport } from 'app/shared/model/type-passport.model';
import { getEntity, updateEntity, createEntity, reset } from './type-passport.reducer';

export const TypePassportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const annees = useAppSelector(state => state.annee.entities);
  const passports = useAppSelector(state => state.passport.entities);
  const typePassportEntity = useAppSelector(state => state.typePassport.entity);
  const loading = useAppSelector(state => state.typePassport.loading);
  const updating = useAppSelector(state => state.typePassport.updating);
  const updateSuccess = useAppSelector(state => state.typePassport.updateSuccess);

  const handleClose = () => {
    navigate('/type-passport');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAnnees({}));
    dispatch(getPassports({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...typePassportEntity,
      ...values,
      annee: annees.find(it => it.id.toString() === values.annee.toString()),
      passport: passports.find(it => it.id.toString() === values.passport.toString()),
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
          ...typePassportEntity,
          annee: typePassportEntity?.annee?.id,
          passport: typePassportEntity?.passport?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pecapApp.typePassport.home.createOrEditLabel" data-cy="TypePassportCreateUpdateHeading">
            <Translate contentKey="pecapApp.typePassport.home.createOrEditLabel">Create or edit a TypePassport</Translate>
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
                  id="type-passport-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('pecapApp.typePassport.nom')}
                id="type-passport-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                id="type-passport-annee"
                name="annee"
                data-cy="annee"
                label={translate('pecapApp.typePassport.annee')}
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
                id="type-passport-passport"
                name="passport"
                data-cy="passport"
                label={translate('pecapApp.typePassport.passport')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/type-passport" replace color="info">
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

export default TypePassportUpdate;

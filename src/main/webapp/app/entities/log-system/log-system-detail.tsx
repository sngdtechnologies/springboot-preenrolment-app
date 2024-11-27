import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './log-system.reducer';

export const LogSystemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const logSystemEntity = useAppSelector(state => state.logSystem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="logSystemDetailsHeading">
          <Translate contentKey="pecapApp.logSystem.detail.title">LogSystem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{logSystemEntity.id}</dd>
          <dt>
            <span id="eventDate">
              <Translate contentKey="pecapApp.logSystem.eventDate">Event Date</Translate>
            </span>
          </dt>
          <dd>
            {logSystemEntity.eventDate ? <TextFormat value={logSystemEntity.eventDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="login">
              <Translate contentKey="pecapApp.logSystem.login">Login</Translate>
            </span>
          </dt>
          <dd>{logSystemEntity.login}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="pecapApp.logSystem.message">Message</Translate>
            </span>
          </dt>
          <dd>{logSystemEntity.message}</dd>
        </dl>
        <Button tag={Link} to="/log-system" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/log-system/${logSystemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LogSystemDetail;

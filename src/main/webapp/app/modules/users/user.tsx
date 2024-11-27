import 'app/modules/home/home.scss';

import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Row, Col } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { START_PREENROLE } from 'app/shared/const/route.const';
import ErrorBoundary from 'app/shared/error/error-boundary';
import UserRoutes from 'app/modules/users/routes';

export const User = () => {
  const navigate = useNavigate();
  const account = useAppSelector(state => state.authentication.account);

  const handleClickBegin = () => {
    navigate(START_PREENROLE);
  };

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9" className="py-4">
        <ErrorBoundary>
          <UserRoutes />
        </ErrorBoundary>
      </Col>
    </Row>
  );
};

export default User;

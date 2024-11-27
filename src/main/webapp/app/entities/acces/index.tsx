import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Acces from './acces';
import AccesDetail from './acces-detail';

const AccesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Acces />} />
    <Route path=":id">
      <Route index element={<AccesDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AccesRoutes;

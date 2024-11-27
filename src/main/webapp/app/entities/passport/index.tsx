import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Passport from './passport';
import PassportDetail from './passport-detail';
import PassportUpdate from './passport-update';
import PassportDeleteDialog from './passport-delete-dialog';

const PassportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Passport />} />
    <Route path="new" element={<PassportUpdate />} />
    <Route path=":id">
      <Route index element={<PassportDetail />} />
      <Route path="edit" element={<PassportUpdate />} />
      <Route path="delete" element={<PassportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PassportRoutes;

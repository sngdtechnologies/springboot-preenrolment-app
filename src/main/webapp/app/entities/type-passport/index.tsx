import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TypePassport from './type-passport';
import TypePassportDetail from './type-passport-detail';
import TypePassportUpdate from './type-passport-update';
import TypePassportDeleteDialog from './type-passport-delete-dialog';

const TypePassportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TypePassport />} />
    <Route path="new" element={<TypePassportUpdate />} />
    <Route path=":id">
      <Route index element={<TypePassportDetail />} />
      <Route path="edit" element={<TypePassportUpdate />} />
      <Route path="delete" element={<TypePassportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TypePassportRoutes;

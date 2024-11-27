import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Annee from './annee';
import AnneeDetail from './annee-detail';
import AnneeUpdate from './annee-update';
import AnneeDeleteDialog from './annee-delete-dialog';

const AnneeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Annee />} />
    <Route path="new" element={<AnneeUpdate />} />
    <Route path=":id">
      <Route index element={<AnneeDetail />} />
      <Route path="edit" element={<AnneeUpdate />} />
      <Route path="delete" element={<AnneeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AnneeRoutes;

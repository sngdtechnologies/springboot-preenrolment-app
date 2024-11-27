import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ModePaiement from './mode-paiement';
import ModePaiementDetail from './mode-paiement-detail';
import ModePaiementUpdate from './mode-paiement-update';
import ModePaiementDeleteDialog from './mode-paiement-delete-dialog';

const ModePaiementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ModePaiement />} />
    <Route path="new" element={<ModePaiementUpdate />} />
    <Route path=":id">
      <Route index element={<ModePaiementDetail />} />
      <Route path="edit" element={<ModePaiementUpdate />} />
      <Route path="delete" element={<ModePaiementDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ModePaiementRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EtatProcedure from './etat-procedure';
import EtatProcedureDetail from './etat-procedure-detail';
import EtatProcedureUpdate from './etat-procedure-update';
import EtatProcedureDeleteDialog from './etat-procedure-delete-dialog';

const EtatProcedureRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EtatProcedure />} />
    <Route path="new" element={<EtatProcedureUpdate />} />
    <Route path=":id">
      <Route index element={<EtatProcedureDetail />} />
      <Route path="edit" element={<EtatProcedureUpdate />} />
      <Route path="delete" element={<EtatProcedureDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EtatProcedureRoutes;

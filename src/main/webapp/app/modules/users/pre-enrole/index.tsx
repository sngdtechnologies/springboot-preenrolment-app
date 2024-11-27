import React from 'react';

import { Route } from 'react-router-dom';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import InfoGenerale from './info-generale';
import InfoPersonelle from './info-personnelle';
import InfoParentale from './info-parentale';
import InfoDocument from './info-sur-les-documents';
import Connexion from './connexion';

const PreEnroleRoutes = () => (
  <div className="card">
    <ErrorBoundaryRoutes>
      <Route path="connexion" element={<Connexion />} />
      <Route path="info-generale" element={<InfoGenerale />} />
      <Route path="info-personnelle" element={<InfoPersonelle />} />
      <Route path="info-parentale" element={<InfoParentale />} />
      <Route path="info-sur-les-documents" element={<InfoDocument />} />
    </ErrorBoundaryRoutes>
  </div>
);

export default PreEnroleRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LogSystem from './log-system';
import LogSystemDetail from './log-system-detail';
import LogSystemUpdate from './log-system-update';
import LogSystemDeleteDialog from './log-system-delete-dialog';

const LogSystemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LogSystem />} />
    <Route path="new" element={<LogSystemUpdate />} />
    <Route path=":id">
      <Route index element={<LogSystemDetail />} />
      <Route path="edit" element={<LogSystemUpdate />} />
      <Route path="delete" element={<LogSystemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LogSystemRoutes;

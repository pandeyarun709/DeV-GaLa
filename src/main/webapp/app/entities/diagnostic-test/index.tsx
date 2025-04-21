import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DiagnosticTest from './diagnostic-test';
import DiagnosticTestDetail from './diagnostic-test-detail';
import DiagnosticTestUpdate from './diagnostic-test-update';
import DiagnosticTestDeleteDialog from './diagnostic-test-delete-dialog';

const DiagnosticTestRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DiagnosticTest />} />
    <Route path="new" element={<DiagnosticTestUpdate />} />
    <Route path=":id">
      <Route index element={<DiagnosticTestDetail />} />
      <Route path="edit" element={<DiagnosticTestUpdate />} />
      <Route path="delete" element={<DiagnosticTestDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DiagnosticTestRoutes;

import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DiagnosticTestReport from './diagnostic-test-report';
import DiagnosticTestReportDetail from './diagnostic-test-report-detail';
import DiagnosticTestReportUpdate from './diagnostic-test-report-update';
import DiagnosticTestReportDeleteDialog from './diagnostic-test-report-delete-dialog';

const DiagnosticTestReportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DiagnosticTestReport />} />
    <Route path="new" element={<DiagnosticTestReportUpdate />} />
    <Route path=":id">
      <Route index element={<DiagnosticTestReportDetail />} />
      <Route path="edit" element={<DiagnosticTestReportUpdate />} />
      <Route path="delete" element={<DiagnosticTestReportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DiagnosticTestReportRoutes;

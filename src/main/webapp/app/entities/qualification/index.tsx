import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Qualification from './qualification';
import QualificationDetail from './qualification-detail';
import QualificationUpdate from './qualification-update';
import QualificationDeleteDialog from './qualification-delete-dialog';

const QualificationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Qualification />} />
    <Route path="new" element={<QualificationUpdate />} />
    <Route path=":id">
      <Route index element={<QualificationDetail />} />
      <Route path="edit" element={<QualificationUpdate />} />
      <Route path="delete" element={<QualificationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QualificationRoutes;

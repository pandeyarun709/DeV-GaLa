import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MedicineBatch from './medicine-batch';
import MedicineBatchDetail from './medicine-batch-detail';
import MedicineBatchUpdate from './medicine-batch-update';
import MedicineBatchDeleteDialog from './medicine-batch-delete-dialog';

const MedicineBatchRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MedicineBatch />} />
    <Route path="new" element={<MedicineBatchUpdate />} />
    <Route path=":id">
      <Route index element={<MedicineBatchDetail />} />
      <Route path="edit" element={<MedicineBatchUpdate />} />
      <Route path="delete" element={<MedicineBatchDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MedicineBatchRoutes;

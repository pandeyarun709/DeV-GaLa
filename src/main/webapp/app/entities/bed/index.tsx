import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bed from './bed';
import BedDetail from './bed-detail';
import BedUpdate from './bed-update';
import BedDeleteDialog from './bed-delete-dialog';

const BedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bed />} />
    <Route path="new" element={<BedUpdate />} />
    <Route path=":id">
      <Route index element={<BedDetail />} />
      <Route path="edit" element={<BedUpdate />} />
      <Route path="delete" element={<BedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BedRoutes;

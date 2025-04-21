import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MedicineDose from './medicine-dose';
import MedicineDoseDetail from './medicine-dose-detail';
import MedicineDoseUpdate from './medicine-dose-update';
import MedicineDoseDeleteDialog from './medicine-dose-delete-dialog';

const MedicineDoseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MedicineDose />} />
    <Route path="new" element={<MedicineDoseUpdate />} />
    <Route path=":id">
      <Route index element={<MedicineDoseDetail />} />
      <Route path="edit" element={<MedicineDoseUpdate />} />
      <Route path="delete" element={<MedicineDoseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MedicineDoseRoutes;

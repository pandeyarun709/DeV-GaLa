import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DoctorVisit from './doctor-visit';
import DoctorVisitDetail from './doctor-visit-detail';
import DoctorVisitUpdate from './doctor-visit-update';
import DoctorVisitDeleteDialog from './doctor-visit-delete-dialog';

const DoctorVisitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DoctorVisit />} />
    <Route path="new" element={<DoctorVisitUpdate />} />
    <Route path=":id">
      <Route index element={<DoctorVisitDetail />} />
      <Route path="edit" element={<DoctorVisitUpdate />} />
      <Route path="delete" element={<DoctorVisitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoctorVisitRoutes;

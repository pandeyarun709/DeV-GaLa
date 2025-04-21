import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DoctorVisitType from './doctor-visit-type';
import DoctorVisitTypeDetail from './doctor-visit-type-detail';
import DoctorVisitTypeUpdate from './doctor-visit-type-update';
import DoctorVisitTypeDeleteDialog from './doctor-visit-type-delete-dialog';

const DoctorVisitTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DoctorVisitType />} />
    <Route path="new" element={<DoctorVisitTypeUpdate />} />
    <Route path=":id">
      <Route index element={<DoctorVisitTypeDetail />} />
      <Route path="edit" element={<DoctorVisitTypeUpdate />} />
      <Route path="delete" element={<DoctorVisitTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoctorVisitTypeRoutes;

import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Admission from './admission';
import AdmissionDetail from './admission-detail';
import AdmissionUpdate from './admission-update';
import AdmissionDeleteDialog from './admission-delete-dialog';

const AdmissionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Admission />} />
    <Route path="new" element={<AdmissionUpdate />} />
    <Route path=":id">
      <Route index element={<AdmissionDetail />} />
      <Route path="edit" element={<AdmissionUpdate />} />
      <Route path="delete" element={<AdmissionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdmissionRoutes;

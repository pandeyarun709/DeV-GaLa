import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PatientRegistrationDetails from './patient-registration-details';
import PatientRegistrationDetailsDetail from './patient-registration-details-detail';
import PatientRegistrationDetailsUpdate from './patient-registration-details-update';
import PatientRegistrationDetailsDeleteDialog from './patient-registration-details-delete-dialog';

const PatientRegistrationDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PatientRegistrationDetails />} />
    <Route path="new" element={<PatientRegistrationDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<PatientRegistrationDetailsDetail />} />
      <Route path="edit" element={<PatientRegistrationDetailsUpdate />} />
      <Route path="delete" element={<PatientRegistrationDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PatientRegistrationDetailsRoutes;

import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ReferralDoctor from './referral-doctor';
import ReferralDoctorDetail from './referral-doctor-detail';
import ReferralDoctorUpdate from './referral-doctor-update';
import ReferralDoctorDeleteDialog from './referral-doctor-delete-dialog';

const ReferralDoctorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ReferralDoctor />} />
    <Route path="new" element={<ReferralDoctorUpdate />} />
    <Route path=":id">
      <Route index element={<ReferralDoctorDetail />} />
      <Route path="edit" element={<ReferralDoctorUpdate />} />
      <Route path="delete" element={<ReferralDoctorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReferralDoctorRoutes;

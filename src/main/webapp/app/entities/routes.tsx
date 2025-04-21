import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Client from './client';
import Hospital from './hospital';
import Address from './address';
import Patient from './patient';
import PatientRegistrationDetails from './patient-registration-details';
import ReferralDoctor from './referral-doctor';
import Department from './department';
import Employee from './employee';
import Qualification from './qualification';
import DiagnosticTest from './diagnostic-test';
import DiagnosticTestReport from './diagnostic-test-report';
import DoctorVisitType from './doctor-visit-type';
import Slot from './slot';
import DoctorVisit from './doctor-visit';
import Prescription from './prescription';
import MedicineDose from './medicine-dose';
import Bed from './bed';
import Medicine from './medicine';
import MedicineBatch from './medicine-batch';
import Admission from './admission';
import LedgerItem from './ledger-item';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="client/*" element={<Client />} />
        <Route path="hospital/*" element={<Hospital />} />
        <Route path="address/*" element={<Address />} />
        <Route path="patient/*" element={<Patient />} />
        <Route path="patient-registration-details/*" element={<PatientRegistrationDetails />} />
        <Route path="referral-doctor/*" element={<ReferralDoctor />} />
        <Route path="department/*" element={<Department />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="qualification/*" element={<Qualification />} />
        <Route path="diagnostic-test/*" element={<DiagnosticTest />} />
        <Route path="diagnostic-test-report/*" element={<DiagnosticTestReport />} />
        <Route path="doctor-visit-type/*" element={<DoctorVisitType />} />
        <Route path="slot/*" element={<Slot />} />
        <Route path="doctor-visit/*" element={<DoctorVisit />} />
        <Route path="prescription/*" element={<Prescription />} />
        <Route path="medicine-dose/*" element={<MedicineDose />} />
        <Route path="bed/*" element={<Bed />} />
        <Route path="medicine/*" element={<Medicine />} />
        <Route path="medicine-batch/*" element={<MedicineBatch />} />
        <Route path="admission/*" element={<Admission />} />
        <Route path="ledger-item/*" element={<LedgerItem />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

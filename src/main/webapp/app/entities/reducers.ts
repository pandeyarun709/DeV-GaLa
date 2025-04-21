import client from 'app/entities/client/client.reducer';
import hospital from 'app/entities/hospital/hospital.reducer';
import address from 'app/entities/address/address.reducer';
import patient from 'app/entities/patient/patient.reducer';
import patientRegistrationDetails from 'app/entities/patient-registration-details/patient-registration-details.reducer';
import referralDoctor from 'app/entities/referral-doctor/referral-doctor.reducer';
import department from 'app/entities/department/department.reducer';
import employee from 'app/entities/employee/employee.reducer';
import qualification from 'app/entities/qualification/qualification.reducer';
import diagnosticTest from 'app/entities/diagnostic-test/diagnostic-test.reducer';
import diagnosticTestReport from 'app/entities/diagnostic-test-report/diagnostic-test-report.reducer';
import doctorVisitType from 'app/entities/doctor-visit-type/doctor-visit-type.reducer';
import slot from 'app/entities/slot/slot.reducer';
import doctorVisit from 'app/entities/doctor-visit/doctor-visit.reducer';
import prescription from 'app/entities/prescription/prescription.reducer';
import medicineDose from 'app/entities/medicine-dose/medicine-dose.reducer';
import bed from 'app/entities/bed/bed.reducer';
import medicine from 'app/entities/medicine/medicine.reducer';
import medicineBatch from 'app/entities/medicine-batch/medicine-batch.reducer';
import admission from 'app/entities/admission/admission.reducer';
import ledgerItem from 'app/entities/ledger-item/ledger-item.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  client,
  hospital,
  address,
  patient,
  patientRegistrationDetails,
  referralDoctor,
  department,
  employee,
  qualification,
  diagnosticTest,
  diagnosticTestReport,
  doctorVisitType,
  slot,
  doctorVisit,
  prescription,
  medicineDose,
  bed,
  medicine,
  medicineBatch,
  admission,
  ledgerItem,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

import dayjs from 'dayjs';
import { IBed } from 'app/shared/model/bed.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IHospital } from 'app/shared/model/hospital.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { AdmissionStatus } from 'app/shared/model/enumerations/admission-status.model';
import { DischargeStatus } from 'app/shared/model/enumerations/discharge-status.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IAdmission {
  id?: number;
  details?: string | null;
  admissionStatus?: keyof typeof AdmissionStatus | null;
  dischargeStatus?: keyof typeof DischargeStatus | null;
  admissionTime?: dayjs.Dayjs | null;
  dischargeTime?: dayjs.Dayjs | null;
  paymentStatus?: keyof typeof PaymentStatus | null;
  totalBillAmount?: number | null;
  insuranceCoveredAmount?: number | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  beds?: IBed[] | null;
  patient?: IPatient | null;
  hospital?: IHospital | null;
  admittedUnder?: IEmployee | null;
}

export const defaultValue: Readonly<IAdmission> = {
  isActive: false,
};

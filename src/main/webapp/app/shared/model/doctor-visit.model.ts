import dayjs from 'dayjs';
import { IPrescription } from 'app/shared/model/prescription.model';
import { IDoctorVisitType } from 'app/shared/model/doctor-visit-type.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IAdmission } from 'app/shared/model/admission.model';
import { VisitStatus } from 'app/shared/model/enumerations/visit-status.model';

export interface IDoctorVisit {
  id?: number;
  description?: string | null;
  status?: keyof typeof VisitStatus | null;
  visitDate?: dayjs.Dayjs | null;
  visitTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  prescription?: IPrescription | null;
  doctorVisitType?: IDoctorVisitType | null;
  patient?: IPatient | null;
  admissions?: IAdmission | null;
}

export const defaultValue: Readonly<IDoctorVisit> = {
  isActive: false,
};

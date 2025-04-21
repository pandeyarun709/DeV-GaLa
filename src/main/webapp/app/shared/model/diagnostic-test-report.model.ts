import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';
import { IDiagnosticTest } from 'app/shared/model/diagnostic-test.model';
import { IAdmission } from 'app/shared/model/admission.model';

export interface IDiagnosticTestReport {
  id?: number;
  description?: string | null;
  signedBy?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  patient?: IPatient | null;
  test?: IDiagnosticTest | null;
  admissions?: IAdmission | null;
}

export const defaultValue: Readonly<IDiagnosticTestReport> = {
  isActive: false,
};

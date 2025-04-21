import dayjs from 'dayjs';
import { IDepartment } from 'app/shared/model/department.model';
import { IPrescription } from 'app/shared/model/prescription.model';

export interface IDiagnosticTest {
  id?: number;
  name?: string | null;
  phone?: number | null;
  email?: string | null;
  fee?: number | null;
  isInsuranceCovered?: boolean | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  department?: IDepartment | null;
  prescription?: IPrescription | null;
}

export const defaultValue: Readonly<IDiagnosticTest> = {
  isInsuranceCovered: false,
  isActive: false,
};

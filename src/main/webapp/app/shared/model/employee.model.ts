import dayjs from 'dayjs';
import { IQualification } from 'app/shared/model/qualification.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IDiagnosticTest } from 'app/shared/model/diagnostic-test.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';

export interface IEmployee {
  id?: number;
  name?: string | null;
  phone?: number | null;
  email?: string | null;
  registrationNo?: string | null;
  rmoHourlyRate?: number | null;
  type?: keyof typeof EmployeeType | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  qualifications?: IQualification[] | null;
  department?: IDepartment | null;
  test?: IDiagnosticTest | null;
}

export const defaultValue: Readonly<IEmployee> = {
  isActive: false,
};

import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IQualification {
  id?: number;
  name?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  employees?: IEmployee[] | null;
}

export const defaultValue: Readonly<IQualification> = {
  isActive: false,
};

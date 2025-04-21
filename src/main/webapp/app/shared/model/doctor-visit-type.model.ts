import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';
import { VisitType } from 'app/shared/model/enumerations/visit-type.model';

export interface IDoctorVisitType {
  id?: number;
  type?: keyof typeof VisitType | null;
  fee?: number | null;
  isInsuranceCovered?: boolean | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  doctor?: IEmployee | null;
}

export const defaultValue: Readonly<IDoctorVisitType> = {
  isInsuranceCovered: false,
  isActive: false,
};

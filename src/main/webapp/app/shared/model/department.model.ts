import dayjs from 'dayjs';
import { IHospital } from 'app/shared/model/hospital.model';

export interface IDepartment {
  id?: number;
  name?: string | null;
  phone?: number | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  hospital?: IHospital | null;
}

export const defaultValue: Readonly<IDepartment> = {
  isActive: false,
};

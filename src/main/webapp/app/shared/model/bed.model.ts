import dayjs from 'dayjs';
import { IHospital } from 'app/shared/model/hospital.model';
import { IAdmission } from 'app/shared/model/admission.model';
import { BedType } from 'app/shared/model/enumerations/bed-type.model';

export interface IBed {
  id?: number;
  type?: keyof typeof BedType | null;
  floor?: number | null;
  roomNo?: number | null;
  dailyRate?: number | null;
  isInsuranceCovered?: boolean | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  hospital?: IHospital | null;
  admissions?: IAdmission[] | null;
}

export const defaultValue: Readonly<IBed> = {
  isInsuranceCovered: false,
  isActive: false,
};

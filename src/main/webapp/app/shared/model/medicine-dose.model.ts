import dayjs from 'dayjs';
import { IPrescription } from 'app/shared/model/prescription.model';
import { IMedicine } from 'app/shared/model/medicine.model';

export interface IMedicineDose {
  id?: number;
  frequency?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  prescription?: IPrescription | null;
  medicine?: IMedicine | null;
}

export const defaultValue: Readonly<IMedicineDose> = {
  isActive: false,
};

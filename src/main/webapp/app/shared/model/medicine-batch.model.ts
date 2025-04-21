import dayjs from 'dayjs';
import { IMedicine } from 'app/shared/model/medicine.model';
import { IHospital } from 'app/shared/model/hospital.model';
import { IAdmission } from 'app/shared/model/admission.model';

export interface IMedicineBatch {
  id?: number;
  batchNo?: string | null;
  expiryDate?: dayjs.Dayjs | null;
  quantity?: number | null;
  sellingPrice?: number | null;
  storageLocation?: string | null;
  rackNo?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  med?: IMedicine | null;
  hospital?: IHospital | null;
  admissions?: IAdmission | null;
}

export const defaultValue: Readonly<IMedicineBatch> = {
  isActive: false,
};

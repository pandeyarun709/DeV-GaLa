import dayjs from 'dayjs';
import { IAdmission } from 'app/shared/model/admission.model';

export interface ILedgerItem {
  id?: number;
  time?: dayjs.Dayjs | null;
  description?: string | null;
  fee?: number | null;
  isCoveredByInsurance?: boolean | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  admission?: IAdmission | null;
}

export const defaultValue: Readonly<ILedgerItem> = {
  isCoveredByInsurance: false,
  isActive: false,
};

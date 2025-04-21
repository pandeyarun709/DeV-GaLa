import dayjs from 'dayjs';
import { MedicineType } from 'app/shared/model/enumerations/medicine-type.model';

export interface IMedicine {
  id?: number;
  name?: string | null;
  genericMolecule?: string | null;
  isPrescriptionNeeded?: boolean | null;
  description?: string | null;
  mrp?: number | null;
  isInsuranceCovered?: boolean | null;
  type?: keyof typeof MedicineType | null;
  isConsumable?: boolean | null;
  isReturnable?: boolean | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IMedicine> = {
  isPrescriptionNeeded: false,
  isInsuranceCovered: false,
  isConsumable: false,
  isReturnable: false,
  isActive: false,
};

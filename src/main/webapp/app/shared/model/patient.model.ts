import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';
import { BloodGroup } from 'app/shared/model/enumerations/blood-group.model';
import { Relationship } from 'app/shared/model/enumerations/relationship.model';

export interface IPatient {
  id?: number;
  registrationId?: string | null;
  name?: string | null;
  phone?: number | null;
  email?: string | null;
  sex?: keyof typeof Sex | null;
  dateOfBirth?: dayjs.Dayjs | null;
  bloodGroup?: keyof typeof BloodGroup | null;
  emergencyContactName?: string | null;
  emergencyContactPhone?: number | null;
  emergencyContactEmail?: string | null;
  emergencyContactRelationShip?: keyof typeof Relationship | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  address?: IAddress | null;
}

export const defaultValue: Readonly<IPatient> = {
  isActive: false,
};

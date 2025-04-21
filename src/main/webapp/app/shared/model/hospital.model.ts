import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { IClient } from 'app/shared/model/client.model';

export interface IHospital {
  id?: number;
  name?: string | null;
  logoPath?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  address?: IAddress | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IHospital> = {
  isActive: false,
};

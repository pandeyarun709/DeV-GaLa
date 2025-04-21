import dayjs from 'dayjs';

export interface IAddress {
  id?: number;
  line1?: string | null;
  line2?: string | null;
  city?: string | null;
  state?: string | null;
  pincode?: number | null;
  lat?: number | null;
  lon?: number | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IAddress> = {
  isActive: false,
};

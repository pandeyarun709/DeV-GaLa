import dayjs from 'dayjs';

export interface IReferralDoctor {
  id?: number;
  name?: string | null;
  phone?: number | null;
  email?: string | null;
  registrationNo?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IReferralDoctor> = {
  isActive: false,
};

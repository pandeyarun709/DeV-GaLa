import dayjs from 'dayjs';

export interface IPrescription {
  id?: number;
  history?: string | null;
  compliant?: string | null;
  height?: number | null;
  weight?: number | null;
  bpHigh?: number | null;
  bpLow?: number | null;
  temperature?: number | null;
  otherVital?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IPrescription> = {
  isActive: false,
};

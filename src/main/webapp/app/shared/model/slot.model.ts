import dayjs from 'dayjs';
import { IDoctorVisitType } from 'app/shared/model/doctor-visit-type.model';
import { IDiagnosticTest } from 'app/shared/model/diagnostic-test.model';
import { Day } from 'app/shared/model/enumerations/day.model';

export interface ISlot {
  id?: number;
  day?: keyof typeof Day | null;
  startTimeHour?: number | null;
  startTimeMin?: number | null;
  endTimeHour?: number | null;
  endTimeMin?: number | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  doctorVisitType?: IDoctorVisitType | null;
  test?: IDiagnosticTest | null;
}

export const defaultValue: Readonly<ISlot> = {
  isActive: false,
};

import { IClient } from 'app/shared/model/client.model';
import { IHospital } from 'app/shared/model/hospital.model';
import { IReferralDoctor } from 'app/shared/model/referral-doctor.model';

export interface IPatientRegistrationDetails {
  id?: number;
  registrationId?: string | null;
  client?: IClient | null;
  hospital?: IHospital | null;
  referredBy?: IReferralDoctor | null;
}

export const defaultValue: Readonly<IPatientRegistrationDetails> = {};

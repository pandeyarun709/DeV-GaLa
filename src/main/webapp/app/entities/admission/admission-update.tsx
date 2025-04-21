import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBeds } from 'app/entities/bed/bed.reducer';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { getEntities as getHospitals } from 'app/entities/hospital/hospital.reducer';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { AdmissionStatus } from 'app/shared/model/enumerations/admission-status.model';
import { DischargeStatus } from 'app/shared/model/enumerations/discharge-status.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { createEntity, getEntity, updateEntity } from './admission.reducer';

export const AdmissionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beds = useAppSelector(state => state.bed.entities);
  const patients = useAppSelector(state => state.patient.entities);
  const hospitals = useAppSelector(state => state.hospital.entities);
  const employees = useAppSelector(state => state.employee.entities);
  const admissionEntity = useAppSelector(state => state.admission.entity);
  const loading = useAppSelector(state => state.admission.loading);
  const updating = useAppSelector(state => state.admission.updating);
  const updateSuccess = useAppSelector(state => state.admission.updateSuccess);
  const admissionStatusValues = Object.keys(AdmissionStatus);
  const dischargeStatusValues = Object.keys(DischargeStatus);
  const paymentStatusValues = Object.keys(PaymentStatus);

  const handleClose = () => {
    navigate('/admission');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getBeds({}));
    dispatch(getPatients({}));
    dispatch(getHospitals({}));
    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.admissionTime = convertDateTimeToServer(values.admissionTime);
    values.dischargeTime = convertDateTimeToServer(values.dischargeTime);
    if (values.totalBillAmount !== undefined && typeof values.totalBillAmount !== 'number') {
      values.totalBillAmount = Number(values.totalBillAmount);
    }
    if (values.insuranceCoveredAmount !== undefined && typeof values.insuranceCoveredAmount !== 'number') {
      values.insuranceCoveredAmount = Number(values.insuranceCoveredAmount);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...admissionEntity,
      ...values,
      beds: mapIdList(values.beds),
      patient: patients.find(it => it.id.toString() === values.patient?.toString()),
      hospital: hospitals.find(it => it.id.toString() === values.hospital?.toString()),
      admittedUnder: employees.find(it => it.id.toString() === values.admittedUnder?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          admissionTime: displayDefaultDateTime(),
          dischargeTime: displayDefaultDateTime(),
          createdOn: displayDefaultDateTime(),
          updatedOn: displayDefaultDateTime(),
        }
      : {
          admissionStatus: 'Accidental',
          dischargeStatus: 'NotDischarged',
          paymentStatus: 'Paid',
          ...admissionEntity,
          admissionTime: convertDateTimeFromServer(admissionEntity.admissionTime),
          dischargeTime: convertDateTimeFromServer(admissionEntity.dischargeTime),
          createdOn: convertDateTimeFromServer(admissionEntity.createdOn),
          updatedOn: convertDateTimeFromServer(admissionEntity.updatedOn),
          beds: admissionEntity?.beds?.map(e => e.id.toString()),
          patient: admissionEntity?.patient?.id,
          hospital: admissionEntity?.hospital?.id,
          admittedUnder: admissionEntity?.admittedUnder?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.admission.home.createOrEditLabel" data-cy="AdmissionCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.admission.home.createOrEditLabel">Create or edit a Admission</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="admission-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.admission.details')}
                id="admission-details"
                name="details"
                data-cy="details"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.admissionStatus')}
                id="admission-admissionStatus"
                name="admissionStatus"
                data-cy="admissionStatus"
                type="select"
              >
                {admissionStatusValues.map(admissionStatus => (
                  <option value={admissionStatus} key={admissionStatus}>
                    {translate(`hospitalBeApp.AdmissionStatus.${admissionStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.admission.dischargeStatus')}
                id="admission-dischargeStatus"
                name="dischargeStatus"
                data-cy="dischargeStatus"
                type="select"
              >
                {dischargeStatusValues.map(dischargeStatus => (
                  <option value={dischargeStatus} key={dischargeStatus}>
                    {translate(`hospitalBeApp.DischargeStatus.${dischargeStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.admission.admissionTime')}
                id="admission-admissionTime"
                name="admissionTime"
                data-cy="admissionTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.dischargeTime')}
                id="admission-dischargeTime"
                name="dischargeTime"
                data-cy="dischargeTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.paymentStatus')}
                id="admission-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate(`hospitalBeApp.PaymentStatus.${paymentStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.admission.totalBillAmount')}
                id="admission-totalBillAmount"
                name="totalBillAmount"
                data-cy="totalBillAmount"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.insuranceCoveredAmount')}
                id="admission-insuranceCoveredAmount"
                name="insuranceCoveredAmount"
                data-cy="insuranceCoveredAmount"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.isActive')}
                id="admission-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.createdBy')}
                id="admission-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.createdOn')}
                id="admission-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.updatedBy')}
                id="admission-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.updatedOn')}
                id="admission-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.admission.beds')}
                id="admission-beds"
                data-cy="beds"
                type="select"
                multiple
                name="beds"
              >
                <option value="" key="0" />
                {beds
                  ? beds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="admission-patient"
                name="patient"
                data-cy="patient"
                label={translate('hospitalBeApp.admission.patient')}
                type="select"
              >
                <option value="" key="0" />
                {patients
                  ? patients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="admission-hospital"
                name="hospital"
                data-cy="hospital"
                label={translate('hospitalBeApp.admission.hospital')}
                type="select"
              >
                <option value="" key="0" />
                {hospitals
                  ? hospitals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="admission-admittedUnder"
                name="admittedUnder"
                data-cy="admittedUnder"
                label={translate('hospitalBeApp.admission.admittedUnder')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/admission" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AdmissionUpdate;

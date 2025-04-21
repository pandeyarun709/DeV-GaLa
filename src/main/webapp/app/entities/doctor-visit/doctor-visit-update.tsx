import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPrescriptions } from 'app/entities/prescription/prescription.reducer';
import { getEntities as getDoctorVisitTypes } from 'app/entities/doctor-visit-type/doctor-visit-type.reducer';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { getEntities as getAdmissions } from 'app/entities/admission/admission.reducer';
import { VisitStatus } from 'app/shared/model/enumerations/visit-status.model';
import { createEntity, getEntity, updateEntity } from './doctor-visit.reducer';

export const DoctorVisitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const prescriptions = useAppSelector(state => state.prescription.entities);
  const doctorVisitTypes = useAppSelector(state => state.doctorVisitType.entities);
  const patients = useAppSelector(state => state.patient.entities);
  const admissions = useAppSelector(state => state.admission.entities);
  const doctorVisitEntity = useAppSelector(state => state.doctorVisit.entity);
  const loading = useAppSelector(state => state.doctorVisit.loading);
  const updating = useAppSelector(state => state.doctorVisit.updating);
  const updateSuccess = useAppSelector(state => state.doctorVisit.updateSuccess);
  const visitStatusValues = Object.keys(VisitStatus);

  const handleClose = () => {
    navigate('/doctor-visit');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPrescriptions({}));
    dispatch(getDoctorVisitTypes({}));
    dispatch(getPatients({}));
    dispatch(getAdmissions({}));
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
    values.visitTime = convertDateTimeToServer(values.visitTime);
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...doctorVisitEntity,
      ...values,
      prescription: prescriptions.find(it => it.id.toString() === values.prescription?.toString()),
      doctorVisitType: doctorVisitTypes.find(it => it.id.toString() === values.doctorVisitType?.toString()),
      patient: patients.find(it => it.id.toString() === values.patient?.toString()),
      admissions: admissions.find(it => it.id.toString() === values.admissions?.toString()),
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
          visitTime: displayDefaultDateTime(),
          createdOn: displayDefaultDateTime(),
          updatedOn: displayDefaultDateTime(),
        }
      : {
          status: 'Planned',
          ...doctorVisitEntity,
          visitTime: convertDateTimeFromServer(doctorVisitEntity.visitTime),
          createdOn: convertDateTimeFromServer(doctorVisitEntity.createdOn),
          updatedOn: convertDateTimeFromServer(doctorVisitEntity.updatedOn),
          prescription: doctorVisitEntity?.prescription?.id,
          doctorVisitType: doctorVisitEntity?.doctorVisitType?.id,
          patient: doctorVisitEntity?.patient?.id,
          admissions: doctorVisitEntity?.admissions?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.doctorVisit.home.createOrEditLabel" data-cy="DoctorVisitCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.doctorVisit.home.createOrEditLabel">Create or edit a DoctorVisit</Translate>
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
                  id="doctor-visit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.description')}
                id="doctor-visit-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.status')}
                id="doctor-visit-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {visitStatusValues.map(visitStatus => (
                  <option value={visitStatus} key={visitStatus}>
                    {translate(`hospitalBeApp.VisitStatus.${visitStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.visitDate')}
                id="doctor-visit-visitDate"
                name="visitDate"
                data-cy="visitDate"
                type="date"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.visitTime')}
                id="doctor-visit-visitTime"
                name="visitTime"
                data-cy="visitTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.isActive')}
                id="doctor-visit-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.createdBy')}
                id="doctor-visit-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.createdOn')}
                id="doctor-visit-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.updatedBy')}
                id="doctor-visit-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisit.updatedOn')}
                id="doctor-visit-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="doctor-visit-prescription"
                name="prescription"
                data-cy="prescription"
                label={translate('hospitalBeApp.doctorVisit.prescription')}
                type="select"
              >
                <option value="" key="0" />
                {prescriptions
                  ? prescriptions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="doctor-visit-doctorVisitType"
                name="doctorVisitType"
                data-cy="doctorVisitType"
                label={translate('hospitalBeApp.doctorVisit.doctorVisitType')}
                type="select"
              >
                <option value="" key="0" />
                {doctorVisitTypes
                  ? doctorVisitTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="doctor-visit-patient"
                name="patient"
                data-cy="patient"
                label={translate('hospitalBeApp.doctorVisit.patient')}
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
                id="doctor-visit-admissions"
                name="admissions"
                data-cy="admissions"
                label={translate('hospitalBeApp.doctorVisit.admissions')}
                type="select"
              >
                <option value="" key="0" />
                {admissions
                  ? admissions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doctor-visit" replace color="info">
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

export default DoctorVisitUpdate;

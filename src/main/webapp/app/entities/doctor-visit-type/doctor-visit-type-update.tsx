import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { VisitType } from 'app/shared/model/enumerations/visit-type.model';
import { createEntity, getEntity, updateEntity } from './doctor-visit-type.reducer';

export const DoctorVisitTypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const doctorVisitTypeEntity = useAppSelector(state => state.doctorVisitType.entity);
  const loading = useAppSelector(state => state.doctorVisitType.loading);
  const updating = useAppSelector(state => state.doctorVisitType.updating);
  const updateSuccess = useAppSelector(state => state.doctorVisitType.updateSuccess);
  const visitTypeValues = Object.keys(VisitType);

  const handleClose = () => {
    navigate('/doctor-visit-type');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    if (values.fee !== undefined && typeof values.fee !== 'number') {
      values.fee = Number(values.fee);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...doctorVisitTypeEntity,
      ...values,
      doctor: employees.find(it => it.id.toString() === values.doctor?.toString()),
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
          createdOn: displayDefaultDateTime(),
          updatedOn: displayDefaultDateTime(),
        }
      : {
          type: 'InpatientVisit',
          ...doctorVisitTypeEntity,
          createdOn: convertDateTimeFromServer(doctorVisitTypeEntity.createdOn),
          updatedOn: convertDateTimeFromServer(doctorVisitTypeEntity.updatedOn),
          doctor: doctorVisitTypeEntity?.doctor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.doctorVisitType.home.createOrEditLabel" data-cy="DoctorVisitTypeCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.doctorVisitType.home.createOrEditLabel">Create or edit a DoctorVisitType</Translate>
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
                  id="doctor-visit-type-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.type')}
                id="doctor-visit-type-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {visitTypeValues.map(visitType => (
                  <option value={visitType} key={visitType}>
                    {translate(`hospitalBeApp.VisitType.${visitType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.fee')}
                id="doctor-visit-type-fee"
                name="fee"
                data-cy="fee"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.isInsuranceCovered')}
                id="doctor-visit-type-isInsuranceCovered"
                name="isInsuranceCovered"
                data-cy="isInsuranceCovered"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.isActive')}
                id="doctor-visit-type-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.createdBy')}
                id="doctor-visit-type-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.createdOn')}
                id="doctor-visit-type-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.updatedBy')}
                id="doctor-visit-type-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.doctorVisitType.updatedOn')}
                id="doctor-visit-type-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="doctor-visit-type-doctor"
                name="doctor"
                data-cy="doctor"
                label={translate('hospitalBeApp.doctorVisitType.doctor')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doctor-visit-type" replace color="info">
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

export default DoctorVisitTypeUpdate;

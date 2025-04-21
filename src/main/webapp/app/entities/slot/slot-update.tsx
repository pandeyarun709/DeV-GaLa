import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getDoctorVisitTypes } from 'app/entities/doctor-visit-type/doctor-visit-type.reducer';
import { getEntities as getDiagnosticTests } from 'app/entities/diagnostic-test/diagnostic-test.reducer';
import { Day } from 'app/shared/model/enumerations/day.model';
import { createEntity, getEntity, updateEntity } from './slot.reducer';

export const SlotUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doctorVisitTypes = useAppSelector(state => state.doctorVisitType.entities);
  const diagnosticTests = useAppSelector(state => state.diagnosticTest.entities);
  const slotEntity = useAppSelector(state => state.slot.entity);
  const loading = useAppSelector(state => state.slot.loading);
  const updating = useAppSelector(state => state.slot.updating);
  const updateSuccess = useAppSelector(state => state.slot.updateSuccess);
  const dayValues = Object.keys(Day);

  const handleClose = () => {
    navigate('/slot');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDoctorVisitTypes({}));
    dispatch(getDiagnosticTests({}));
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
    if (values.startTimeHour !== undefined && typeof values.startTimeHour !== 'number') {
      values.startTimeHour = Number(values.startTimeHour);
    }
    if (values.startTimeMin !== undefined && typeof values.startTimeMin !== 'number') {
      values.startTimeMin = Number(values.startTimeMin);
    }
    if (values.endTimeHour !== undefined && typeof values.endTimeHour !== 'number') {
      values.endTimeHour = Number(values.endTimeHour);
    }
    if (values.endTimeMin !== undefined && typeof values.endTimeMin !== 'number') {
      values.endTimeMin = Number(values.endTimeMin);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...slotEntity,
      ...values,
      doctorVisitType: doctorVisitTypes.find(it => it.id.toString() === values.doctorVisitType?.toString()),
      test: diagnosticTests.find(it => it.id.toString() === values.test?.toString()),
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
          day: 'Mon',
          ...slotEntity,
          createdOn: convertDateTimeFromServer(slotEntity.createdOn),
          updatedOn: convertDateTimeFromServer(slotEntity.updatedOn),
          doctorVisitType: slotEntity?.doctorVisitType?.id,
          test: slotEntity?.test?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.slot.home.createOrEditLabel" data-cy="SlotCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.slot.home.createOrEditLabel">Create or edit a Slot</Translate>
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
                  id="slot-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hospitalBeApp.slot.day')} id="slot-day" name="day" data-cy="day" type="select">
                {dayValues.map(day => (
                  <option value={day} key={day}>
                    {translate(`hospitalBeApp.Day.${day}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.slot.startTimeHour')}
                id="slot-startTimeHour"
                name="startTimeHour"
                data-cy="startTimeHour"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.startTimeMin')}
                id="slot-startTimeMin"
                name="startTimeMin"
                data-cy="startTimeMin"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.endTimeHour')}
                id="slot-endTimeHour"
                name="endTimeHour"
                data-cy="endTimeHour"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.endTimeMin')}
                id="slot-endTimeMin"
                name="endTimeMin"
                data-cy="endTimeMin"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.isActive')}
                id="slot-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.createdBy')}
                id="slot-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.createdOn')}
                id="slot-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.updatedBy')}
                id="slot-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.slot.updatedOn')}
                id="slot-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="slot-doctorVisitType"
                name="doctorVisitType"
                data-cy="doctorVisitType"
                label={translate('hospitalBeApp.slot.doctorVisitType')}
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
              <ValidatedField id="slot-test" name="test" data-cy="test" label={translate('hospitalBeApp.slot.test')} type="select">
                <option value="" key="0" />
                {diagnosticTests
                  ? diagnosticTests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/slot" replace color="info">
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

export default SlotUpdate;

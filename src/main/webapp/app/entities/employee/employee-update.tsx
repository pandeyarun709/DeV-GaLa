import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getQualifications } from 'app/entities/qualification/qualification.reducer';
import { getEntities as getDepartments } from 'app/entities/department/department.reducer';
import { getEntities as getDiagnosticTests } from 'app/entities/diagnostic-test/diagnostic-test.reducer';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';
import { createEntity, getEntity, updateEntity } from './employee.reducer';

export const EmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const qualifications = useAppSelector(state => state.qualification.entities);
  const departments = useAppSelector(state => state.department.entities);
  const diagnosticTests = useAppSelector(state => state.diagnosticTest.entities);
  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);
  const employeeTypeValues = Object.keys(EmployeeType);

  const handleClose = () => {
    navigate('/employee');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getQualifications({}));
    dispatch(getDepartments({}));
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
    if (values.phone !== undefined && typeof values.phone !== 'number') {
      values.phone = Number(values.phone);
    }
    if (values.rmoHourlyRate !== undefined && typeof values.rmoHourlyRate !== 'number') {
      values.rmoHourlyRate = Number(values.rmoHourlyRate);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...employeeEntity,
      ...values,
      qualifications: mapIdList(values.qualifications),
      department: departments.find(it => it.id.toString() === values.department?.toString()),
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
          type: 'Doctor',
          ...employeeEntity,
          createdOn: convertDateTimeFromServer(employeeEntity.createdOn),
          updatedOn: convertDateTimeFromServer(employeeEntity.updatedOn),
          qualifications: employeeEntity?.qualifications?.map(e => e.id.toString()),
          department: employeeEntity?.department?.id,
          test: employeeEntity?.test?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.employee.home.createOrEditLabel" data-cy="EmployeeCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
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
                  id="employee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hospitalBeApp.employee.name')} id="employee-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.employee.phone')}
                id="employee-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.email')}
                id="employee-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.registrationNo')}
                id="employee-registrationNo"
                name="registrationNo"
                data-cy="registrationNo"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.rmoHourlyRate')}
                id="employee-rmoHourlyRate"
                name="rmoHourlyRate"
                data-cy="rmoHourlyRate"
                type="text"
              />
              <ValidatedField label={translate('hospitalBeApp.employee.type')} id="employee-type" name="type" data-cy="type" type="select">
                {employeeTypeValues.map(employeeType => (
                  <option value={employeeType} key={employeeType}>
                    {translate(`hospitalBeApp.EmployeeType.${employeeType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.employee.isActive')}
                id="employee-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.createdBy')}
                id="employee-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.createdOn')}
                id="employee-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.updatedBy')}
                id="employee-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.updatedOn')}
                id="employee-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.employee.qualifications')}
                id="employee-qualifications"
                data-cy="qualifications"
                type="select"
                multiple
                name="qualifications"
              >
                <option value="" key="0" />
                {qualifications
                  ? qualifications.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-department"
                name="department"
                data-cy="department"
                label={translate('hospitalBeApp.employee.department')}
                type="select"
              >
                <option value="" key="0" />
                {departments
                  ? departments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employee-test" name="test" data-cy="test" label={translate('hospitalBeApp.employee.test')} type="select">
                <option value="" key="0" />
                {diagnosticTests
                  ? diagnosticTests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee" replace color="info">
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

export default EmployeeUpdate;

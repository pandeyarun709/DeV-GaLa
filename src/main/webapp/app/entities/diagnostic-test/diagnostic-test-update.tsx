import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getDepartments } from 'app/entities/department/department.reducer';
import { getEntities as getPrescriptions } from 'app/entities/prescription/prescription.reducer';
import { createEntity, getEntity, updateEntity } from './diagnostic-test.reducer';

export const DiagnosticTestUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const departments = useAppSelector(state => state.department.entities);
  const prescriptions = useAppSelector(state => state.prescription.entities);
  const diagnosticTestEntity = useAppSelector(state => state.diagnosticTest.entity);
  const loading = useAppSelector(state => state.diagnosticTest.loading);
  const updating = useAppSelector(state => state.diagnosticTest.updating);
  const updateSuccess = useAppSelector(state => state.diagnosticTest.updateSuccess);

  const handleClose = () => {
    navigate('/diagnostic-test');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDepartments({}));
    dispatch(getPrescriptions({}));
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
    if (values.fee !== undefined && typeof values.fee !== 'number') {
      values.fee = Number(values.fee);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...diagnosticTestEntity,
      ...values,
      department: departments.find(it => it.id.toString() === values.department?.toString()),
      prescription: prescriptions.find(it => it.id.toString() === values.prescription?.toString()),
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
          ...diagnosticTestEntity,
          createdOn: convertDateTimeFromServer(diagnosticTestEntity.createdOn),
          updatedOn: convertDateTimeFromServer(diagnosticTestEntity.updatedOn),
          department: diagnosticTestEntity?.department?.id,
          prescription: diagnosticTestEntity?.prescription?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.diagnosticTest.home.createOrEditLabel" data-cy="DiagnosticTestCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.diagnosticTest.home.createOrEditLabel">Create or edit a DiagnosticTest</Translate>
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
                  id="diagnostic-test-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.name')}
                id="diagnostic-test-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.phone')}
                id="diagnostic-test-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.email')}
                id="diagnostic-test-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.fee')}
                id="diagnostic-test-fee"
                name="fee"
                data-cy="fee"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.isInsuranceCovered')}
                id="diagnostic-test-isInsuranceCovered"
                name="isInsuranceCovered"
                data-cy="isInsuranceCovered"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.isActive')}
                id="diagnostic-test-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.createdBy')}
                id="diagnostic-test-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.createdOn')}
                id="diagnostic-test-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.updatedBy')}
                id="diagnostic-test-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTest.updatedOn')}
                id="diagnostic-test-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="diagnostic-test-department"
                name="department"
                data-cy="department"
                label={translate('hospitalBeApp.diagnosticTest.department')}
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
              <ValidatedField
                id="diagnostic-test-prescription"
                name="prescription"
                data-cy="prescription"
                label={translate('hospitalBeApp.diagnosticTest.prescription')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/diagnostic-test" replace color="info">
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

export default DiagnosticTestUpdate;

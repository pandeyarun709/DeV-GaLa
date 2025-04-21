import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { getEntities as getDiagnosticTests } from 'app/entities/diagnostic-test/diagnostic-test.reducer';
import { getEntities as getAdmissions } from 'app/entities/admission/admission.reducer';
import { createEntity, getEntity, updateEntity } from './diagnostic-test-report.reducer';

export const DiagnosticTestReportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const patients = useAppSelector(state => state.patient.entities);
  const diagnosticTests = useAppSelector(state => state.diagnosticTest.entities);
  const admissions = useAppSelector(state => state.admission.entities);
  const diagnosticTestReportEntity = useAppSelector(state => state.diagnosticTestReport.entity);
  const loading = useAppSelector(state => state.diagnosticTestReport.loading);
  const updating = useAppSelector(state => state.diagnosticTestReport.updating);
  const updateSuccess = useAppSelector(state => state.diagnosticTestReport.updateSuccess);

  const handleClose = () => {
    navigate('/diagnostic-test-report');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPatients({}));
    dispatch(getDiagnosticTests({}));
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
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...diagnosticTestReportEntity,
      ...values,
      patient: patients.find(it => it.id.toString() === values.patient?.toString()),
      test: diagnosticTests.find(it => it.id.toString() === values.test?.toString()),
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
          createdOn: displayDefaultDateTime(),
          updatedOn: displayDefaultDateTime(),
        }
      : {
          ...diagnosticTestReportEntity,
          createdOn: convertDateTimeFromServer(diagnosticTestReportEntity.createdOn),
          updatedOn: convertDateTimeFromServer(diagnosticTestReportEntity.updatedOn),
          patient: diagnosticTestReportEntity?.patient?.id,
          test: diagnosticTestReportEntity?.test?.id,
          admissions: diagnosticTestReportEntity?.admissions?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.diagnosticTestReport.home.createOrEditLabel" data-cy="DiagnosticTestReportCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.diagnosticTestReport.home.createOrEditLabel">
              Create or edit a DiagnosticTestReport
            </Translate>
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
                  id="diagnostic-test-report-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTestReport.description')}
                id="diagnostic-test-report-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTestReport.signedBy')}
                id="diagnostic-test-report-signedBy"
                name="signedBy"
                data-cy="signedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTestReport.isActive')}
                id="diagnostic-test-report-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTestReport.createdBy')}
                id="diagnostic-test-report-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTestReport.createdOn')}
                id="diagnostic-test-report-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTestReport.updatedBy')}
                id="diagnostic-test-report-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.diagnosticTestReport.updatedOn')}
                id="diagnostic-test-report-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="diagnostic-test-report-patient"
                name="patient"
                data-cy="patient"
                label={translate('hospitalBeApp.diagnosticTestReport.patient')}
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
                id="diagnostic-test-report-test"
                name="test"
                data-cy="test"
                label={translate('hospitalBeApp.diagnosticTestReport.test')}
                type="select"
              >
                <option value="" key="0" />
                {diagnosticTests
                  ? diagnosticTests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="diagnostic-test-report-admissions"
                name="admissions"
                data-cy="admissions"
                label={translate('hospitalBeApp.diagnosticTestReport.admissions')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/diagnostic-test-report" replace color="info">
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

export default DiagnosticTestReportUpdate;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './diagnostic-test-report.reducer';

export const DiagnosticTestReportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const diagnosticTestReportEntity = useAppSelector(state => state.diagnosticTestReport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="diagnosticTestReportDetailsHeading">
          <Translate contentKey="hospitalBeApp.diagnosticTestReport.detail.title">DiagnosticTestReport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestReportEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hospitalBeApp.diagnosticTestReport.description">Description</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestReportEntity.description}</dd>
          <dt>
            <span id="signedBy">
              <Translate contentKey="hospitalBeApp.diagnosticTestReport.signedBy">Signed By</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestReportEntity.signedBy}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.diagnosticTestReport.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestReportEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.diagnosticTestReport.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestReportEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.diagnosticTestReport.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {diagnosticTestReportEntity.createdOn ? (
              <TextFormat value={diagnosticTestReportEntity.createdOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.diagnosticTestReport.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestReportEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.diagnosticTestReport.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {diagnosticTestReportEntity.updatedOn ? (
              <TextFormat value={diagnosticTestReportEntity.updatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.diagnosticTestReport.patient">Patient</Translate>
          </dt>
          <dd>{diagnosticTestReportEntity.patient ? diagnosticTestReportEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.diagnosticTestReport.test">Test</Translate>
          </dt>
          <dd>{diagnosticTestReportEntity.test ? diagnosticTestReportEntity.test.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.diagnosticTestReport.admissions">Admissions</Translate>
          </dt>
          <dd>{diagnosticTestReportEntity.admissions ? diagnosticTestReportEntity.admissions.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/diagnostic-test-report" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/diagnostic-test-report/${diagnosticTestReportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DiagnosticTestReportDetail;

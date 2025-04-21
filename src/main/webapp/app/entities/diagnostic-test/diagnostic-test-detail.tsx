import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './diagnostic-test.reducer';

export const DiagnosticTestDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const diagnosticTestEntity = useAppSelector(state => state.diagnosticTest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="diagnosticTestDetailsHeading">
          <Translate contentKey="hospitalBeApp.diagnosticTest.detail.title">DiagnosticTest</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.diagnosticTest.name">Name</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hospitalBeApp.diagnosticTest.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.phone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="hospitalBeApp.diagnosticTest.email">Email</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.email}</dd>
          <dt>
            <span id="fee">
              <Translate contentKey="hospitalBeApp.diagnosticTest.fee">Fee</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.fee}</dd>
          <dt>
            <span id="isInsuranceCovered">
              <Translate contentKey="hospitalBeApp.diagnosticTest.isInsuranceCovered">Is Insurance Covered</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.isInsuranceCovered ? 'true' : 'false'}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.diagnosticTest.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.diagnosticTest.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.diagnosticTest.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {diagnosticTestEntity.createdOn ? (
              <TextFormat value={diagnosticTestEntity.createdOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.diagnosticTest.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{diagnosticTestEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.diagnosticTest.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {diagnosticTestEntity.updatedOn ? (
              <TextFormat value={diagnosticTestEntity.updatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.diagnosticTest.department">Department</Translate>
          </dt>
          <dd>{diagnosticTestEntity.department ? diagnosticTestEntity.department.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.diagnosticTest.prescription">Prescription</Translate>
          </dt>
          <dd>{diagnosticTestEntity.prescription ? diagnosticTestEntity.prescription.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/diagnostic-test" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/diagnostic-test/${diagnosticTestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DiagnosticTestDetail;

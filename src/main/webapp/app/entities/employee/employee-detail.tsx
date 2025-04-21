import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">
          <Translate contentKey="hospitalBeApp.employee.detail.title">Employee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.employee.name">Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hospitalBeApp.employee.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.phone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="hospitalBeApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="registrationNo">
              <Translate contentKey="hospitalBeApp.employee.registrationNo">Registration No</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.registrationNo}</dd>
          <dt>
            <span id="rmoHourlyRate">
              <Translate contentKey="hospitalBeApp.employee.rmoHourlyRate">Rmo Hourly Rate</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.rmoHourlyRate}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hospitalBeApp.employee.type">Type</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.type}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.employee.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.employee.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.employee.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.createdOn ? <TextFormat value={employeeEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.employee.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.employee.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.updatedOn ? <TextFormat value={employeeEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.employee.qualifications">Qualifications</Translate>
          </dt>
          <dd>
            {employeeEntity.qualifications
              ? employeeEntity.qualifications.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {employeeEntity.qualifications && i === employeeEntity.qualifications.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.employee.department">Department</Translate>
          </dt>
          <dd>{employeeEntity.department ? employeeEntity.department.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.employee.test">Test</Translate>
          </dt>
          <dd>{employeeEntity.test ? employeeEntity.test.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;

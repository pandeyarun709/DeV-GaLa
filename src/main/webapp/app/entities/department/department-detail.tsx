import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './department.reducer';

export const DepartmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const departmentEntity = useAppSelector(state => state.department.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="departmentDetailsHeading">
          <Translate contentKey="hospitalBeApp.department.detail.title">Department</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.department.name">Name</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hospitalBeApp.department.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.phone}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.department.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.department.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.department.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {departmentEntity.createdOn ? <TextFormat value={departmentEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.department.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.department.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {departmentEntity.updatedOn ? <TextFormat value={departmentEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.department.hospital">Hospital</Translate>
          </dt>
          <dd>{departmentEntity.hospital ? departmentEntity.hospital.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/department" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/department/${departmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepartmentDetail;

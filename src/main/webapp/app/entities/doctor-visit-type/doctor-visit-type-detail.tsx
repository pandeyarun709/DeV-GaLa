import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doctor-visit-type.reducer';

export const DoctorVisitTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doctorVisitTypeEntity = useAppSelector(state => state.doctorVisitType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doctorVisitTypeDetailsHeading">
          <Translate contentKey="hospitalBeApp.doctorVisitType.detail.title">DoctorVisitType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{doctorVisitTypeEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hospitalBeApp.doctorVisitType.type">Type</Translate>
            </span>
          </dt>
          <dd>{doctorVisitTypeEntity.type}</dd>
          <dt>
            <span id="fee">
              <Translate contentKey="hospitalBeApp.doctorVisitType.fee">Fee</Translate>
            </span>
          </dt>
          <dd>{doctorVisitTypeEntity.fee}</dd>
          <dt>
            <span id="isInsuranceCovered">
              <Translate contentKey="hospitalBeApp.doctorVisitType.isInsuranceCovered">Is Insurance Covered</Translate>
            </span>
          </dt>
          <dd>{doctorVisitTypeEntity.isInsuranceCovered ? 'true' : 'false'}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.doctorVisitType.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{doctorVisitTypeEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.doctorVisitType.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{doctorVisitTypeEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.doctorVisitType.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {doctorVisitTypeEntity.createdOn ? (
              <TextFormat value={doctorVisitTypeEntity.createdOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.doctorVisitType.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{doctorVisitTypeEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.doctorVisitType.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {doctorVisitTypeEntity.updatedOn ? (
              <TextFormat value={doctorVisitTypeEntity.updatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.doctorVisitType.doctor">Doctor</Translate>
          </dt>
          <dd>{doctorVisitTypeEntity.doctor ? doctorVisitTypeEntity.doctor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/doctor-visit-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctor-visit-type/${doctorVisitTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoctorVisitTypeDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doctor-visit.reducer';

export const DoctorVisitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doctorVisitEntity = useAppSelector(state => state.doctorVisit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doctorVisitDetailsHeading">
          <Translate contentKey="hospitalBeApp.doctorVisit.detail.title">DoctorVisit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{doctorVisitEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hospitalBeApp.doctorVisit.description">Description</Translate>
            </span>
          </dt>
          <dd>{doctorVisitEntity.description}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="hospitalBeApp.doctorVisit.status">Status</Translate>
            </span>
          </dt>
          <dd>{doctorVisitEntity.status}</dd>
          <dt>
            <span id="visitDate">
              <Translate contentKey="hospitalBeApp.doctorVisit.visitDate">Visit Date</Translate>
            </span>
          </dt>
          <dd>
            {doctorVisitEntity.visitDate ? (
              <TextFormat value={doctorVisitEntity.visitDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="visitTime">
              <Translate contentKey="hospitalBeApp.doctorVisit.visitTime">Visit Time</Translate>
            </span>
          </dt>
          <dd>
            {doctorVisitEntity.visitTime ? <TextFormat value={doctorVisitEntity.visitTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.doctorVisit.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{doctorVisitEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.doctorVisit.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{doctorVisitEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.doctorVisit.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {doctorVisitEntity.createdOn ? <TextFormat value={doctorVisitEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.doctorVisit.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{doctorVisitEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.doctorVisit.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {doctorVisitEntity.updatedOn ? <TextFormat value={doctorVisitEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.doctorVisit.prescription">Prescription</Translate>
          </dt>
          <dd>{doctorVisitEntity.prescription ? doctorVisitEntity.prescription.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.doctorVisit.doctorVisitType">Doctor Visit Type</Translate>
          </dt>
          <dd>{doctorVisitEntity.doctorVisitType ? doctorVisitEntity.doctorVisitType.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.doctorVisit.patient">Patient</Translate>
          </dt>
          <dd>{doctorVisitEntity.patient ? doctorVisitEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.doctorVisit.admissions">Admissions</Translate>
          </dt>
          <dd>{doctorVisitEntity.admissions ? doctorVisitEntity.admissions.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/doctor-visit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctor-visit/${doctorVisitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoctorVisitDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './patient.reducer';

export const PatientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const patientEntity = useAppSelector(state => state.patient.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientDetailsHeading">
          <Translate contentKey="hospitalBeApp.patient.detail.title">Patient</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{patientEntity.id}</dd>
          <dt>
            <span id="registrationId">
              <Translate contentKey="hospitalBeApp.patient.registrationId">Registration Id</Translate>
            </span>
          </dt>
          <dd>{patientEntity.registrationId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.patient.name">Name</Translate>
            </span>
          </dt>
          <dd>{patientEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hospitalBeApp.patient.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{patientEntity.phone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="hospitalBeApp.patient.email">Email</Translate>
            </span>
          </dt>
          <dd>{patientEntity.email}</dd>
          <dt>
            <span id="sex">
              <Translate contentKey="hospitalBeApp.patient.sex">Sex</Translate>
            </span>
          </dt>
          <dd>{patientEntity.sex}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="hospitalBeApp.patient.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {patientEntity.dateOfBirth ? <TextFormat value={patientEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="bloodGroup">
              <Translate contentKey="hospitalBeApp.patient.bloodGroup">Blood Group</Translate>
            </span>
          </dt>
          <dd>{patientEntity.bloodGroup}</dd>
          <dt>
            <span id="emergencyContactName">
              <Translate contentKey="hospitalBeApp.patient.emergencyContactName">Emergency Contact Name</Translate>
            </span>
          </dt>
          <dd>{patientEntity.emergencyContactName}</dd>
          <dt>
            <span id="emergencyContactPhone">
              <Translate contentKey="hospitalBeApp.patient.emergencyContactPhone">Emergency Contact Phone</Translate>
            </span>
          </dt>
          <dd>{patientEntity.emergencyContactPhone}</dd>
          <dt>
            <span id="emergencyContactEmail">
              <Translate contentKey="hospitalBeApp.patient.emergencyContactEmail">Emergency Contact Email</Translate>
            </span>
          </dt>
          <dd>{patientEntity.emergencyContactEmail}</dd>
          <dt>
            <span id="emergencyContactRelationShip">
              <Translate contentKey="hospitalBeApp.patient.emergencyContactRelationShip">Emergency Contact Relation Ship</Translate>
            </span>
          </dt>
          <dd>{patientEntity.emergencyContactRelationShip}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.patient.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{patientEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.patient.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{patientEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.patient.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{patientEntity.createdOn ? <TextFormat value={patientEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.patient.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{patientEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.patient.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{patientEntity.updatedOn ? <TextFormat value={patientEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.patient.address">Address</Translate>
          </dt>
          <dd>{patientEntity.address ? patientEntity.address.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/patient" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient/${patientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientDetail;

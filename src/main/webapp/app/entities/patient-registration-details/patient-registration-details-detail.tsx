import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './patient-registration-details.reducer';

export const PatientRegistrationDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const patientRegistrationDetailsEntity = useAppSelector(state => state.patientRegistrationDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientRegistrationDetailsDetailsHeading">
          <Translate contentKey="hospitalBeApp.patientRegistrationDetails.detail.title">PatientRegistrationDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{patientRegistrationDetailsEntity.id}</dd>
          <dt>
            <span id="registrationId">
              <Translate contentKey="hospitalBeApp.patientRegistrationDetails.registrationId">Registration Id</Translate>
            </span>
          </dt>
          <dd>{patientRegistrationDetailsEntity.registrationId}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.patientRegistrationDetails.client">Client</Translate>
          </dt>
          <dd>{patientRegistrationDetailsEntity.client ? patientRegistrationDetailsEntity.client.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.patientRegistrationDetails.hospital">Hospital</Translate>
          </dt>
          <dd>{patientRegistrationDetailsEntity.hospital ? patientRegistrationDetailsEntity.hospital.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.patientRegistrationDetails.referredBy">Referred By</Translate>
          </dt>
          <dd>{patientRegistrationDetailsEntity.referredBy ? patientRegistrationDetailsEntity.referredBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/patient-registration-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient-registration-details/${patientRegistrationDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientRegistrationDetailsDetail;

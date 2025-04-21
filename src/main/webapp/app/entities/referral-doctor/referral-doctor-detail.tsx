import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './referral-doctor.reducer';

export const ReferralDoctorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const referralDoctorEntity = useAppSelector(state => state.referralDoctor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="referralDoctorDetailsHeading">
          <Translate contentKey="hospitalBeApp.referralDoctor.detail.title">ReferralDoctor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.referralDoctor.name">Name</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hospitalBeApp.referralDoctor.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.phone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="hospitalBeApp.referralDoctor.email">Email</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.email}</dd>
          <dt>
            <span id="registrationNo">
              <Translate contentKey="hospitalBeApp.referralDoctor.registrationNo">Registration No</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.registrationNo}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.referralDoctor.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.referralDoctor.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.referralDoctor.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {referralDoctorEntity.createdOn ? (
              <TextFormat value={referralDoctorEntity.createdOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.referralDoctor.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{referralDoctorEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.referralDoctor.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {referralDoctorEntity.updatedOn ? (
              <TextFormat value={referralDoctorEntity.updatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/referral-doctor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/referral-doctor/${referralDoctorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReferralDoctorDetail;

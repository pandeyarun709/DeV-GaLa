import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hospital.reducer';

export const HospitalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hospitalEntity = useAppSelector(state => state.hospital.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hospitalDetailsHeading">
          <Translate contentKey="hospitalBeApp.hospital.detail.title">Hospital</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.hospital.name">Name</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.name}</dd>
          <dt>
            <span id="logoPath">
              <Translate contentKey="hospitalBeApp.hospital.logoPath">Logo Path</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.logoPath}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.hospital.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.hospital.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.hospital.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.createdOn ? <TextFormat value={hospitalEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.hospital.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.hospital.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{hospitalEntity.updatedOn ? <TextFormat value={hospitalEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.hospital.address">Address</Translate>
          </dt>
          <dd>{hospitalEntity.address ? hospitalEntity.address.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.hospital.client">Client</Translate>
          </dt>
          <dd>{hospitalEntity.client ? hospitalEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/hospital" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hospital/${hospitalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HospitalDetail;

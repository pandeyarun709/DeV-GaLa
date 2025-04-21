import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prescription.reducer';

export const PrescriptionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const prescriptionEntity = useAppSelector(state => state.prescription.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="prescriptionDetailsHeading">
          <Translate contentKey="hospitalBeApp.prescription.detail.title">Prescription</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.id}</dd>
          <dt>
            <span id="history">
              <Translate contentKey="hospitalBeApp.prescription.history">History</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.history}</dd>
          <dt>
            <span id="compliant">
              <Translate contentKey="hospitalBeApp.prescription.compliant">Compliant</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.compliant}</dd>
          <dt>
            <span id="height">
              <Translate contentKey="hospitalBeApp.prescription.height">Height</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.height}</dd>
          <dt>
            <span id="weight">
              <Translate contentKey="hospitalBeApp.prescription.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.weight}</dd>
          <dt>
            <span id="bpHigh">
              <Translate contentKey="hospitalBeApp.prescription.bpHigh">Bp High</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.bpHigh}</dd>
          <dt>
            <span id="bpLow">
              <Translate contentKey="hospitalBeApp.prescription.bpLow">Bp Low</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.bpLow}</dd>
          <dt>
            <span id="temperature">
              <Translate contentKey="hospitalBeApp.prescription.temperature">Temperature</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.temperature}</dd>
          <dt>
            <span id="otherVital">
              <Translate contentKey="hospitalBeApp.prescription.otherVital">Other Vital</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.otherVital}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hospitalBeApp.prescription.description">Description</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.description}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.prescription.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.prescription.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.prescription.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {prescriptionEntity.createdOn ? <TextFormat value={prescriptionEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.prescription.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{prescriptionEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.prescription.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {prescriptionEntity.updatedOn ? <TextFormat value={prescriptionEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/prescription" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prescription/${prescriptionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PrescriptionDetail;

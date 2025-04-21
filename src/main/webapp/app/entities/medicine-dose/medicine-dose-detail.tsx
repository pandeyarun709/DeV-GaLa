import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './medicine-dose.reducer';

export const MedicineDoseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const medicineDoseEntity = useAppSelector(state => state.medicineDose.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="medicineDoseDetailsHeading">
          <Translate contentKey="hospitalBeApp.medicineDose.detail.title">MedicineDose</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{medicineDoseEntity.id}</dd>
          <dt>
            <span id="frequency">
              <Translate contentKey="hospitalBeApp.medicineDose.frequency">Frequency</Translate>
            </span>
          </dt>
          <dd>{medicineDoseEntity.frequency}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.medicineDose.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{medicineDoseEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.medicineDose.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{medicineDoseEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.medicineDose.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {medicineDoseEntity.createdOn ? <TextFormat value={medicineDoseEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.medicineDose.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{medicineDoseEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.medicineDose.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {medicineDoseEntity.updatedOn ? <TextFormat value={medicineDoseEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.medicineDose.prescription">Prescription</Translate>
          </dt>
          <dd>{medicineDoseEntity.prescription ? medicineDoseEntity.prescription.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.medicineDose.medicine">Medicine</Translate>
          </dt>
          <dd>{medicineDoseEntity.medicine ? medicineDoseEntity.medicine.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/medicine-dose" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medicine-dose/${medicineDoseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MedicineDoseDetail;

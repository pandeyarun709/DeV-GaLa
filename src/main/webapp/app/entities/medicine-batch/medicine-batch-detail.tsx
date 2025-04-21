import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './medicine-batch.reducer';

export const MedicineBatchDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const medicineBatchEntity = useAppSelector(state => state.medicineBatch.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="medicineBatchDetailsHeading">
          <Translate contentKey="hospitalBeApp.medicineBatch.detail.title">MedicineBatch</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.id}</dd>
          <dt>
            <span id="batchNo">
              <Translate contentKey="hospitalBeApp.medicineBatch.batchNo">Batch No</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.batchNo}</dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="hospitalBeApp.medicineBatch.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {medicineBatchEntity.expiryDate ? (
              <TextFormat value={medicineBatchEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="hospitalBeApp.medicineBatch.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.quantity}</dd>
          <dt>
            <span id="sellingPrice">
              <Translate contentKey="hospitalBeApp.medicineBatch.sellingPrice">Selling Price</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.sellingPrice}</dd>
          <dt>
            <span id="storageLocation">
              <Translate contentKey="hospitalBeApp.medicineBatch.storageLocation">Storage Location</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.storageLocation}</dd>
          <dt>
            <span id="rackNo">
              <Translate contentKey="hospitalBeApp.medicineBatch.rackNo">Rack No</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.rackNo}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.medicineBatch.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.medicineBatch.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.medicineBatch.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {medicineBatchEntity.createdOn ? (
              <TextFormat value={medicineBatchEntity.createdOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.medicineBatch.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{medicineBatchEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.medicineBatch.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {medicineBatchEntity.updatedOn ? (
              <TextFormat value={medicineBatchEntity.updatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.medicineBatch.med">Med</Translate>
          </dt>
          <dd>{medicineBatchEntity.med ? medicineBatchEntity.med.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.medicineBatch.hospital">Hospital</Translate>
          </dt>
          <dd>{medicineBatchEntity.hospital ? medicineBatchEntity.hospital.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.medicineBatch.admissions">Admissions</Translate>
          </dt>
          <dd>{medicineBatchEntity.admissions ? medicineBatchEntity.admissions.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/medicine-batch" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medicine-batch/${medicineBatchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MedicineBatchDetail;

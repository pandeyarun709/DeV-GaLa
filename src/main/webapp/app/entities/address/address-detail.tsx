import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="hospitalBeApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="line1">
              <Translate contentKey="hospitalBeApp.address.line1">Line 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.line1}</dd>
          <dt>
            <span id="line2">
              <Translate contentKey="hospitalBeApp.address.line2">Line 2</Translate>
            </span>
          </dt>
          <dd>{addressEntity.line2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="hospitalBeApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="hospitalBeApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="hospitalBeApp.address.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{addressEntity.pincode}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="hospitalBeApp.address.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="hospitalBeApp.address.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lon}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.address.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{addressEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.address.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.address.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{addressEntity.createdOn ? <TextFormat value={addressEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.address.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.address.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{addressEntity.updatedOn ? <TextFormat value={addressEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;

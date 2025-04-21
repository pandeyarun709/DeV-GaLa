import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bed.reducer';

export const BedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bedEntity = useAppSelector(state => state.bed.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bedDetailsHeading">
          <Translate contentKey="hospitalBeApp.bed.detail.title">Bed</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bedEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hospitalBeApp.bed.type">Type</Translate>
            </span>
          </dt>
          <dd>{bedEntity.type}</dd>
          <dt>
            <span id="floor">
              <Translate contentKey="hospitalBeApp.bed.floor">Floor</Translate>
            </span>
          </dt>
          <dd>{bedEntity.floor}</dd>
          <dt>
            <span id="roomNo">
              <Translate contentKey="hospitalBeApp.bed.roomNo">Room No</Translate>
            </span>
          </dt>
          <dd>{bedEntity.roomNo}</dd>
          <dt>
            <span id="dailyRate">
              <Translate contentKey="hospitalBeApp.bed.dailyRate">Daily Rate</Translate>
            </span>
          </dt>
          <dd>{bedEntity.dailyRate}</dd>
          <dt>
            <span id="isInsuranceCovered">
              <Translate contentKey="hospitalBeApp.bed.isInsuranceCovered">Is Insurance Covered</Translate>
            </span>
          </dt>
          <dd>{bedEntity.isInsuranceCovered ? 'true' : 'false'}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.bed.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{bedEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.bed.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bedEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.bed.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{bedEntity.createdOn ? <TextFormat value={bedEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.bed.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{bedEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.bed.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{bedEntity.updatedOn ? <TextFormat value={bedEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.bed.hospital">Hospital</Translate>
          </dt>
          <dd>{bedEntity.hospital ? bedEntity.hospital.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.bed.admissions">Admissions</Translate>
          </dt>
          <dd>
            {bedEntity.admissions
              ? bedEntity.admissions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {bedEntity.admissions && i === bedEntity.admissions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bed" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bed/${bedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BedDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './slot.reducer';

export const SlotDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const slotEntity = useAppSelector(state => state.slot.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="slotDetailsHeading">
          <Translate contentKey="hospitalBeApp.slot.detail.title">Slot</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{slotEntity.id}</dd>
          <dt>
            <span id="day">
              <Translate contentKey="hospitalBeApp.slot.day">Day</Translate>
            </span>
          </dt>
          <dd>{slotEntity.day}</dd>
          <dt>
            <span id="startTimeHour">
              <Translate contentKey="hospitalBeApp.slot.startTimeHour">Start Time Hour</Translate>
            </span>
          </dt>
          <dd>{slotEntity.startTimeHour}</dd>
          <dt>
            <span id="startTimeMin">
              <Translate contentKey="hospitalBeApp.slot.startTimeMin">Start Time Min</Translate>
            </span>
          </dt>
          <dd>{slotEntity.startTimeMin}</dd>
          <dt>
            <span id="endTimeHour">
              <Translate contentKey="hospitalBeApp.slot.endTimeHour">End Time Hour</Translate>
            </span>
          </dt>
          <dd>{slotEntity.endTimeHour}</dd>
          <dt>
            <span id="endTimeMin">
              <Translate contentKey="hospitalBeApp.slot.endTimeMin">End Time Min</Translate>
            </span>
          </dt>
          <dd>{slotEntity.endTimeMin}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.slot.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{slotEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.slot.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{slotEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.slot.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{slotEntity.createdOn ? <TextFormat value={slotEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.slot.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{slotEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.slot.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{slotEntity.updatedOn ? <TextFormat value={slotEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.slot.doctorVisitType">Doctor Visit Type</Translate>
          </dt>
          <dd>{slotEntity.doctorVisitType ? slotEntity.doctorVisitType.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.slot.test">Test</Translate>
          </dt>
          <dd>{slotEntity.test ? slotEntity.test.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/slot" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/slot/${slotEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SlotDetail;

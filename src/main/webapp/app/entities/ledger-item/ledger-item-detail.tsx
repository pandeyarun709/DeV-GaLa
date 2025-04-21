import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ledger-item.reducer';

export const LedgerItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ledgerItemEntity = useAppSelector(state => state.ledgerItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ledgerItemDetailsHeading">
          <Translate contentKey="hospitalBeApp.ledgerItem.detail.title">LedgerItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.id}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="hospitalBeApp.ledgerItem.time">Time</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.time ? <TextFormat value={ledgerItemEntity.time} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hospitalBeApp.ledgerItem.description">Description</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.description}</dd>
          <dt>
            <span id="fee">
              <Translate contentKey="hospitalBeApp.ledgerItem.fee">Fee</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.fee}</dd>
          <dt>
            <span id="isCoveredByInsurance">
              <Translate contentKey="hospitalBeApp.ledgerItem.isCoveredByInsurance">Is Covered By Insurance</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.isCoveredByInsurance ? 'true' : 'false'}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.ledgerItem.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.ledgerItem.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.ledgerItem.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {ledgerItemEntity.createdOn ? <TextFormat value={ledgerItemEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.ledgerItem.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{ledgerItemEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.ledgerItem.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {ledgerItemEntity.updatedOn ? <TextFormat value={ledgerItemEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.ledgerItem.admission">Admission</Translate>
          </dt>
          <dd>{ledgerItemEntity.admission ? ledgerItemEntity.admission.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ledger-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ledger-item/${ledgerItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LedgerItemDetail;

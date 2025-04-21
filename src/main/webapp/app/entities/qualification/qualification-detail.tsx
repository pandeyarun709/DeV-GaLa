import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './qualification.reducer';

export const QualificationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const qualificationEntity = useAppSelector(state => state.qualification.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="qualificationDetailsHeading">
          <Translate contentKey="hospitalBeApp.qualification.detail.title">Qualification</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{qualificationEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.qualification.name">Name</Translate>
            </span>
          </dt>
          <dd>{qualificationEntity.name}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.qualification.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{qualificationEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.qualification.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{qualificationEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.qualification.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {qualificationEntity.createdOn ? (
              <TextFormat value={qualificationEntity.createdOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.qualification.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{qualificationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.qualification.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {qualificationEntity.updatedOn ? (
              <TextFormat value={qualificationEntity.updatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.qualification.employees">Employees</Translate>
          </dt>
          <dd>
            {qualificationEntity.employees
              ? qualificationEntity.employees.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {qualificationEntity.employees && i === qualificationEntity.employees.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/qualification" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/qualification/${qualificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QualificationDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './admission.reducer';

export const AdmissionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const admissionEntity = useAppSelector(state => state.admission.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="admissionDetailsHeading">
          <Translate contentKey="hospitalBeApp.admission.detail.title">Admission</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.id}</dd>
          <dt>
            <span id="details">
              <Translate contentKey="hospitalBeApp.admission.details">Details</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.details}</dd>
          <dt>
            <span id="admissionStatus">
              <Translate contentKey="hospitalBeApp.admission.admissionStatus">Admission Status</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.admissionStatus}</dd>
          <dt>
            <span id="dischargeStatus">
              <Translate contentKey="hospitalBeApp.admission.dischargeStatus">Discharge Status</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.dischargeStatus}</dd>
          <dt>
            <span id="admissionTime">
              <Translate contentKey="hospitalBeApp.admission.admissionTime">Admission Time</Translate>
            </span>
          </dt>
          <dd>
            {admissionEntity.admissionTime ? (
              <TextFormat value={admissionEntity.admissionTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dischargeTime">
              <Translate contentKey="hospitalBeApp.admission.dischargeTime">Discharge Time</Translate>
            </span>
          </dt>
          <dd>
            {admissionEntity.dischargeTime ? (
              <TextFormat value={admissionEntity.dischargeTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="hospitalBeApp.admission.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.paymentStatus}</dd>
          <dt>
            <span id="totalBillAmount">
              <Translate contentKey="hospitalBeApp.admission.totalBillAmount">Total Bill Amount</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.totalBillAmount}</dd>
          <dt>
            <span id="insuranceCoveredAmount">
              <Translate contentKey="hospitalBeApp.admission.insuranceCoveredAmount">Insurance Covered Amount</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.insuranceCoveredAmount}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.admission.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.admission.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.admission.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {admissionEntity.createdOn ? <TextFormat value={admissionEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.admission.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{admissionEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.admission.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {admissionEntity.updatedOn ? <TextFormat value={admissionEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.admission.beds">Beds</Translate>
          </dt>
          <dd>
            {admissionEntity.beds
              ? admissionEntity.beds.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {admissionEntity.beds && i === admissionEntity.beds.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="hospitalBeApp.admission.patient">Patient</Translate>
          </dt>
          <dd>{admissionEntity.patient ? admissionEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.admission.hospital">Hospital</Translate>
          </dt>
          <dd>{admissionEntity.hospital ? admissionEntity.hospital.id : ''}</dd>
          <dt>
            <Translate contentKey="hospitalBeApp.admission.admittedUnder">Admitted Under</Translate>
          </dt>
          <dd>{admissionEntity.admittedUnder ? admissionEntity.admittedUnder.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/admission" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admission/${admissionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdmissionDetail;

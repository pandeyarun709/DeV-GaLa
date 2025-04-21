import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './medicine.reducer';

export const MedicineDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const medicineEntity = useAppSelector(state => state.medicine.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="medicineDetailsHeading">
          <Translate contentKey="hospitalBeApp.medicine.detail.title">Medicine</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hospitalBeApp.medicine.name">Name</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.name}</dd>
          <dt>
            <span id="genericMolecule">
              <Translate contentKey="hospitalBeApp.medicine.genericMolecule">Generic Molecule</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.genericMolecule}</dd>
          <dt>
            <span id="isPrescriptionNeeded">
              <Translate contentKey="hospitalBeApp.medicine.isPrescriptionNeeded">Is Prescription Needed</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.isPrescriptionNeeded ? 'true' : 'false'}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hospitalBeApp.medicine.description">Description</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.description}</dd>
          <dt>
            <span id="mrp">
              <Translate contentKey="hospitalBeApp.medicine.mrp">Mrp</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.mrp}</dd>
          <dt>
            <span id="isInsuranceCovered">
              <Translate contentKey="hospitalBeApp.medicine.isInsuranceCovered">Is Insurance Covered</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.isInsuranceCovered ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hospitalBeApp.medicine.type">Type</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.type}</dd>
          <dt>
            <span id="isConsumable">
              <Translate contentKey="hospitalBeApp.medicine.isConsumable">Is Consumable</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.isConsumable ? 'true' : 'false'}</dd>
          <dt>
            <span id="isReturnable">
              <Translate contentKey="hospitalBeApp.medicine.isReturnable">Is Returnable</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.isReturnable ? 'true' : 'false'}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="hospitalBeApp.medicine.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="hospitalBeApp.medicine.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="hospitalBeApp.medicine.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.createdOn ? <TextFormat value={medicineEntity.createdOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="hospitalBeApp.medicine.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="hospitalBeApp.medicine.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{medicineEntity.updatedOn ? <TextFormat value={medicineEntity.updatedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/medicine" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medicine/${medicineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MedicineDetail;

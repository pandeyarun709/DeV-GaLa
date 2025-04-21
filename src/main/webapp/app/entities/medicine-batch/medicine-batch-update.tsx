import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getMedicines } from 'app/entities/medicine/medicine.reducer';
import { getEntities as getHospitals } from 'app/entities/hospital/hospital.reducer';
import { getEntities as getAdmissions } from 'app/entities/admission/admission.reducer';
import { createEntity, getEntity, updateEntity } from './medicine-batch.reducer';

export const MedicineBatchUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medicines = useAppSelector(state => state.medicine.entities);
  const hospitals = useAppSelector(state => state.hospital.entities);
  const admissions = useAppSelector(state => state.admission.entities);
  const medicineBatchEntity = useAppSelector(state => state.medicineBatch.entity);
  const loading = useAppSelector(state => state.medicineBatch.loading);
  const updating = useAppSelector(state => state.medicineBatch.updating);
  const updateSuccess = useAppSelector(state => state.medicineBatch.updateSuccess);

  const handleClose = () => {
    navigate('/medicine-batch');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getMedicines({}));
    dispatch(getHospitals({}));
    dispatch(getAdmissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.quantity !== undefined && typeof values.quantity !== 'number') {
      values.quantity = Number(values.quantity);
    }
    if (values.sellingPrice !== undefined && typeof values.sellingPrice !== 'number') {
      values.sellingPrice = Number(values.sellingPrice);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...medicineBatchEntity,
      ...values,
      med: medicines.find(it => it.id.toString() === values.med?.toString()),
      hospital: hospitals.find(it => it.id.toString() === values.hospital?.toString()),
      admissions: admissions.find(it => it.id.toString() === values.admissions?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdOn: displayDefaultDateTime(),
          updatedOn: displayDefaultDateTime(),
        }
      : {
          ...medicineBatchEntity,
          createdOn: convertDateTimeFromServer(medicineBatchEntity.createdOn),
          updatedOn: convertDateTimeFromServer(medicineBatchEntity.updatedOn),
          med: medicineBatchEntity?.med?.id,
          hospital: medicineBatchEntity?.hospital?.id,
          admissions: medicineBatchEntity?.admissions?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.medicineBatch.home.createOrEditLabel" data-cy="MedicineBatchCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.medicineBatch.home.createOrEditLabel">Create or edit a MedicineBatch</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="medicine-batch-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.batchNo')}
                id="medicine-batch-batchNo"
                name="batchNo"
                data-cy="batchNo"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.expiryDate')}
                id="medicine-batch-expiryDate"
                name="expiryDate"
                data-cy="expiryDate"
                type="date"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.quantity')}
                id="medicine-batch-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.sellingPrice')}
                id="medicine-batch-sellingPrice"
                name="sellingPrice"
                data-cy="sellingPrice"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.storageLocation')}
                id="medicine-batch-storageLocation"
                name="storageLocation"
                data-cy="storageLocation"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.rackNo')}
                id="medicine-batch-rackNo"
                name="rackNo"
                data-cy="rackNo"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.isActive')}
                id="medicine-batch-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.createdBy')}
                id="medicine-batch-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.createdOn')}
                id="medicine-batch-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.updatedBy')}
                id="medicine-batch-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineBatch.updatedOn')}
                id="medicine-batch-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="medicine-batch-med"
                name="med"
                data-cy="med"
                label={translate('hospitalBeApp.medicineBatch.med')}
                type="select"
              >
                <option value="" key="0" />
                {medicines
                  ? medicines.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="medicine-batch-hospital"
                name="hospital"
                data-cy="hospital"
                label={translate('hospitalBeApp.medicineBatch.hospital')}
                type="select"
              >
                <option value="" key="0" />
                {hospitals
                  ? hospitals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="medicine-batch-admissions"
                name="admissions"
                data-cy="admissions"
                label={translate('hospitalBeApp.medicineBatch.admissions')}
                type="select"
              >
                <option value="" key="0" />
                {admissions
                  ? admissions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/medicine-batch" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MedicineBatchUpdate;

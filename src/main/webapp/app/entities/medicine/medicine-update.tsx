import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { MedicineType } from 'app/shared/model/enumerations/medicine-type.model';
import { createEntity, getEntity, updateEntity } from './medicine.reducer';

export const MedicineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medicineEntity = useAppSelector(state => state.medicine.entity);
  const loading = useAppSelector(state => state.medicine.loading);
  const updating = useAppSelector(state => state.medicine.updating);
  const updateSuccess = useAppSelector(state => state.medicine.updateSuccess);
  const medicineTypeValues = Object.keys(MedicineType);

  const handleClose = () => {
    navigate('/medicine');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
    if (values.mrp !== undefined && typeof values.mrp !== 'number') {
      values.mrp = Number(values.mrp);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...medicineEntity,
      ...values,
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
          type: 'Tablet',
          ...medicineEntity,
          createdOn: convertDateTimeFromServer(medicineEntity.createdOn),
          updatedOn: convertDateTimeFromServer(medicineEntity.updatedOn),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.medicine.home.createOrEditLabel" data-cy="MedicineCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.medicine.home.createOrEditLabel">Create or edit a Medicine</Translate>
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
                  id="medicine-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hospitalBeApp.medicine.name')} id="medicine-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.genericMolecule')}
                id="medicine-genericMolecule"
                name="genericMolecule"
                data-cy="genericMolecule"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.isPrescriptionNeeded')}
                id="medicine-isPrescriptionNeeded"
                name="isPrescriptionNeeded"
                data-cy="isPrescriptionNeeded"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.description')}
                id="medicine-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('hospitalBeApp.medicine.mrp')} id="medicine-mrp" name="mrp" data-cy="mrp" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.isInsuranceCovered')}
                id="medicine-isInsuranceCovered"
                name="isInsuranceCovered"
                data-cy="isInsuranceCovered"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('hospitalBeApp.medicine.type')} id="medicine-type" name="type" data-cy="type" type="select">
                {medicineTypeValues.map(medicineType => (
                  <option value={medicineType} key={medicineType}>
                    {translate(`hospitalBeApp.MedicineType.${medicineType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.medicine.isConsumable')}
                id="medicine-isConsumable"
                name="isConsumable"
                data-cy="isConsumable"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.isReturnable')}
                id="medicine-isReturnable"
                name="isReturnable"
                data-cy="isReturnable"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.isActive')}
                id="medicine-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.createdBy')}
                id="medicine-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.createdOn')}
                id="medicine-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.updatedBy')}
                id="medicine-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicine.updatedOn')}
                id="medicine-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/medicine" replace color="info">
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

export default MedicineUpdate;

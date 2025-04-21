import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, updateEntity } from './prescription.reducer';

export const PrescriptionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const prescriptionEntity = useAppSelector(state => state.prescription.entity);
  const loading = useAppSelector(state => state.prescription.loading);
  const updating = useAppSelector(state => state.prescription.updating);
  const updateSuccess = useAppSelector(state => state.prescription.updateSuccess);

  const handleClose = () => {
    navigate('/prescription');
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
    if (values.height !== undefined && typeof values.height !== 'number') {
      values.height = Number(values.height);
    }
    if (values.weight !== undefined && typeof values.weight !== 'number') {
      values.weight = Number(values.weight);
    }
    if (values.bpHigh !== undefined && typeof values.bpHigh !== 'number') {
      values.bpHigh = Number(values.bpHigh);
    }
    if (values.bpLow !== undefined && typeof values.bpLow !== 'number') {
      values.bpLow = Number(values.bpLow);
    }
    if (values.temperature !== undefined && typeof values.temperature !== 'number') {
      values.temperature = Number(values.temperature);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...prescriptionEntity,
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
          ...prescriptionEntity,
          createdOn: convertDateTimeFromServer(prescriptionEntity.createdOn),
          updatedOn: convertDateTimeFromServer(prescriptionEntity.updatedOn),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.prescription.home.createOrEditLabel" data-cy="PrescriptionCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.prescription.home.createOrEditLabel">Create or edit a Prescription</Translate>
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
                  id="prescription-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.prescription.history')}
                id="prescription-history"
                name="history"
                data-cy="history"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.compliant')}
                id="prescription-compliant"
                name="compliant"
                data-cy="compliant"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.height')}
                id="prescription-height"
                name="height"
                data-cy="height"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.weight')}
                id="prescription-weight"
                name="weight"
                data-cy="weight"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.bpHigh')}
                id="prescription-bpHigh"
                name="bpHigh"
                data-cy="bpHigh"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.bpLow')}
                id="prescription-bpLow"
                name="bpLow"
                data-cy="bpLow"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.temperature')}
                id="prescription-temperature"
                name="temperature"
                data-cy="temperature"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.otherVital')}
                id="prescription-otherVital"
                name="otherVital"
                data-cy="otherVital"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.description')}
                id="prescription-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.isActive')}
                id="prescription-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.createdBy')}
                id="prescription-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.createdOn')}
                id="prescription-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.updatedBy')}
                id="prescription-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.prescription.updatedOn')}
                id="prescription-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/prescription" replace color="info">
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

export default PrescriptionUpdate;

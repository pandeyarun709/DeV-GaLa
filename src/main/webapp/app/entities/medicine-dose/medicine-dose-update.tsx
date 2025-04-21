import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPrescriptions } from 'app/entities/prescription/prescription.reducer';
import { getEntities as getMedicines } from 'app/entities/medicine/medicine.reducer';
import { createEntity, getEntity, updateEntity } from './medicine-dose.reducer';

export const MedicineDoseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const prescriptions = useAppSelector(state => state.prescription.entities);
  const medicines = useAppSelector(state => state.medicine.entities);
  const medicineDoseEntity = useAppSelector(state => state.medicineDose.entity);
  const loading = useAppSelector(state => state.medicineDose.loading);
  const updating = useAppSelector(state => state.medicineDose.updating);
  const updateSuccess = useAppSelector(state => state.medicineDose.updateSuccess);

  const handleClose = () => {
    navigate('/medicine-dose');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPrescriptions({}));
    dispatch(getMedicines({}));
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
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...medicineDoseEntity,
      ...values,
      prescription: prescriptions.find(it => it.id.toString() === values.prescription?.toString()),
      medicine: medicines.find(it => it.id.toString() === values.medicine?.toString()),
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
          ...medicineDoseEntity,
          createdOn: convertDateTimeFromServer(medicineDoseEntity.createdOn),
          updatedOn: convertDateTimeFromServer(medicineDoseEntity.updatedOn),
          prescription: medicineDoseEntity?.prescription?.id,
          medicine: medicineDoseEntity?.medicine?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.medicineDose.home.createOrEditLabel" data-cy="MedicineDoseCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.medicineDose.home.createOrEditLabel">Create or edit a MedicineDose</Translate>
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
                  id="medicine-dose-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.medicineDose.frequency')}
                id="medicine-dose-frequency"
                name="frequency"
                data-cy="frequency"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineDose.isActive')}
                id="medicine-dose-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineDose.createdBy')}
                id="medicine-dose-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineDose.createdOn')}
                id="medicine-dose-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineDose.updatedBy')}
                id="medicine-dose-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.medicineDose.updatedOn')}
                id="medicine-dose-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="medicine-dose-prescription"
                name="prescription"
                data-cy="prescription"
                label={translate('hospitalBeApp.medicineDose.prescription')}
                type="select"
              >
                <option value="" key="0" />
                {prescriptions
                  ? prescriptions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="medicine-dose-medicine"
                name="medicine"
                data-cy="medicine"
                label={translate('hospitalBeApp.medicineDose.medicine')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/medicine-dose" replace color="info">
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

export default MedicineDoseUpdate;

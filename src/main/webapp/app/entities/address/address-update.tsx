import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, updateEntity } from './address.reducer';

export const AddressUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addressEntity = useAppSelector(state => state.address.entity);
  const loading = useAppSelector(state => state.address.loading);
  const updating = useAppSelector(state => state.address.updating);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);

  const handleClose = () => {
    navigate('/address');
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
    if (values.pincode !== undefined && typeof values.pincode !== 'number') {
      values.pincode = Number(values.pincode);
    }
    if (values.lat !== undefined && typeof values.lat !== 'number') {
      values.lat = Number(values.lat);
    }
    if (values.lon !== undefined && typeof values.lon !== 'number') {
      values.lon = Number(values.lon);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...addressEntity,
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
          ...addressEntity,
          createdOn: convertDateTimeFromServer(addressEntity.createdOn),
          updatedOn: convertDateTimeFromServer(addressEntity.updatedOn),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.address.home.createOrEditLabel" data-cy="AddressCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.address.home.createOrEditLabel">Create or edit a Address</Translate>
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
                  id="address-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.address.line1')}
                id="address-line1"
                name="line1"
                data-cy="line1"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.address.line2')}
                id="address-line2"
                name="line2"
                data-cy="line2"
                type="text"
              />
              <ValidatedField label={translate('hospitalBeApp.address.city')} id="address-city" name="city" data-cy="city" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.address.state')}
                id="address-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.address.pincode')}
                id="address-pincode"
                name="pincode"
                data-cy="pincode"
                type="text"
              />
              <ValidatedField label={translate('hospitalBeApp.address.lat')} id="address-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField label={translate('hospitalBeApp.address.lon')} id="address-lon" name="lon" data-cy="lon" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.address.isActive')}
                id="address-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.address.createdBy')}
                id="address-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.address.createdOn')}
                id="address-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.address.updatedBy')}
                id="address-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.address.updatedOn')}
                id="address-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/address" replace color="info">
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

export default AddressUpdate;

import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { createEntity, getEntity, updateEntity } from './hospital.reducer';

export const HospitalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addresses = useAppSelector(state => state.address.entities);
  const clients = useAppSelector(state => state.client.entities);
  const hospitalEntity = useAppSelector(state => state.hospital.entity);
  const loading = useAppSelector(state => state.hospital.loading);
  const updating = useAppSelector(state => state.hospital.updating);
  const updateSuccess = useAppSelector(state => state.hospital.updateSuccess);

  const handleClose = () => {
    navigate('/hospital');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAddresses({}));
    dispatch(getClients({}));
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
      ...hospitalEntity,
      ...values,
      address: addresses.find(it => it.id.toString() === values.address?.toString()),
      client: clients.find(it => it.id.toString() === values.client?.toString()),
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
          ...hospitalEntity,
          createdOn: convertDateTimeFromServer(hospitalEntity.createdOn),
          updatedOn: convertDateTimeFromServer(hospitalEntity.updatedOn),
          address: hospitalEntity?.address?.id,
          client: hospitalEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.hospital.home.createOrEditLabel" data-cy="HospitalCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.hospital.home.createOrEditLabel">Create or edit a Hospital</Translate>
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
                  id="hospital-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hospitalBeApp.hospital.name')} id="hospital-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.hospital.logoPath')}
                id="hospital-logoPath"
                name="logoPath"
                data-cy="logoPath"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.hospital.isActive')}
                id="hospital-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.hospital.createdBy')}
                id="hospital-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.hospital.createdOn')}
                id="hospital-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.hospital.updatedBy')}
                id="hospital-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.hospital.updatedOn')}
                id="hospital-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="hospital-address"
                name="address"
                data-cy="address"
                label={translate('hospitalBeApp.hospital.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="hospital-client"
                name="client"
                data-cy="client"
                label={translate('hospitalBeApp.hospital.client')}
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hospital" replace color="info">
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

export default HospitalUpdate;

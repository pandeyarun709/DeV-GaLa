import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { Sex } from 'app/shared/model/enumerations/sex.model';
import { BloodGroup } from 'app/shared/model/enumerations/blood-group.model';
import { Relationship } from 'app/shared/model/enumerations/relationship.model';
import { createEntity, getEntity, updateEntity } from './patient.reducer';

export const PatientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addresses = useAppSelector(state => state.address.entities);
  const patientEntity = useAppSelector(state => state.patient.entity);
  const loading = useAppSelector(state => state.patient.loading);
  const updating = useAppSelector(state => state.patient.updating);
  const updateSuccess = useAppSelector(state => state.patient.updateSuccess);
  const sexValues = Object.keys(Sex);
  const bloodGroupValues = Object.keys(BloodGroup);
  const relationshipValues = Object.keys(Relationship);

  const handleClose = () => {
    navigate('/patient');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAddresses({}));
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
    if (values.phone !== undefined && typeof values.phone !== 'number') {
      values.phone = Number(values.phone);
    }
    if (values.emergencyContactPhone !== undefined && typeof values.emergencyContactPhone !== 'number') {
      values.emergencyContactPhone = Number(values.emergencyContactPhone);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...patientEntity,
      ...values,
      address: addresses.find(it => it.id.toString() === values.address?.toString()),
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
          sex: 'Male',
          bloodGroup: 'AP',
          emergencyContactRelationShip: 'Spouse',
          ...patientEntity,
          createdOn: convertDateTimeFromServer(patientEntity.createdOn),
          updatedOn: convertDateTimeFromServer(patientEntity.updatedOn),
          address: patientEntity?.address?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.patient.home.createOrEditLabel" data-cy="PatientCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.patient.home.createOrEditLabel">Create or edit a Patient</Translate>
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
                  id="patient-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.patient.registrationId')}
                id="patient-registrationId"
                name="registrationId"
                data-cy="registrationId"
                type="text"
              />
              <ValidatedField label={translate('hospitalBeApp.patient.name')} id="patient-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.patient.phone')}
                id="patient-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.email')}
                id="patient-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField label={translate('hospitalBeApp.patient.sex')} id="patient-sex" name="sex" data-cy="sex" type="select">
                {sexValues.map(sex => (
                  <option value={sex} key={sex}>
                    {translate(`hospitalBeApp.Sex.${sex}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.patient.dateOfBirth')}
                id="patient-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="date"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.bloodGroup')}
                id="patient-bloodGroup"
                name="bloodGroup"
                data-cy="bloodGroup"
                type="select"
              >
                {bloodGroupValues.map(bloodGroup => (
                  <option value={bloodGroup} key={bloodGroup}>
                    {translate(`hospitalBeApp.BloodGroup.${bloodGroup}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.patient.emergencyContactName')}
                id="patient-emergencyContactName"
                name="emergencyContactName"
                data-cy="emergencyContactName"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.emergencyContactPhone')}
                id="patient-emergencyContactPhone"
                name="emergencyContactPhone"
                data-cy="emergencyContactPhone"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.emergencyContactEmail')}
                id="patient-emergencyContactEmail"
                name="emergencyContactEmail"
                data-cy="emergencyContactEmail"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.emergencyContactRelationShip')}
                id="patient-emergencyContactRelationShip"
                name="emergencyContactRelationShip"
                data-cy="emergencyContactRelationShip"
                type="select"
              >
                {relationshipValues.map(relationship => (
                  <option value={relationship} key={relationship}>
                    {translate(`hospitalBeApp.Relationship.${relationship}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hospitalBeApp.patient.isActive')}
                id="patient-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.createdBy')}
                id="patient-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.createdOn')}
                id="patient-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.updatedBy')}
                id="patient-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.patient.updatedOn')}
                id="patient-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="patient-address"
                name="address"
                data-cy="address"
                label={translate('hospitalBeApp.patient.address')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/patient" replace color="info">
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

export default PatientUpdate;

import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntities as getHospitals } from 'app/entities/hospital/hospital.reducer';
import { getEntities as getReferralDoctors } from 'app/entities/referral-doctor/referral-doctor.reducer';
import { createEntity, getEntity, updateEntity } from './patient-registration-details.reducer';

export const PatientRegistrationDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const hospitals = useAppSelector(state => state.hospital.entities);
  const referralDoctors = useAppSelector(state => state.referralDoctor.entities);
  const patientRegistrationDetailsEntity = useAppSelector(state => state.patientRegistrationDetails.entity);
  const loading = useAppSelector(state => state.patientRegistrationDetails.loading);
  const updating = useAppSelector(state => state.patientRegistrationDetails.updating);
  const updateSuccess = useAppSelector(state => state.patientRegistrationDetails.updateSuccess);

  const handleClose = () => {
    navigate('/patient-registration-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getClients({}));
    dispatch(getHospitals({}));
    dispatch(getReferralDoctors({}));
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

    const entity = {
      ...patientRegistrationDetailsEntity,
      ...values,
      client: clients.find(it => it.id.toString() === values.client?.toString()),
      hospital: hospitals.find(it => it.id.toString() === values.hospital?.toString()),
      referredBy: referralDoctors.find(it => it.id.toString() === values.referredBy?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...patientRegistrationDetailsEntity,
          client: patientRegistrationDetailsEntity?.client?.id,
          hospital: patientRegistrationDetailsEntity?.hospital?.id,
          referredBy: patientRegistrationDetailsEntity?.referredBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.patientRegistrationDetails.home.createOrEditLabel" data-cy="PatientRegistrationDetailsCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.patientRegistrationDetails.home.createOrEditLabel">
              Create or edit a PatientRegistrationDetails
            </Translate>
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
                  id="patient-registration-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.patientRegistrationDetails.registrationId')}
                id="patient-registration-details-registrationId"
                name="registrationId"
                data-cy="registrationId"
                type="text"
              />
              <ValidatedField
                id="patient-registration-details-client"
                name="client"
                data-cy="client"
                label={translate('hospitalBeApp.patientRegistrationDetails.client')}
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
              <ValidatedField
                id="patient-registration-details-hospital"
                name="hospital"
                data-cy="hospital"
                label={translate('hospitalBeApp.patientRegistrationDetails.hospital')}
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
                id="patient-registration-details-referredBy"
                name="referredBy"
                data-cy="referredBy"
                label={translate('hospitalBeApp.patientRegistrationDetails.referredBy')}
                type="select"
              >
                <option value="" key="0" />
                {referralDoctors
                  ? referralDoctors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/patient-registration-details"
                replace
                color="info"
              >
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

export default PatientRegistrationDetailsUpdate;

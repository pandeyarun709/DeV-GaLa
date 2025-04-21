import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, updateEntity } from './referral-doctor.reducer';

export const ReferralDoctorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const referralDoctorEntity = useAppSelector(state => state.referralDoctor.entity);
  const loading = useAppSelector(state => state.referralDoctor.loading);
  const updating = useAppSelector(state => state.referralDoctor.updating);
  const updateSuccess = useAppSelector(state => state.referralDoctor.updateSuccess);

  const handleClose = () => {
    navigate('/referral-doctor');
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
    if (values.phone !== undefined && typeof values.phone !== 'number') {
      values.phone = Number(values.phone);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...referralDoctorEntity,
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
          ...referralDoctorEntity,
          createdOn: convertDateTimeFromServer(referralDoctorEntity.createdOn),
          updatedOn: convertDateTimeFromServer(referralDoctorEntity.updatedOn),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.referralDoctor.home.createOrEditLabel" data-cy="ReferralDoctorCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.referralDoctor.home.createOrEditLabel">Create or edit a ReferralDoctor</Translate>
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
                  id="referral-doctor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.name')}
                id="referral-doctor-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.phone')}
                id="referral-doctor-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.email')}
                id="referral-doctor-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.registrationNo')}
                id="referral-doctor-registrationNo"
                name="registrationNo"
                data-cy="registrationNo"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.isActive')}
                id="referral-doctor-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.createdBy')}
                id="referral-doctor-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.createdOn')}
                id="referral-doctor-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.updatedBy')}
                id="referral-doctor-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.referralDoctor.updatedOn')}
                id="referral-doctor-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/referral-doctor" replace color="info">
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

export default ReferralDoctorUpdate;

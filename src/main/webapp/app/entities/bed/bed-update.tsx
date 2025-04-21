import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getHospitals } from 'app/entities/hospital/hospital.reducer';
import { getEntities as getAdmissions } from 'app/entities/admission/admission.reducer';
import { BedType } from 'app/shared/model/enumerations/bed-type.model';
import { createEntity, getEntity, updateEntity } from './bed.reducer';

export const BedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hospitals = useAppSelector(state => state.hospital.entities);
  const admissions = useAppSelector(state => state.admission.entities);
  const bedEntity = useAppSelector(state => state.bed.entity);
  const loading = useAppSelector(state => state.bed.loading);
  const updating = useAppSelector(state => state.bed.updating);
  const updateSuccess = useAppSelector(state => state.bed.updateSuccess);
  const bedTypeValues = Object.keys(BedType);

  const handleClose = () => {
    navigate('/bed');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    if (values.floor !== undefined && typeof values.floor !== 'number') {
      values.floor = Number(values.floor);
    }
    if (values.roomNo !== undefined && typeof values.roomNo !== 'number') {
      values.roomNo = Number(values.roomNo);
    }
    if (values.dailyRate !== undefined && typeof values.dailyRate !== 'number') {
      values.dailyRate = Number(values.dailyRate);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...bedEntity,
      ...values,
      hospital: hospitals.find(it => it.id.toString() === values.hospital?.toString()),
      admissions: mapIdList(values.admissions),
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
          type: 'ICU',
          ...bedEntity,
          createdOn: convertDateTimeFromServer(bedEntity.createdOn),
          updatedOn: convertDateTimeFromServer(bedEntity.updatedOn),
          hospital: bedEntity?.hospital?.id,
          admissions: bedEntity?.admissions?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.bed.home.createOrEditLabel" data-cy="BedCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.bed.home.createOrEditLabel">Create or edit a Bed</Translate>
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
                  id="bed-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hospitalBeApp.bed.type')} id="bed-type" name="type" data-cy="type" type="select">
                {bedTypeValues.map(bedType => (
                  <option value={bedType} key={bedType}>
                    {translate(`hospitalBeApp.BedType.${bedType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('hospitalBeApp.bed.floor')} id="bed-floor" name="floor" data-cy="floor" type="text" />
              <ValidatedField label={translate('hospitalBeApp.bed.roomNo')} id="bed-roomNo" name="roomNo" data-cy="roomNo" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.bed.dailyRate')}
                id="bed-dailyRate"
                name="dailyRate"
                data-cy="dailyRate"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.bed.isInsuranceCovered')}
                id="bed-isInsuranceCovered"
                name="isInsuranceCovered"
                data-cy="isInsuranceCovered"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.bed.isActive')}
                id="bed-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.bed.createdBy')}
                id="bed-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.bed.createdOn')}
                id="bed-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.bed.updatedBy')}
                id="bed-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.bed.updatedOn')}
                id="bed-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="bed-hospital"
                name="hospital"
                data-cy="hospital"
                label={translate('hospitalBeApp.bed.hospital')}
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
                label={translate('hospitalBeApp.bed.admissions')}
                id="bed-admissions"
                data-cy="admissions"
                type="select"
                multiple
                name="admissions"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bed" replace color="info">
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

export default BedUpdate;

import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAdmissions } from 'app/entities/admission/admission.reducer';
import { createEntity, getEntity, updateEntity } from './ledger-item.reducer';

export const LedgerItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const admissions = useAppSelector(state => state.admission.entities);
  const ledgerItemEntity = useAppSelector(state => state.ledgerItem.entity);
  const loading = useAppSelector(state => state.ledgerItem.loading);
  const updating = useAppSelector(state => state.ledgerItem.updating);
  const updateSuccess = useAppSelector(state => state.ledgerItem.updateSuccess);

  const handleClose = () => {
    navigate('/ledger-item');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    values.time = convertDateTimeToServer(values.time);
    if (values.fee !== undefined && typeof values.fee !== 'number') {
      values.fee = Number(values.fee);
    }
    values.createdOn = convertDateTimeToServer(values.createdOn);
    values.updatedOn = convertDateTimeToServer(values.updatedOn);

    const entity = {
      ...ledgerItemEntity,
      ...values,
      admission: admissions.find(it => it.id.toString() === values.admission?.toString()),
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
          time: displayDefaultDateTime(),
          createdOn: displayDefaultDateTime(),
          updatedOn: displayDefaultDateTime(),
        }
      : {
          ...ledgerItemEntity,
          time: convertDateTimeFromServer(ledgerItemEntity.time),
          createdOn: convertDateTimeFromServer(ledgerItemEntity.createdOn),
          updatedOn: convertDateTimeFromServer(ledgerItemEntity.updatedOn),
          admission: ledgerItemEntity?.admission?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hospitalBeApp.ledgerItem.home.createOrEditLabel" data-cy="LedgerItemCreateUpdateHeading">
            <Translate contentKey="hospitalBeApp.ledgerItem.home.createOrEditLabel">Create or edit a LedgerItem</Translate>
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
                  id="ledger-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.time')}
                id="ledger-item-time"
                name="time"
                data-cy="time"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.description')}
                id="ledger-item-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('hospitalBeApp.ledgerItem.fee')} id="ledger-item-fee" name="fee" data-cy="fee" type="text" />
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.isCoveredByInsurance')}
                id="ledger-item-isCoveredByInsurance"
                name="isCoveredByInsurance"
                data-cy="isCoveredByInsurance"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.isActive')}
                id="ledger-item-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.createdBy')}
                id="ledger-item-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.createdOn')}
                id="ledger-item-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.updatedBy')}
                id="ledger-item-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('hospitalBeApp.ledgerItem.updatedOn')}
                id="ledger-item-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="ledger-item-admission"
                name="admission"
                data-cy="admission"
                label={translate('hospitalBeApp.ledgerItem.admission')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ledger-item" replace color="info">
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

export default LedgerItemUpdate;

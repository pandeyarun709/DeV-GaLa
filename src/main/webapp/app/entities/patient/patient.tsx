import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './patient.reducer';

export const Patient = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const patientList = useAppSelector(state => state.patient.entities);
  const loading = useAppSelector(state => state.patient.loading);
  const links = useAppSelector(state => state.patient.links);
  const updateSuccess = useAppSelector(state => state.patient.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="patient-heading" data-cy="PatientHeading">
        <Translate contentKey="hospitalBeApp.patient.home.title">Patients</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.patient.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/patient/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.patient.home.createLabel">Create new Patient</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={patientList ? patientList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {patientList && patientList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.patient.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('registrationId')}>
                    <Translate contentKey="hospitalBeApp.patient.registrationId">Registration Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('registrationId')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="hospitalBeApp.patient.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('phone')}>
                    <Translate contentKey="hospitalBeApp.patient.phone">Phone</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('phone')} />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    <Translate contentKey="hospitalBeApp.patient.email">Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                  </th>
                  <th className="hand" onClick={sort('sex')}>
                    <Translate contentKey="hospitalBeApp.patient.sex">Sex</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('sex')} />
                  </th>
                  <th className="hand" onClick={sort('dateOfBirth')}>
                    <Translate contentKey="hospitalBeApp.patient.dateOfBirth">Date Of Birth</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('dateOfBirth')} />
                  </th>
                  <th className="hand" onClick={sort('bloodGroup')}>
                    <Translate contentKey="hospitalBeApp.patient.bloodGroup">Blood Group</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bloodGroup')} />
                  </th>
                  <th className="hand" onClick={sort('emergencyContactName')}>
                    <Translate contentKey="hospitalBeApp.patient.emergencyContactName">Emergency Contact Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('emergencyContactName')} />
                  </th>
                  <th className="hand" onClick={sort('emergencyContactPhone')}>
                    <Translate contentKey="hospitalBeApp.patient.emergencyContactPhone">Emergency Contact Phone</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('emergencyContactPhone')} />
                  </th>
                  <th className="hand" onClick={sort('emergencyContactEmail')}>
                    <Translate contentKey="hospitalBeApp.patient.emergencyContactEmail">Emergency Contact Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('emergencyContactEmail')} />
                  </th>
                  <th className="hand" onClick={sort('emergencyContactRelationShip')}>
                    <Translate contentKey="hospitalBeApp.patient.emergencyContactRelationShip">Emergency Contact Relation Ship</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('emergencyContactRelationShip')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.patient.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.patient.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.patient.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.patient.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.patient.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.patient.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {patientList.map((patient, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/patient/${patient.id}`} color="link" size="sm">
                        {patient.id}
                      </Button>
                    </td>
                    <td>{patient.registrationId}</td>
                    <td>{patient.name}</td>
                    <td>{patient.phone}</td>
                    <td>{patient.email}</td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.Sex.${patient.sex}`} />
                    </td>
                    <td>
                      {patient.dateOfBirth ? <TextFormat type="date" value={patient.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.BloodGroup.${patient.bloodGroup}`} />
                    </td>
                    <td>{patient.emergencyContactName}</td>
                    <td>{patient.emergencyContactPhone}</td>
                    <td>{patient.emergencyContactEmail}</td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.Relationship.${patient.emergencyContactRelationShip}`} />
                    </td>
                    <td>{patient.isActive ? 'true' : 'false'}</td>
                    <td>{patient.createdBy}</td>
                    <td>{patient.createdOn ? <TextFormat type="date" value={patient.createdOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{patient.updatedBy}</td>
                    <td>{patient.updatedOn ? <TextFormat type="date" value={patient.updatedOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{patient.address ? <Link to={`/address/${patient.address.id}`}>{patient.address.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/patient/${patient.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/patient/${patient.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/patient/${patient.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="hospitalBeApp.patient.home.notFound">No Patients found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Patient;

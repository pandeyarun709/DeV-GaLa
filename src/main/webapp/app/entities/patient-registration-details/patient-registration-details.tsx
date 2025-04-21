import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './patient-registration-details.reducer';

export const PatientRegistrationDetails = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const patientRegistrationDetailsList = useAppSelector(state => state.patientRegistrationDetails.entities);
  const loading = useAppSelector(state => state.patientRegistrationDetails.loading);
  const links = useAppSelector(state => state.patientRegistrationDetails.links);
  const updateSuccess = useAppSelector(state => state.patientRegistrationDetails.updateSuccess);

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
      <h2 id="patient-registration-details-heading" data-cy="PatientRegistrationDetailsHeading">
        <Translate contentKey="hospitalBeApp.patientRegistrationDetails.home.title">Patient Registration Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.patientRegistrationDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/patient-registration-details/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.patientRegistrationDetails.home.createLabel">
              Create new Patient Registration Details
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={patientRegistrationDetailsList ? patientRegistrationDetailsList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {patientRegistrationDetailsList && patientRegistrationDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.patientRegistrationDetails.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('registrationId')}>
                    <Translate contentKey="hospitalBeApp.patientRegistrationDetails.registrationId">Registration Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('registrationId')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.patientRegistrationDetails.client">Client</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.patientRegistrationDetails.hospital">Hospital</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.patientRegistrationDetails.referredBy">Referred By</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {patientRegistrationDetailsList.map((patientRegistrationDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/patient-registration-details/${patientRegistrationDetails.id}`} color="link" size="sm">
                        {patientRegistrationDetails.id}
                      </Button>
                    </td>
                    <td>{patientRegistrationDetails.registrationId}</td>
                    <td>
                      {patientRegistrationDetails.client ? (
                        <Link to={`/client/${patientRegistrationDetails.client.id}`}>{patientRegistrationDetails.client.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {patientRegistrationDetails.hospital ? (
                        <Link to={`/hospital/${patientRegistrationDetails.hospital.id}`}>{patientRegistrationDetails.hospital.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {patientRegistrationDetails.referredBy ? (
                        <Link to={`/referral-doctor/${patientRegistrationDetails.referredBy.id}`}>
                          {patientRegistrationDetails.referredBy.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/patient-registration-details/${patientRegistrationDetails.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/patient-registration-details/${patientRegistrationDetails.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/patient-registration-details/${patientRegistrationDetails.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.patientRegistrationDetails.home.notFound">
                  No Patient Registration Details found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PatientRegistrationDetails;

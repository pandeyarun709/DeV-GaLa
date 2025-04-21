import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './admission.reducer';

export const Admission = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const admissionList = useAppSelector(state => state.admission.entities);
  const loading = useAppSelector(state => state.admission.loading);
  const links = useAppSelector(state => state.admission.links);
  const updateSuccess = useAppSelector(state => state.admission.updateSuccess);

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
      <h2 id="admission-heading" data-cy="AdmissionHeading">
        <Translate contentKey="hospitalBeApp.admission.home.title">Admissions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.admission.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/admission/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.admission.home.createLabel">Create new Admission</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={admissionList ? admissionList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {admissionList && admissionList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.admission.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('details')}>
                    <Translate contentKey="hospitalBeApp.admission.details">Details</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('details')} />
                  </th>
                  <th className="hand" onClick={sort('admissionStatus')}>
                    <Translate contentKey="hospitalBeApp.admission.admissionStatus">Admission Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('admissionStatus')} />
                  </th>
                  <th className="hand" onClick={sort('dischargeStatus')}>
                    <Translate contentKey="hospitalBeApp.admission.dischargeStatus">Discharge Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('dischargeStatus')} />
                  </th>
                  <th className="hand" onClick={sort('admissionTime')}>
                    <Translate contentKey="hospitalBeApp.admission.admissionTime">Admission Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('admissionTime')} />
                  </th>
                  <th className="hand" onClick={sort('dischargeTime')}>
                    <Translate contentKey="hospitalBeApp.admission.dischargeTime">Discharge Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('dischargeTime')} />
                  </th>
                  <th className="hand" onClick={sort('paymentStatus')}>
                    <Translate contentKey="hospitalBeApp.admission.paymentStatus">Payment Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentStatus')} />
                  </th>
                  <th className="hand" onClick={sort('totalBillAmount')}>
                    <Translate contentKey="hospitalBeApp.admission.totalBillAmount">Total Bill Amount</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalBillAmount')} />
                  </th>
                  <th className="hand" onClick={sort('insuranceCoveredAmount')}>
                    <Translate contentKey="hospitalBeApp.admission.insuranceCoveredAmount">Insurance Covered Amount</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('insuranceCoveredAmount')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.admission.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.admission.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.admission.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.admission.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.admission.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.admission.patient">Patient</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.admission.hospital">Hospital</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.admission.admittedUnder">Admitted Under</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {admissionList.map((admission, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/admission/${admission.id}`} color="link" size="sm">
                        {admission.id}
                      </Button>
                    </td>
                    <td>{admission.details}</td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.AdmissionStatus.${admission.admissionStatus}`} />
                    </td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.DischargeStatus.${admission.dischargeStatus}`} />
                    </td>
                    <td>
                      {admission.admissionTime ? <TextFormat type="date" value={admission.admissionTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {admission.dischargeTime ? <TextFormat type="date" value={admission.dischargeTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.PaymentStatus.${admission.paymentStatus}`} />
                    </td>
                    <td>{admission.totalBillAmount}</td>
                    <td>{admission.insuranceCoveredAmount}</td>
                    <td>{admission.isActive ? 'true' : 'false'}</td>
                    <td>{admission.createdBy}</td>
                    <td>{admission.createdOn ? <TextFormat type="date" value={admission.createdOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{admission.updatedBy}</td>
                    <td>{admission.updatedOn ? <TextFormat type="date" value={admission.updatedOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{admission.patient ? <Link to={`/patient/${admission.patient.id}`}>{admission.patient.id}</Link> : ''}</td>
                    <td>{admission.hospital ? <Link to={`/hospital/${admission.hospital.id}`}>{admission.hospital.id}</Link> : ''}</td>
                    <td>
                      {admission.admittedUnder ? (
                        <Link to={`/employee/${admission.admittedUnder.id}`}>{admission.admittedUnder.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/admission/${admission.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/admission/${admission.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/admission/${admission.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.admission.home.notFound">No Admissions found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Admission;

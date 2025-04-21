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

import { getEntities, reset } from './doctor-visit.reducer';

export const DoctorVisit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const doctorVisitList = useAppSelector(state => state.doctorVisit.entities);
  const loading = useAppSelector(state => state.doctorVisit.loading);
  const links = useAppSelector(state => state.doctorVisit.links);
  const updateSuccess = useAppSelector(state => state.doctorVisit.updateSuccess);

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
      <h2 id="doctor-visit-heading" data-cy="DoctorVisitHeading">
        <Translate contentKey="hospitalBeApp.doctorVisit.home.title">Doctor Visits</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.doctorVisit.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/doctor-visit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.doctorVisit.home.createLabel">Create new Doctor Visit</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={doctorVisitList ? doctorVisitList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {doctorVisitList && doctorVisitList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.status">Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                  </th>
                  <th className="hand" onClick={sort('visitDate')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.visitDate">Visit Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('visitDate')} />
                  </th>
                  <th className="hand" onClick={sort('visitTime')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.visitTime">Visit Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('visitTime')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.doctorVisit.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.doctorVisit.prescription">Prescription</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.doctorVisit.doctorVisitType">Doctor Visit Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.doctorVisit.patient">Patient</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.doctorVisit.admissions">Admissions</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {doctorVisitList.map((doctorVisit, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/doctor-visit/${doctorVisit.id}`} color="link" size="sm">
                        {doctorVisit.id}
                      </Button>
                    </td>
                    <td>{doctorVisit.description}</td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.VisitStatus.${doctorVisit.status}`} />
                    </td>
                    <td>
                      {doctorVisit.visitDate ? (
                        <TextFormat type="date" value={doctorVisit.visitDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {doctorVisit.visitTime ? <TextFormat type="date" value={doctorVisit.visitTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{doctorVisit.isActive ? 'true' : 'false'}</td>
                    <td>{doctorVisit.createdBy}</td>
                    <td>
                      {doctorVisit.createdOn ? <TextFormat type="date" value={doctorVisit.createdOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{doctorVisit.updatedBy}</td>
                    <td>
                      {doctorVisit.updatedOn ? <TextFormat type="date" value={doctorVisit.updatedOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {doctorVisit.prescription ? (
                        <Link to={`/prescription/${doctorVisit.prescription.id}`}>{doctorVisit.prescription.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {doctorVisit.doctorVisitType ? (
                        <Link to={`/doctor-visit-type/${doctorVisit.doctorVisitType.id}`}>{doctorVisit.doctorVisitType.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{doctorVisit.patient ? <Link to={`/patient/${doctorVisit.patient.id}`}>{doctorVisit.patient.id}</Link> : ''}</td>
                    <td>
                      {doctorVisit.admissions ? (
                        <Link to={`/admission/${doctorVisit.admissions.id}`}>{doctorVisit.admissions.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/doctor-visit/${doctorVisit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/doctor-visit/${doctorVisit.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/doctor-visit/${doctorVisit.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.doctorVisit.home.notFound">No Doctor Visits found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default DoctorVisit;

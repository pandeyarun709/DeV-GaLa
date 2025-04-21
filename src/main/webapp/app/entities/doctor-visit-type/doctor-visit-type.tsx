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

import { getEntities, reset } from './doctor-visit-type.reducer';

export const DoctorVisitType = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const doctorVisitTypeList = useAppSelector(state => state.doctorVisitType.entities);
  const loading = useAppSelector(state => state.doctorVisitType.loading);
  const links = useAppSelector(state => state.doctorVisitType.links);
  const updateSuccess = useAppSelector(state => state.doctorVisitType.updateSuccess);

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
      <h2 id="doctor-visit-type-heading" data-cy="DoctorVisitTypeHeading">
        <Translate contentKey="hospitalBeApp.doctorVisitType.home.title">Doctor Visit Types</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.doctorVisitType.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/doctor-visit-type/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.doctorVisitType.home.createLabel">Create new Doctor Visit Type</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={doctorVisitTypeList ? doctorVisitTypeList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {doctorVisitTypeList && doctorVisitTypeList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.type">Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                  </th>
                  <th className="hand" onClick={sort('fee')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.fee">Fee</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('fee')} />
                  </th>
                  <th className="hand" onClick={sort('isInsuranceCovered')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.isInsuranceCovered">Is Insurance Covered</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isInsuranceCovered')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.doctorVisitType.doctor">Doctor</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {doctorVisitTypeList.map((doctorVisitType, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/doctor-visit-type/${doctorVisitType.id}`} color="link" size="sm">
                        {doctorVisitType.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.VisitType.${doctorVisitType.type}`} />
                    </td>
                    <td>{doctorVisitType.fee}</td>
                    <td>{doctorVisitType.isInsuranceCovered ? 'true' : 'false'}</td>
                    <td>{doctorVisitType.isActive ? 'true' : 'false'}</td>
                    <td>{doctorVisitType.createdBy}</td>
                    <td>
                      {doctorVisitType.createdOn ? (
                        <TextFormat type="date" value={doctorVisitType.createdOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{doctorVisitType.updatedBy}</td>
                    <td>
                      {doctorVisitType.updatedOn ? (
                        <TextFormat type="date" value={doctorVisitType.updatedOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {doctorVisitType.doctor ? <Link to={`/employee/${doctorVisitType.doctor.id}`}>{doctorVisitType.doctor.id}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/doctor-visit-type/${doctorVisitType.id}`}
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
                          to={`/doctor-visit-type/${doctorVisitType.id}/edit`}
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
                          onClick={() => (window.location.href = `/doctor-visit-type/${doctorVisitType.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.doctorVisitType.home.notFound">No Doctor Visit Types found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default DoctorVisitType;

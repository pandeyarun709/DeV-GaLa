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

import { getEntities, reset } from './slot.reducer';

export const Slot = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const slotList = useAppSelector(state => state.slot.entities);
  const loading = useAppSelector(state => state.slot.loading);
  const links = useAppSelector(state => state.slot.links);
  const updateSuccess = useAppSelector(state => state.slot.updateSuccess);

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
      <h2 id="slot-heading" data-cy="SlotHeading">
        <Translate contentKey="hospitalBeApp.slot.home.title">Slots</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.slot.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/slot/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.slot.home.createLabel">Create new Slot</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={slotList ? slotList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {slotList && slotList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.slot.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('day')}>
                    <Translate contentKey="hospitalBeApp.slot.day">Day</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('day')} />
                  </th>
                  <th className="hand" onClick={sort('startTimeHour')}>
                    <Translate contentKey="hospitalBeApp.slot.startTimeHour">Start Time Hour</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('startTimeHour')} />
                  </th>
                  <th className="hand" onClick={sort('startTimeMin')}>
                    <Translate contentKey="hospitalBeApp.slot.startTimeMin">Start Time Min</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('startTimeMin')} />
                  </th>
                  <th className="hand" onClick={sort('endTimeHour')}>
                    <Translate contentKey="hospitalBeApp.slot.endTimeHour">End Time Hour</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('endTimeHour')} />
                  </th>
                  <th className="hand" onClick={sort('endTimeMin')}>
                    <Translate contentKey="hospitalBeApp.slot.endTimeMin">End Time Min</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('endTimeMin')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.slot.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.slot.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.slot.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.slot.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.slot.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.slot.doctorVisitType">Doctor Visit Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.slot.test">Test</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {slotList.map((slot, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/slot/${slot.id}`} color="link" size="sm">
                        {slot.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.Day.${slot.day}`} />
                    </td>
                    <td>{slot.startTimeHour}</td>
                    <td>{slot.startTimeMin}</td>
                    <td>{slot.endTimeHour}</td>
                    <td>{slot.endTimeMin}</td>
                    <td>{slot.isActive ? 'true' : 'false'}</td>
                    <td>{slot.createdBy}</td>
                    <td>{slot.createdOn ? <TextFormat type="date" value={slot.createdOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{slot.updatedBy}</td>
                    <td>{slot.updatedOn ? <TextFormat type="date" value={slot.updatedOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>
                      {slot.doctorVisitType ? (
                        <Link to={`/doctor-visit-type/${slot.doctorVisitType.id}`}>{slot.doctorVisitType.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{slot.test ? <Link to={`/diagnostic-test/${slot.test.id}`}>{slot.test.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/slot/${slot.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/slot/${slot.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/slot/${slot.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.slot.home.notFound">No Slots found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Slot;

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

import { getEntities, reset } from './ledger-item.reducer';

export const LedgerItem = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const ledgerItemList = useAppSelector(state => state.ledgerItem.entities);
  const loading = useAppSelector(state => state.ledgerItem.loading);
  const links = useAppSelector(state => state.ledgerItem.links);
  const updateSuccess = useAppSelector(state => state.ledgerItem.updateSuccess);

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
      <h2 id="ledger-item-heading" data-cy="LedgerItemHeading">
        <Translate contentKey="hospitalBeApp.ledgerItem.home.title">Ledger Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.ledgerItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/ledger-item/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.ledgerItem.home.createLabel">Create new Ledger Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={ledgerItemList ? ledgerItemList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {ledgerItemList && ledgerItemList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('time')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.time">Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('time')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('fee')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.fee">Fee</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('fee')} />
                  </th>
                  <th className="hand" onClick={sort('isCoveredByInsurance')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.isCoveredByInsurance">Is Covered By Insurance</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isCoveredByInsurance')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.ledgerItem.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.ledgerItem.admission">Admission</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {ledgerItemList.map((ledgerItem, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/ledger-item/${ledgerItem.id}`} color="link" size="sm">
                        {ledgerItem.id}
                      </Button>
                    </td>
                    <td>{ledgerItem.time ? <TextFormat type="date" value={ledgerItem.time} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{ledgerItem.description}</td>
                    <td>{ledgerItem.fee}</td>
                    <td>{ledgerItem.isCoveredByInsurance ? 'true' : 'false'}</td>
                    <td>{ledgerItem.isActive ? 'true' : 'false'}</td>
                    <td>{ledgerItem.createdBy}</td>
                    <td>
                      {ledgerItem.createdOn ? <TextFormat type="date" value={ledgerItem.createdOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{ledgerItem.updatedBy}</td>
                    <td>
                      {ledgerItem.updatedOn ? <TextFormat type="date" value={ledgerItem.updatedOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {ledgerItem.admission ? <Link to={`/admission/${ledgerItem.admission.id}`}>{ledgerItem.admission.id}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/ledger-item/${ledgerItem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/ledger-item/${ledgerItem.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/ledger-item/${ledgerItem.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.ledgerItem.home.notFound">No Ledger Items found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default LedgerItem;

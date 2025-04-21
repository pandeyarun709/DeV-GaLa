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

import { getEntities, reset } from './medicine-batch.reducer';

export const MedicineBatch = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const medicineBatchList = useAppSelector(state => state.medicineBatch.entities);
  const loading = useAppSelector(state => state.medicineBatch.loading);
  const links = useAppSelector(state => state.medicineBatch.links);
  const updateSuccess = useAppSelector(state => state.medicineBatch.updateSuccess);

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
      <h2 id="medicine-batch-heading" data-cy="MedicineBatchHeading">
        <Translate contentKey="hospitalBeApp.medicineBatch.home.title">Medicine Batches</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.medicineBatch.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/medicine-batch/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.medicineBatch.home.createLabel">Create new Medicine Batch</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={medicineBatchList ? medicineBatchList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {medicineBatchList && medicineBatchList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('batchNo')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.batchNo">Batch No</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('batchNo')} />
                  </th>
                  <th className="hand" onClick={sort('expiryDate')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.expiryDate">Expiry Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('expiryDate')} />
                  </th>
                  <th className="hand" onClick={sort('quantity')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.quantity">Quantity</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('quantity')} />
                  </th>
                  <th className="hand" onClick={sort('sellingPrice')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.sellingPrice">Selling Price</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('sellingPrice')} />
                  </th>
                  <th className="hand" onClick={sort('storageLocation')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.storageLocation">Storage Location</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('storageLocation')} />
                  </th>
                  <th className="hand" onClick={sort('rackNo')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.rackNo">Rack No</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('rackNo')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.medicineBatch.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.medicineBatch.med">Med</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.medicineBatch.hospital">Hospital</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.medicineBatch.admissions">Admissions</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {medicineBatchList.map((medicineBatch, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/medicine-batch/${medicineBatch.id}`} color="link" size="sm">
                        {medicineBatch.id}
                      </Button>
                    </td>
                    <td>{medicineBatch.batchNo}</td>
                    <td>
                      {medicineBatch.expiryDate ? (
                        <TextFormat type="date" value={medicineBatch.expiryDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{medicineBatch.quantity}</td>
                    <td>{medicineBatch.sellingPrice}</td>
                    <td>{medicineBatch.storageLocation}</td>
                    <td>{medicineBatch.rackNo}</td>
                    <td>{medicineBatch.isActive ? 'true' : 'false'}</td>
                    <td>{medicineBatch.createdBy}</td>
                    <td>
                      {medicineBatch.createdOn ? <TextFormat type="date" value={medicineBatch.createdOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{medicineBatch.updatedBy}</td>
                    <td>
                      {medicineBatch.updatedOn ? <TextFormat type="date" value={medicineBatch.updatedOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{medicineBatch.med ? <Link to={`/medicine/${medicineBatch.med.id}`}>{medicineBatch.med.id}</Link> : ''}</td>
                    <td>
                      {medicineBatch.hospital ? <Link to={`/hospital/${medicineBatch.hospital.id}`}>{medicineBatch.hospital.id}</Link> : ''}
                    </td>
                    <td>
                      {medicineBatch.admissions ? (
                        <Link to={`/admission/${medicineBatch.admissions.id}`}>{medicineBatch.admissions.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/medicine-batch/${medicineBatch.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/medicine-batch/${medicineBatch.id}/edit`}
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
                          onClick={() => (window.location.href = `/medicine-batch/${medicineBatch.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.medicineBatch.home.notFound">No Medicine Batches found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default MedicineBatch;

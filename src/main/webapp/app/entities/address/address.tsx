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

import { getEntities, reset } from './address.reducer';

export const Address = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const addressList = useAppSelector(state => state.address.entities);
  const loading = useAppSelector(state => state.address.loading);
  const links = useAppSelector(state => state.address.links);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);

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
      <h2 id="address-heading" data-cy="AddressHeading">
        <Translate contentKey="hospitalBeApp.address.home.title">Addresses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.address.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/address/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.address.home.createLabel">Create new Address</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={addressList ? addressList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {addressList && addressList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.address.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('line1')}>
                    <Translate contentKey="hospitalBeApp.address.line1">Line 1</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('line1')} />
                  </th>
                  <th className="hand" onClick={sort('line2')}>
                    <Translate contentKey="hospitalBeApp.address.line2">Line 2</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('line2')} />
                  </th>
                  <th className="hand" onClick={sort('city')}>
                    <Translate contentKey="hospitalBeApp.address.city">City</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('city')} />
                  </th>
                  <th className="hand" onClick={sort('state')}>
                    <Translate contentKey="hospitalBeApp.address.state">State</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('state')} />
                  </th>
                  <th className="hand" onClick={sort('pincode')}>
                    <Translate contentKey="hospitalBeApp.address.pincode">Pincode</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pincode')} />
                  </th>
                  <th className="hand" onClick={sort('lat')}>
                    <Translate contentKey="hospitalBeApp.address.lat">Lat</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('lat')} />
                  </th>
                  <th className="hand" onClick={sort('lon')}>
                    <Translate contentKey="hospitalBeApp.address.lon">Lon</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('lon')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.address.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.address.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.address.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.address.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.address.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {addressList.map((address, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/address/${address.id}`} color="link" size="sm">
                        {address.id}
                      </Button>
                    </td>
                    <td>{address.line1}</td>
                    <td>{address.line2}</td>
                    <td>{address.city}</td>
                    <td>{address.state}</td>
                    <td>{address.pincode}</td>
                    <td>{address.lat}</td>
                    <td>{address.lon}</td>
                    <td>{address.isActive ? 'true' : 'false'}</td>
                    <td>{address.createdBy}</td>
                    <td>{address.createdOn ? <TextFormat type="date" value={address.createdOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{address.updatedBy}</td>
                    <td>{address.updatedOn ? <TextFormat type="date" value={address.updatedOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/address/${address.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/address/${address.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/address/${address.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.address.home.notFound">No Addresses found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Address;

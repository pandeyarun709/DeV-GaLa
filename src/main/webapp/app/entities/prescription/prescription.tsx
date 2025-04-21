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

import { getEntities, reset } from './prescription.reducer';

export const Prescription = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const prescriptionList = useAppSelector(state => state.prescription.entities);
  const loading = useAppSelector(state => state.prescription.loading);
  const links = useAppSelector(state => state.prescription.links);
  const updateSuccess = useAppSelector(state => state.prescription.updateSuccess);

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
      <h2 id="prescription-heading" data-cy="PrescriptionHeading">
        <Translate contentKey="hospitalBeApp.prescription.home.title">Prescriptions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.prescription.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/prescription/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.prescription.home.createLabel">Create new Prescription</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={prescriptionList ? prescriptionList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {prescriptionList && prescriptionList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.prescription.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('history')}>
                    <Translate contentKey="hospitalBeApp.prescription.history">History</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('history')} />
                  </th>
                  <th className="hand" onClick={sort('compliant')}>
                    <Translate contentKey="hospitalBeApp.prescription.compliant">Compliant</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('compliant')} />
                  </th>
                  <th className="hand" onClick={sort('height')}>
                    <Translate contentKey="hospitalBeApp.prescription.height">Height</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('height')} />
                  </th>
                  <th className="hand" onClick={sort('weight')}>
                    <Translate contentKey="hospitalBeApp.prescription.weight">Weight</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('weight')} />
                  </th>
                  <th className="hand" onClick={sort('bpHigh')}>
                    <Translate contentKey="hospitalBeApp.prescription.bpHigh">Bp High</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bpHigh')} />
                  </th>
                  <th className="hand" onClick={sort('bpLow')}>
                    <Translate contentKey="hospitalBeApp.prescription.bpLow">Bp Low</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bpLow')} />
                  </th>
                  <th className="hand" onClick={sort('temperature')}>
                    <Translate contentKey="hospitalBeApp.prescription.temperature">Temperature</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('temperature')} />
                  </th>
                  <th className="hand" onClick={sort('otherVital')}>
                    <Translate contentKey="hospitalBeApp.prescription.otherVital">Other Vital</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('otherVital')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="hospitalBeApp.prescription.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.prescription.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.prescription.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.prescription.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.prescription.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.prescription.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {prescriptionList.map((prescription, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/prescription/${prescription.id}`} color="link" size="sm">
                        {prescription.id}
                      </Button>
                    </td>
                    <td>{prescription.history}</td>
                    <td>{prescription.compliant}</td>
                    <td>{prescription.height}</td>
                    <td>{prescription.weight}</td>
                    <td>{prescription.bpHigh}</td>
                    <td>{prescription.bpLow}</td>
                    <td>{prescription.temperature}</td>
                    <td>{prescription.otherVital}</td>
                    <td>{prescription.description}</td>
                    <td>{prescription.isActive ? 'true' : 'false'}</td>
                    <td>{prescription.createdBy}</td>
                    <td>
                      {prescription.createdOn ? <TextFormat type="date" value={prescription.createdOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{prescription.updatedBy}</td>
                    <td>
                      {prescription.updatedOn ? <TextFormat type="date" value={prescription.updatedOn} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/prescription/${prescription.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/prescription/${prescription.id}/edit`}
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
                          onClick={() => (window.location.href = `/prescription/${prescription.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.prescription.home.notFound">No Prescriptions found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Prescription;

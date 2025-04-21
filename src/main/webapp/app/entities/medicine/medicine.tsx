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

import { getEntities, reset } from './medicine.reducer';

export const Medicine = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const medicineList = useAppSelector(state => state.medicine.entities);
  const loading = useAppSelector(state => state.medicine.loading);
  const links = useAppSelector(state => state.medicine.links);
  const updateSuccess = useAppSelector(state => state.medicine.updateSuccess);

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
      <h2 id="medicine-heading" data-cy="MedicineHeading">
        <Translate contentKey="hospitalBeApp.medicine.home.title">Medicines</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.medicine.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/medicine/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.medicine.home.createLabel">Create new Medicine</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={medicineList ? medicineList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {medicineList && medicineList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.medicine.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="hospitalBeApp.medicine.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('genericMolecule')}>
                    <Translate contentKey="hospitalBeApp.medicine.genericMolecule">Generic Molecule</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('genericMolecule')} />
                  </th>
                  <th className="hand" onClick={sort('isPrescriptionNeeded')}>
                    <Translate contentKey="hospitalBeApp.medicine.isPrescriptionNeeded">Is Prescription Needed</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isPrescriptionNeeded')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="hospitalBeApp.medicine.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('mrp')}>
                    <Translate contentKey="hospitalBeApp.medicine.mrp">Mrp</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('mrp')} />
                  </th>
                  <th className="hand" onClick={sort('isInsuranceCovered')}>
                    <Translate contentKey="hospitalBeApp.medicine.isInsuranceCovered">Is Insurance Covered</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isInsuranceCovered')} />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="hospitalBeApp.medicine.type">Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                  </th>
                  <th className="hand" onClick={sort('isConsumable')}>
                    <Translate contentKey="hospitalBeApp.medicine.isConsumable">Is Consumable</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isConsumable')} />
                  </th>
                  <th className="hand" onClick={sort('isReturnable')}>
                    <Translate contentKey="hospitalBeApp.medicine.isReturnable">Is Returnable</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isReturnable')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.medicine.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.medicine.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.medicine.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.medicine.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.medicine.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {medicineList.map((medicine, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/medicine/${medicine.id}`} color="link" size="sm">
                        {medicine.id}
                      </Button>
                    </td>
                    <td>{medicine.name}</td>
                    <td>{medicine.genericMolecule}</td>
                    <td>{medicine.isPrescriptionNeeded ? 'true' : 'false'}</td>
                    <td>{medicine.description}</td>
                    <td>{medicine.mrp}</td>
                    <td>{medicine.isInsuranceCovered ? 'true' : 'false'}</td>
                    <td>
                      <Translate contentKey={`hospitalBeApp.MedicineType.${medicine.type}`} />
                    </td>
                    <td>{medicine.isConsumable ? 'true' : 'false'}</td>
                    <td>{medicine.isReturnable ? 'true' : 'false'}</td>
                    <td>{medicine.isActive ? 'true' : 'false'}</td>
                    <td>{medicine.createdBy}</td>
                    <td>{medicine.createdOn ? <TextFormat type="date" value={medicine.createdOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{medicine.updatedBy}</td>
                    <td>{medicine.updatedOn ? <TextFormat type="date" value={medicine.updatedOn} format={APP_DATE_FORMAT} /> : null}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/medicine/${medicine.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/medicine/${medicine.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/medicine/${medicine.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.medicine.home.notFound">No Medicines found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Medicine;

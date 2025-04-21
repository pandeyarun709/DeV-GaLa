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

import { getEntities, reset } from './diagnostic-test.reducer';

export const DiagnosticTest = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const diagnosticTestList = useAppSelector(state => state.diagnosticTest.entities);
  const loading = useAppSelector(state => state.diagnosticTest.loading);
  const links = useAppSelector(state => state.diagnosticTest.links);
  const updateSuccess = useAppSelector(state => state.diagnosticTest.updateSuccess);

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
      <h2 id="diagnostic-test-heading" data-cy="DiagnosticTestHeading">
        <Translate contentKey="hospitalBeApp.diagnosticTest.home.title">Diagnostic Tests</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.diagnosticTest.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/diagnostic-test/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.diagnosticTest.home.createLabel">Create new Diagnostic Test</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={diagnosticTestList ? diagnosticTestList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {diagnosticTestList && diagnosticTestList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('phone')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.phone">Phone</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('phone')} />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.email">Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                  </th>
                  <th className="hand" onClick={sort('fee')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.fee">Fee</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('fee')} />
                  </th>
                  <th className="hand" onClick={sort('isInsuranceCovered')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.isInsuranceCovered">Is Insurance Covered</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isInsuranceCovered')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.department">Department</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.diagnosticTest.prescription">Prescription</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {diagnosticTestList.map((diagnosticTest, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/diagnostic-test/${diagnosticTest.id}`} color="link" size="sm">
                        {diagnosticTest.id}
                      </Button>
                    </td>
                    <td>{diagnosticTest.name}</td>
                    <td>{diagnosticTest.phone}</td>
                    <td>{diagnosticTest.email}</td>
                    <td>{diagnosticTest.fee}</td>
                    <td>{diagnosticTest.isInsuranceCovered ? 'true' : 'false'}</td>
                    <td>{diagnosticTest.isActive ? 'true' : 'false'}</td>
                    <td>{diagnosticTest.createdBy}</td>
                    <td>
                      {diagnosticTest.createdOn ? (
                        <TextFormat type="date" value={diagnosticTest.createdOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{diagnosticTest.updatedBy}</td>
                    <td>
                      {diagnosticTest.updatedOn ? (
                        <TextFormat type="date" value={diagnosticTest.updatedOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {diagnosticTest.department ? (
                        <Link to={`/department/${diagnosticTest.department.id}`}>{diagnosticTest.department.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {diagnosticTest.prescription ? (
                        <Link to={`/prescription/${diagnosticTest.prescription.id}`}>{diagnosticTest.prescription.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/diagnostic-test/${diagnosticTest.id}`}
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
                          to={`/diagnostic-test/${diagnosticTest.id}/edit`}
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
                          onClick={() => (window.location.href = `/diagnostic-test/${diagnosticTest.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.diagnosticTest.home.notFound">No Diagnostic Tests found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default DiagnosticTest;

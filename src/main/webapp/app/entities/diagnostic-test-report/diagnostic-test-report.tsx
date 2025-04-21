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

import { getEntities, reset } from './diagnostic-test-report.reducer';

export const DiagnosticTestReport = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const diagnosticTestReportList = useAppSelector(state => state.diagnosticTestReport.entities);
  const loading = useAppSelector(state => state.diagnosticTestReport.loading);
  const links = useAppSelector(state => state.diagnosticTestReport.links);
  const updateSuccess = useAppSelector(state => state.diagnosticTestReport.updateSuccess);

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
      <h2 id="diagnostic-test-report-heading" data-cy="DiagnosticTestReportHeading">
        <Translate contentKey="hospitalBeApp.diagnosticTestReport.home.title">Diagnostic Test Reports</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hospitalBeApp.diagnosticTestReport.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/diagnostic-test-report/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hospitalBeApp.diagnosticTestReport.home.createLabel">Create new Diagnostic Test Report</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={diagnosticTestReportList ? diagnosticTestReportList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {diagnosticTestReportList && diagnosticTestReportList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('signedBy')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.signedBy">Signed By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('signedBy')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.patient">Patient</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.test">Test</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="hospitalBeApp.diagnosticTestReport.admissions">Admissions</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {diagnosticTestReportList.map((diagnosticTestReport, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/diagnostic-test-report/${diagnosticTestReport.id}`} color="link" size="sm">
                        {diagnosticTestReport.id}
                      </Button>
                    </td>
                    <td>{diagnosticTestReport.description}</td>
                    <td>{diagnosticTestReport.signedBy}</td>
                    <td>{diagnosticTestReport.isActive ? 'true' : 'false'}</td>
                    <td>{diagnosticTestReport.createdBy}</td>
                    <td>
                      {diagnosticTestReport.createdOn ? (
                        <TextFormat type="date" value={diagnosticTestReport.createdOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{diagnosticTestReport.updatedBy}</td>
                    <td>
                      {diagnosticTestReport.updatedOn ? (
                        <TextFormat type="date" value={diagnosticTestReport.updatedOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {diagnosticTestReport.patient ? (
                        <Link to={`/patient/${diagnosticTestReport.patient.id}`}>{diagnosticTestReport.patient.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {diagnosticTestReport.test ? (
                        <Link to={`/diagnostic-test/${diagnosticTestReport.test.id}`}>{diagnosticTestReport.test.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {diagnosticTestReport.admissions ? (
                        <Link to={`/admission/${diagnosticTestReport.admissions.id}`}>{diagnosticTestReport.admissions.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/diagnostic-test-report/${diagnosticTestReport.id}`}
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
                          to={`/diagnostic-test-report/${diagnosticTestReport.id}/edit`}
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
                          onClick={() => (window.location.href = `/diagnostic-test-report/${diagnosticTestReport.id}/delete`)}
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
                <Translate contentKey="hospitalBeApp.diagnosticTestReport.home.notFound">No Diagnostic Test Reports found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default DiagnosticTestReport;

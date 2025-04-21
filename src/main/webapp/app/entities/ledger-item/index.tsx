import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LedgerItem from './ledger-item';
import LedgerItemDetail from './ledger-item-detail';
import LedgerItemUpdate from './ledger-item-update';
import LedgerItemDeleteDialog from './ledger-item-delete-dialog';

const LedgerItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LedgerItem />} />
    <Route path="new" element={<LedgerItemUpdate />} />
    <Route path=":id">
      <Route index element={<LedgerItemDetail />} />
      <Route path="edit" element={<LedgerItemUpdate />} />
      <Route path="delete" element={<LedgerItemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LedgerItemRoutes;

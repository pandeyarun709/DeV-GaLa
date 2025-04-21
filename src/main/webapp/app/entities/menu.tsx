import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hospital">
        <Translate contentKey="global.menu.entities.hospital" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/patient">
        <Translate contentKey="global.menu.entities.patient" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/patient-registration-details">
        <Translate contentKey="global.menu.entities.patientRegistrationDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/referral-doctor">
        <Translate contentKey="global.menu.entities.referralDoctor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/department">
        <Translate contentKey="global.menu.entities.department" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        <Translate contentKey="global.menu.entities.employee" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/qualification">
        <Translate contentKey="global.menu.entities.qualification" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/diagnostic-test">
        <Translate contentKey="global.menu.entities.diagnosticTest" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/diagnostic-test-report">
        <Translate contentKey="global.menu.entities.diagnosticTestReport" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/doctor-visit-type">
        <Translate contentKey="global.menu.entities.doctorVisitType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/slot">
        <Translate contentKey="global.menu.entities.slot" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/doctor-visit">
        <Translate contentKey="global.menu.entities.doctorVisit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/prescription">
        <Translate contentKey="global.menu.entities.prescription" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/medicine-dose">
        <Translate contentKey="global.menu.entities.medicineDose" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bed">
        <Translate contentKey="global.menu.entities.bed" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/medicine">
        <Translate contentKey="global.menu.entities.medicine" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/medicine-batch">
        <Translate contentKey="global.menu.entities.medicineBatch" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/admission">
        <Translate contentKey="global.menu.entities.admission" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ledger-item">
        <Translate contentKey="global.menu.entities.ledgerItem" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;

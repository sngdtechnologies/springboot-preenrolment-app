import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/annee">
        <Translate contentKey="global.menu.entities.annee" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/acces">
        <Translate contentKey="global.menu.entities.acces" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/profil">
        <Translate contentKey="global.menu.entities.profil" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/mode-paiement">
        <Translate contentKey="global.menu.entities.modePaiement" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/etat-procedure">
        <Translate contentKey="global.menu.entities.etatProcedure" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/passport">
        <Translate contentKey="global.menu.entities.passport" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/type-passport">
        <Translate contentKey="global.menu.entities.typePassport" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/log-system">
        <Translate contentKey="global.menu.entities.logSystem" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;

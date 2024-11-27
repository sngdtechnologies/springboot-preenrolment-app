import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Annee from './annee';
import Acces from './acces';
import Client from './client';
import Profil from './profil';
import ModePaiement from './mode-paiement';
import EtatProcedure from './etat-procedure';
import Passport from './passport';
import TypePassport from './type-passport';
import LogSystem from './log-system';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="annee/*" element={<Annee />} />
        <Route path="acces/*" element={<Acces />} />
        <Route path="client/*" element={<Client />} />
        <Route path="profil/*" element={<Profil />} />
        <Route path="mode-paiement/*" element={<ModePaiement />} />
        <Route path="etat-procedure/*" element={<EtatProcedure />} />
        <Route path="passport/*" element={<Passport />} />
        <Route path="type-passport/*" element={<TypePassport />} />
        <Route path="log-system/*" element={<LogSystem />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

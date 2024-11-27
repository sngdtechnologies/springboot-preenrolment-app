import React from 'react';
import { Route } from 'react-router-dom';

import EntitiesRoutes from 'app/entities/routes';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import PreEnroleRoutes from './pre-enrole/index';
import Start from './start';

import Annee from 'app/entities/annee';
import Acces from 'app/entities/acces';
import Client from 'app/entities/client';
import Profil from 'app/entities/profil';
import ModePaiement from 'app/entities/mode-paiement';
import EtatProcedure from 'app/entities/etat-procedure';
import Passport from 'app/entities/passport';
import TypePassport from 'app/entities/type-passport';
import LogSystem from 'app/entities/log-system';
import Connexion from './pre-enrole/connexion';

const UserRoutes = () => {
  return (
    <div className="view-routes">
      <ErrorBoundaryRoutes>
        <Route
          path="pre-enrole"
          element={
            // <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
            //   <Start />
            // </PrivateRoute>
            <Start />
          }
        />
        <Route
          path="connexion"
          element={
            // <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
            //   <Connexion />
            // </PrivateRoute>
            <Connexion />
          }
        />
        <Route path="*" element={<PageNotFound />} />
        <Route path="annee/*" element={<Annee />} />
        <Route path="acces/*" element={<Acces />} />
        <Route path="client/*" element={<Client />} />
        <Route path="profil/*" element={<Profil />} />
        <Route path="mode-paiement/*" element={<ModePaiement />} />
        <Route path="etat-procedure/*" element={<EtatProcedure />} />
        <Route path="passport/*" element={<Passport />} />
        <Route path="type-passport/*" element={<TypePassport />} />
        <Route path="log-system/*" element={<LogSystem />} />
      </ErrorBoundaryRoutes>
    </div>
  );
};

export default UserRoutes;

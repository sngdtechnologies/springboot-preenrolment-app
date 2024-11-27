import annee from 'app/entities/annee/annee.reducer';
import acces from 'app/entities/acces/acces.reducer';
import client from 'app/entities/client/client.reducer';
import profil from 'app/entities/profil/profil.reducer';
import modePaiement from 'app/entities/mode-paiement/mode-paiement.reducer';
import etatProcedure from 'app/entities/etat-procedure/etat-procedure.reducer';
import passport from 'app/entities/passport/passport.reducer';
import typePassport from 'app/entities/type-passport/type-passport.reducer';
import logSystem from 'app/entities/log-system/log-system.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  annee,
  acces,
  client,
  profil,
  modePaiement,
  etatProcedure,
  passport,
  typePassport,
  logSystem,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

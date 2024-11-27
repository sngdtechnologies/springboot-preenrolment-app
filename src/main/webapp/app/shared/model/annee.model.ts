import dayjs from 'dayjs';
import { IAcces } from 'app/shared/model/acces.model';
import { IEtatProcedure } from 'app/shared/model/etat-procedure.model';
import { IPassport } from 'app/shared/model/passport.model';
import { IProfil } from 'app/shared/model/profil.model';
import { IModePaiement } from 'app/shared/model/mode-paiement.model';
import { ITypePassport } from 'app/shared/model/type-passport.model';

export interface IAnnee {
  id?: number;
  nom?: string;
  dateDebut?: string | null;
  dateFin?: string | null;
  isVerrouiller?: boolean;
  isCloturer?: boolean;
  acces?: IAcces[] | null;
  etatProcedures?: IEtatProcedure[] | null;
  passports?: IPassport[] | null;
  profils?: IProfil[] | null;
  modePaiements?: IModePaiement[] | null;
  typePassports?: ITypePassport[] | null;
}

export const defaultValue: Readonly<IAnnee> = {
  isVerrouiller: false,
  isCloturer: false,
};

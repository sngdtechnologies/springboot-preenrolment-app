import { IModePaiement } from 'app/shared/model/mode-paiement.model';
import { ITypePassport } from 'app/shared/model/type-passport.model';
import { IAnnee } from 'app/shared/model/annee.model';

export interface IPassport {
  id?: number;
  nom?: string | null;
  modePaiements?: IModePaiement[] | null;
  typePassports?: ITypePassport[] | null;
  annee?: IAnnee;
}

export const defaultValue: Readonly<IPassport> = {};

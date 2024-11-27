import { IAnnee } from 'app/shared/model/annee.model';
import { IPassport } from 'app/shared/model/passport.model';

export interface ITypePassport {
  id?: number;
  nom?: string | null;
  annee?: IAnnee;
  passport?: IPassport;
}

export const defaultValue: Readonly<ITypePassport> = {};

import { IAcces } from 'app/shared/model/acces.model';
import { IAnnee } from 'app/shared/model/annee.model';

export interface IProfil {
  id?: number;
  nom?: string;
  acces?: IAcces[] | null;
  annee?: IAnnee;
}

export const defaultValue: Readonly<IProfil> = {};

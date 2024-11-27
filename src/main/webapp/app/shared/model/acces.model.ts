import { IAnnee } from 'app/shared/model/annee.model';
import { IProfil } from 'app/shared/model/profil.model';

export interface IAcces {
  id?: number;
  nom?: string;
  code?: string;
  annee?: IAnnee;
  profils?: IProfil[] | null;
}

export const defaultValue: Readonly<IAcces> = {};

import { IAnnee } from 'app/shared/model/annee.model';
import { IPassport } from 'app/shared/model/passport.model';
import { IClient } from 'app/shared/model/client.model';

export interface IModePaiement {
  id?: number;
  nom?: string;
  annee?: IAnnee;
  passport?: IPassport;
  client?: IClient;
}

export const defaultValue: Readonly<IModePaiement> = {};

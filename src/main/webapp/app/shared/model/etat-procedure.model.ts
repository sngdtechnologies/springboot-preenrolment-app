import { IAnnee } from 'app/shared/model/annee.model';
import { IClient } from 'app/shared/model/client.model';

export interface IEtatProcedure {
  id?: number;
  etatPreEnrole?: string | null;
  etatEnrole?: string | null;
  etatRetrait?: string | null;
  annee?: IAnnee;
  client?: IClient;
}

export const defaultValue: Readonly<IEtatProcedure> = {};

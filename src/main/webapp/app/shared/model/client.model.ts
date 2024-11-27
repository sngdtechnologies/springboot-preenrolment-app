import dayjs from 'dayjs';
import { IModePaiement } from 'app/shared/model/mode-paiement.model';
import { IEtatProcedure } from 'app/shared/model/etat-procedure.model';

export interface IClient {
  id?: any;
  nom?: string | null;
  prenom?: string | null;
  photoUrl?: string | null;
  dateNaiss?: string | null;
  anneeNaiss?: string | null;
  lieuNaiss?: string | null;
  genre?: string | null;
  typeDemande?: string | null;
  email?: string | null;
  destVoyageP?: string | null;
  motifDeplacement?: string | null;
  paysNaissance?: string | null;
  regionNaiss?: string | null;
  departeNaiss?: string | null;
  telephone?: string | null;
  pays?: string | null;
  region?: string | null;
  departement?: string | null;
  lieu?: string | null;
  rue?: string | null;
  profession?: string | null;
  prenomMere?: string | null;
  nomMere?: string | null;
  prenomPere?: string | null;
  nomPere?: string | null;
  formatCni?: string | null;
  numeroCni?: number | null;
  dateDelivCni?: string | null;
  dateExpCni?: string | null;
  modePaiements?: IModePaiement[] | null;
  etatProcedures?: IEtatProcedure[] | null;
}

export const defaultValue: Readonly<IClient> = {
  id: null,
  nom: '',
  prenom: '',
  photoUrl: '',
  dateNaiss: '',
  anneeNaiss: '',
  lieuNaiss: '',
  genre: '',
  typeDemande: '',
  email: '',
  destVoyageP: '',
  motifDeplacement: '',
  paysNaissance: '',
  regionNaiss: '',
  departeNaiss: '',
  telephone: '',
  pays: '',
  region: '',
  departement: '',
  lieu: '',
  rue: '',
  profession: '',
  prenomMere: '',
  nomMere: '',
  prenomPere: '',
  nomPere: '',
  formatCni: '',
  numeroCni: null,
  dateDelivCni: '',
  dateExpCni: '',
  modePaiements: null,
  etatProcedures: null,
};

export const defaultClient: IClient = {
  id: '',
  nom: '',
  prenom: '',
  photoUrl: '',
  dateNaiss: '2023-02-15',
  anneeNaiss: '',
  lieuNaiss: '',
  genre: '',
  typeDemande: '',
  email: '',
  destVoyageP: '',
  motifDeplacement: '',
  paysNaissance: '',
  regionNaiss: '',
  departeNaiss: '',
  telephone: '',
  pays: '',
  region: '',
  departement: '',
  lieu: '',
  rue: '',
  profession: '',
  prenomMere: '',
  nomMere: '',
  prenomPere: '',
  nomPere: '',
  formatCni: '',
  numeroCni: 0,
  dateDelivCni: '2023-02-15',
  dateExpCni: '2023-02-15',
};

import React, { useEffect, useState, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { isNumber, Translate, translate, ValidatedField } from 'react-jhipster';
import { classNames } from 'primereact/utils';

import { useAppSelector, useAppDispatch } from 'app/config/store';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { START_PREENROLE } from 'app/shared/const/route.const';
import { getEntity, updateEntity, createEntity, reset } from '../../../entities/client/client.reducer';
import { DefaultBreadcrumb } from 'app/components/breadcrumb';
import { InputText } from 'primereact/inputtext';
import { Controller, useForm } from 'react-hook-form';
import { FieldErrorMessage } from 'app/components/form';
import { Password } from 'primereact/password';
import { savePassword } from 'app/modules/account/password/password.reducer';
import { Dropdown } from 'primereact/dropdown';
import { useLocalStorage } from 'primereact/hooks';
import { Image } from 'primereact/image';
import { useInterval } from 'primereact/hooks';
import { Toast } from 'primereact/toast';
import { getUser, getRoles, createUser } from '../../../modules/administration/user-management/user-management.reducer';
import { IUser } from 'app/shared/model/user.model';
import { IClient, defaultClient } from 'app/shared/model/client.model';

export const Connexion = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const toast = useRef(null);

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const { login } = useParams<'login'>();

  const [confirmEmail, setConfirmEmail] = useState<boolean>();
  const [page, setPage] = useState<number>();
  const [clientId, setClientId] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useLocalStorage('', 'email');
  const [nom, setNom] = useLocalStorage('', 'nom');
  const [prenom, setPrenom] = useLocalStorage('', 'prenom');
  const [typeDemande, setTypeDemande] = useLocalStorage('', 'typeDemande');
  const [destVoyageP, setDestVoyageP] = useLocalStorage('', 'destVoyageP');
  const [motifDeplacement, setMotifDeplacement] = useLocalStorage('', 'motifDeplacement');
  const [photoUrl, setPhotoUrl] = useLocalStorage('', 'photoUrl');
  const [dateNaiss, setDateNaiss] = useLocalStorage('', 'dateNaiss');
  const [anneeNaiss, setAnneeNaiss] = useLocalStorage('', 'anneeNaiss');
  const [lieuNaiss, setLieuNaiss] = useLocalStorage('', 'lieuNaiss');
  const [genre, setGenre] = useLocalStorage('', 'genre');
  const [paysNaissance, setPaysNaissance] = useLocalStorage('', 'paysNaissance');
  const [regionNaiss, setRegionNaiss] = useLocalStorage('', 'regionNaiss');
  const [departeNaiss, setDeparteNaiss] = useLocalStorage('', 'departeNaiss');
  const [telephone, setTelephone] = useLocalStorage('', 'telephone');
  const [pays, setPays] = useLocalStorage('', 'pays');
  const [region, setRegion] = useLocalStorage('', 'region');
  const [departement, setDepartement] = useLocalStorage('', 'departement');
  const [lieu, setLieu] = useLocalStorage('', 'lieu');
  const [rue, setRue] = useLocalStorage('', 'rue');
  const [profession, setProfession] = useLocalStorage('', 'profession');
  const [prenomMere, setPrenomMere] = useLocalStorage('', 'prenomMere');
  const [nomMere, setNomMere] = useLocalStorage('', 'nomMere');
  const [prenomPere, setPrenomPere] = useLocalStorage('', 'prenomPere');
  const [formatCni, setFormatCni] = useLocalStorage('', 'formatCni');
  const [numeroCni, setNumeroCni] = useLocalStorage<number>(0, 'numeroCni');
  const [dateDelivCni, setDateDelivCni] = useLocalStorage('', 'dateDelivCni');
  const [dateExpCni, setDateExpCni] = useLocalStorage('', 'dateExpCni');
  const [nomPere, setNomPere] = useLocalStorage('', 'nomPere');
  const [imageSelected, setImageSelected] = useLocalStorage('', 'imageSelected');
  const time = 10;
  const code = '15002253';

  const [seconds, setSeconds] = useState<number>();
  const [active, setActive] = useState<boolean>();
  const [newUser, setNewUser] = useState<IUser>();
  const [newClient, setNewClient] = useState<IClient>();

  const updateSuccess = useAppSelector(state => state.client.updateSuccess);
  const clientEntity = useAppSelector(state => state.client.entity);
  const loading = useAppSelector(state => state.client.loading);

  const user = useAppSelector(state => state.userManagement.user);
  const authorities = useAppSelector(state => state.userManagement.authorities);

  const typeDemandes = [
    { index: 0, nom: 'Premier passeport' },
    { index: 1, nom: 'Périmé' },
    { index: 2, nom: 'Passeport perdu' },
    { index: 4, nom: 'Passeport volé' },
    { index: 5, nom: 'Détérioration' },
    { index: 6, nom: 'Changement de filiation' },
    { index: 7, nom: 'Changement de nom' },
    { index: 8, nom: 'Correction' },
    { index: 9, nom: 'Passeport plein' },
  ];

  const notify = (severity, summary, detail, life = 3000) => {
    toast.current.show({ severity: severity, summary: summary, detail: detail, life: life });
  };

  const handleClose = () => {
    navigate('/home');
  };

  useInterval(
    () => {
      setSeconds(e => e - 1);
      if (seconds == 0) {
        setActive(false);
      }
    },
    1000,
    active
  );

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    } else {
      dispatch(getUser(login));
      dispatch(getRoles());
      setConfirmEmail(false);
      setPage(0);
      setActive(false);
      setSeconds(10);
      setEmail(null);
    }
  }, []);

  // useEffect(() => {
  //   if (updateSuccess && page == 5) {
  //     handleClose();
  //   }
  // }, [updateSuccess]);

  useEffect(() => {
    setNewUser(user);
  }, [user]);

  useEffect(() => {
    setNewClient(clientEntity);
  }, [clientEntity]);

  const saveEntity = values => {
    // const entity = {
    //   ...clientEntity,
    //   ...values,
    // };

    // if (isNew) {
    //   dispatch(createEntity(entity));
    // } else {
    //   dispatch(updateEntity(entity));
    // }
    console.log('password', password);
    console.log('email', email);
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...clientEntity,
        };

  const handleClickBegin = () => {
    navigate(START_PREENROLE);
  };

  const handleFormSubmit = e => {
    console.log(e);
    handleSubmit(saveEntity)(e);
  };

  const backEmail = () => {
    setConfirmEmail(false);
  };

  const handleGoConfirmPassword = () => {
    if (email != null) {
      setConfirmEmail(true);
      handleStartTimer();
    } else {
      notify('error', 'Error', 'Un email est requie pour la suite');
    }
  };

  const handleSetNom = e => {
    console.log(e);
  };

  const goOnPage = d => {
    setPage(d);
  };

  const handleSetSelectedImage = i => {
    setImageSelected(i);
  };

  const handleResendCode = () => {
    handleStartTimer();
  };

  const handleStartTimer = () => {
    setSeconds(time);
    setActive(true);
  };

  const handleConfirmPassword = () => {
    console.log('authorities', authorities);
    console.log('user', user);
    const objet = {
      id: '',
      login: email,
      firstName: '',
      lastName: '',
      email: email,
      activated: true,
      langKey: 'fr',
      authorities: ['ROLE_USER'],
      createdBy: '',
      createdDate: null,
      lastModifiedBy: '',
      lastModifiedDate: null,
      password: password,
    };
    dispatch(createUser(objet));
    goOnPage(1);
  };

  const handleSavePage2 = () => {
    if (typeDemande != null && destVoyageP != null && motifDeplacement != null) {
      let objet: IClient = defaultClient;
      objet.email = email;
      objet.typeDemande = typeDemande;
      objet.destVoyageP = destVoyageP;
      objet.motifDeplacement = motifDeplacement;

      console.log('createEntity', objet);
      dispatch(createEntity(objet));
      goOnPage(3);
    }
  };

  const handleSavePage3 = () => {
    console.log('oui');
    if (
      // nom != null
      // && prenom != null
      // && dateNaiss != null
      // && lieuNaiss != null
      // && genre != null
      // && paysNaissance != null
      // && regionNaiss != null
      // && departeNaiss != null
      // && telephone != null
      // && pays != null
      // && region != null
      // && departement != null
      // && lieu != null
      // && rue != null
      // && profession != null
      true
    ) {
      let objet: IClient = defaultClient;
      objet.id = newClient.id;
      objet.nom = nom;
      objet.prenom = prenom;
      objet.dateNaiss = dateNaiss;
      objet.lieuNaiss = lieuNaiss;
      objet.genre = genre;
      objet.paysNaissance = paysNaissance;
      objet.regionNaiss = regionNaiss;
      objet.departeNaiss = departeNaiss;
      objet.telephone = telephone;
      objet.pays = pays;
      objet.region = region;
      objet.departement = departement;
      objet.lieu = lieu;
      objet.rue = rue;
      objet.profession = profession;

      // objet.nom = nom;
      // objet.prenom = prenom;
      // objet.photoUrl = photoUrl;
      // objet.dateNaiss = dateNaiss;
      // objet.anneeNaiss = anneeNaiss;
      // objet.lieuNaiss = lieuNaiss;
      // objet.genre = genre;
      // objet.paysNaissance = paysNaissance;
      // objet.regionNaiss = regionNaiss;
      // objet.departeNaiss = departeNaiss;
      // objet.telephone = telephone;
      // objet.pays = pays;
      // objet.region = region;
      // objet.departement = departement;
      // objet.lieu = lieu;
      // objet.rue = rue;
      // objet.profession = profession;
      // objet.prenomMere = prenomMere;
      // objet.nomMere = nomMere;
      // objet.prenomPere = prenomPere;
      // objet.nomPere = nomPere;
      // objet.formatCni = formatCni;
      // objet.numeroCni = numeroCni;
      // objet.dateDelivCni = dateDelivCni;
      // objet.dateExpCni = dateExpCni;

      console.log('updateEntity', objet);
      dispatch(updateEntity(objet));
    } else {
      notify('error', 'Error', 'Tout les champs doivent etre remplie');
    }
    goOnPage(4);
  };

  const handleSavePage4 = () => {
    console.log('oui');
    if (
      prenomMere != null &&
      nomMere != null &&
      nomPere != null &&
      prenomPere != null &&
      formatCni != null &&
      numeroCni != null &&
      dateDelivCni != null &&
      dateExpCni != null
    ) {
      let objet: IClient = defaultClient;
      objet.id = newClient.id;
      objet.prenomMere = prenomMere;
      objet.nomMere = nomMere;
      objet.prenomPere = prenomPere;
      objet.nomPere = nomPere;
      objet.formatCni = formatCni;
      objet.numeroCni = numeroCni;
      objet.dateDelivCni = dateDelivCni;
      objet.dateExpCni = dateExpCni;

      console.log('updateEntity', objet);
      dispatch(updateEntity(objet));
    } else {
      notify('error', 'Error', 'Tout les champs doivent etre remplie');
    }
    goOnPage(5);
  };

  const handleSavePage5 = () => {
    // goOnPage(6);
    console.log('terminer');
    // handleClose();
  };

  const {
    control,
    formState: { errors },
    handleSubmit,
    reset,
    setValue,
  } = useForm();

  return (
    <div>
      <Toast ref={toast} />
      <h3 className="c-v">
        {page == 0 ? (
          <span>Connexion</span>
        ) : page == 1 ? (
          <span>Type de procédure</span>
        ) : page == 2 ? (
          <span>Entrez les informations biographiques</span>
        ) : (
          <span>Entrez les informations biographiques</span>
        )}
      </h3>
      <DefaultBreadcrumb index={page} />
      <form onSubmit={handleFormSubmit}>
        {page == 0 ? (
          <div className="flex flex-column align-items-center">
            <h5 id="connexion" className="mt-7">
              Accédez au portail de pré-enrôlement du passeport électronique du Cameroun
            </h5>
            <label htmlFor="email">Entrez le code que vous avez reçu sur votre adresse électronique descartessob@gmail.com</label>

            {confirmEmail == true ? (
              <div className="mt-7">
                <div className="flex flex-column gap-2">
                  <Controller
                    name="email"
                    control={control}
                    aria-describedby="email-help"
                    rules={{ required: translate('validation.required') }}
                    render={({ field, fieldState }) => (
                      <Password
                        id={field.name}
                        {...field}
                        placeholder="Mot de passe à usage unique"
                        className={classNames({ 'p-invalid': fieldState.error }) + ' col-12 md:col-12'}
                        onChange={e => {
                          field.onChange(e.target.value);
                          setPassword(e.target.value);
                        }}
                        toggleMask
                      />
                    )}
                  />
                  {active == true ? (
                    <span>Renvoyer le code dans {seconds}</span>
                  ) : (
                    <Button color="default" data-cy="entityCreateSaveButton" onClick={handleResendCode}>
                      {' '}
                      Renvoyer le code{' '}
                    </Button>
                  )}
                  <FieldErrorMessage errors={errors} name={'email'} />
                </div>

                <div className="flex flex-wrap gap-7 mt-3">
                  <Button id="cancel-save" data-cy="entityCreateCancelButton" color="info" onClick={backEmail}>
                    <FontAwesomeIcon icon="arrow-left" />
                    &nbsp;
                    <span className="d-none d-md-inline">retour</span>
                  </Button>
                  &nbsp;
                  <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" onClick={handleConfirmPassword}>
                    Suivant
                  </Button>
                </div>
              </div>
            ) : (
              <div className="mt-7">
                <div className="flex flex-column gap-2">
                  <small id="email-help" className="fs-5 pl-5">
                    Entrer votre adresse électronique
                  </small>
                  <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <Controller
                      name="email"
                      control={control}
                      aria-describedby="email-help"
                      rules={{ required: translate('validation.required') }}
                      render={({ field, fieldState }) => (
                        <InputText
                          id={field.name}
                          {...field}
                          placeholder="Entrer l'adresse électronique"
                          className={classNames({ 'p-invalid': fieldState.error }) + ' col-12 md:col-12'}
                          onChange={e => {
                            field.onChange(e.target.value);
                            setEmail(e.target.value);
                          }}
                        />
                      )}
                    />
                    <FieldErrorMessage errors={errors} name={'email'} />
                  </span>
                </div>

                <div className="flex flex-wrap gap-7 mt-3">
                  <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pre-enrole" replace color="info">
                    <FontAwesomeIcon icon="arrow-left" />
                    &nbsp;
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.back">Retout</Translate>
                    </span>
                  </Button>
                  &nbsp;
                  <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" onClick={handleGoConfirmPassword}>
                    Suivant
                  </Button>
                </div>
              </div>
            )}
          </div>
        ) : page == 1 ? (
          <div className="flex flex-column align-items-center">
            <h5 id="type-procedure" className="mt-7">
              Sélectionnez le type de procédure
            </h5>
            <label htmlFor="email">Veuillez choisir le type de service</label>
            <div className="flex flex-wrap gap-7 mt-3">
              <Button
                color="default"
                id="logout"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  goOnPage(2);
                }}
              >
                Cliquer ici pour commencer
              </Button>
            </div>
            <div className="flex flex-column align-items-start mt-2">
              <Button
                color="primary"
                id="logout"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  goOnPage(0);
                }}
              >
                Se déconecter
              </Button>
            </div>
          </div>
        ) : page == 2 ? (
          <div className="flex flex-column align-items-center">
            <h5 id="type-procedure" className="mt-7">
              <strong>Informations générales</strong>
            </h5>
            <h5 className="">Entrez vos informations générales ci-dessous</h5>
            <h6>
              <strong>NUMERO DE LA DEMANDE: PO-20230219-000214</strong>
            </h6>

            <div className="p-grid">
              <div className="p-col">
                <label htmlFor="type-demande">Type de demande</label>
                <Controller
                  name="type-demande"
                  control={control}
                  aria-describedby="type-demande-help"
                  rules={{ required: translate('validation.required') }}
                  render={({ field, fieldState }) => (
                    <Dropdown
                      id={field.name}
                      {...field}
                      className={classNames({ 'p-invalid': fieldState.invalid })}
                      options={typeDemandes}
                      optionLabel="nom"
                      placeholder="Sélectionnez le type de la demande"
                      onChange={e => {
                        field.onChange(e.target.value);
                        setTypeDemande(e.target.value);
                      }}
                    />
                  )}
                />
                <FieldErrorMessage errors={errors} name={'type-demande'} />
              </div>

              <div className="p-col">
                <ValidatedField
                  label={translate('pecapApp.client.destVoyageP')}
                  id="client-destVoyageP"
                  name="destVoyageP"
                  data-cy="destVoyageP"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                  }}
                  onChange={e => {
                    setTypeDemande(e.target.value);
                  }}
                />
              </div>

              <div className="p-col">
                <ValidatedField
                  label={translate('pecapApp.client.motifDeplacement')}
                  id="client-motifDeplacement"
                  name="motifDeplacement"
                  data-cy="motifDeplacement"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                  }}
                />
              </div>
            </div>

            <div className="flex flex-wrap gap-7 mt-3">
              <Button
                color="default"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  goOnPage(1);
                }}
              >
                Annuler
              </Button>
              &nbsp;
              <Button
                color="default"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  handleSavePage2();
                }}
              >
                Suivant
              </Button>
            </div>
          </div>
        ) : page == 3 ? (
          <div>
            <div className="flex flex-column align-items-center">
              <h5 id="type-procedure" className="mt-7">
                <strong>Informations personnelles</strong>
              </h5>
              <h5 className="">Entrez vos informations personnelles ci-dessous</h5>
              <h6>
                <strong>NUMERO DE LA DEMANDE: PO-20230219-000214</strong>
              </h6>
            </div>

            <h6>
              <strong>Donnée personnelle</strong>
            </h6>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.nom')}
                  id="client-nom"
                  name="nom"
                  data-cy="nom"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setNom(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.prenom')}
                  id="client-prenom"
                  name="prenom"
                  data-cy="prenom"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setPrenom(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.dateNaiss')}
                  id="client-dateNaiss"
                  name="dateNaiss"
                  data-cy="dateNaiss"
                  type="date"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                  onChange={e => {
                    setDateNaiss(e.target.value);
                  }}
                />
              </div>
            </div>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.lieuNaiss')}
                  id="client-lieuNaiss"
                  name="lieuNaiss"
                  data-cy="lieuNaiss"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setLieuNaiss(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.genre')}
                  id="client-genre"
                  name="genre"
                  data-cy="genre"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                  }}
                  onChange={e => {
                    setGenre(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.paysNaissance')}
                  id="client-paysNaissance"
                  name="paysNaissance"
                  data-cy="paysNaissance"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setPaysNaissance(e.target.value);
                  }}
                />
              </div>
            </div>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.regionNaiss')}
                  id="client-regionNaiss"
                  name="regionNaiss"
                  data-cy="regionNaiss"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setRegionNaiss(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.departeNaiss')}
                  id="client-departeNaiss"
                  name="departeNaiss"
                  data-cy="departeNaiss"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setDeparteNaiss(e.target.value);
                  }}
                />
              </div>
            </div>

            <h6>
              <strong>Information sur le demandeur</strong>
            </h6>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.telephone')}
                  id="client-telephone"
                  name="telephone"
                  data-cy="telephone"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 20, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setTelephone(e.target.value);
                  }}
                />
              </div>
            </div>

            <h6>
              <strong>Lieu de résidence</strong>
            </h6>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.pays')}
                  id="client-pays"
                  name="pays"
                  data-cy="pays"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                  onChange={e => {
                    setPays(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.region')}
                  id="client-region"
                  name="region"
                  data-cy="region"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setRegion(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.departement')}
                  id="client-departement"
                  name="departement"
                  data-cy="departement"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setDepartement(e.target.value);
                  }}
                />
              </div>
            </div>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.lieu')}
                  id="client-lieu"
                  name="lieu"
                  data-cy="lieu"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setDepartement(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.rue')}
                  id="client-rue"
                  name="rue"
                  data-cy="rue"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setDepartement(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.profession')}
                  id="client-profession"
                  name="profession"
                  data-cy="profession"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setDepartement(e.target.value);
                  }}
                />
              </div>
            </div>

            <div className="flex flex-wrap gap-7 mt-3">
              <Button
                color="default"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  goOnPage(2);
                }}
              >
                Annuler
              </Button>
              &nbsp;
              <Button
                color="default"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  handleSavePage3();
                }}
              >
                Suivant
              </Button>
            </div>
          </div>
        ) : page == 4 ? (
          <div>
            <div className="flex flex-column align-items-center">
              <h5 id="type-procedure" className="mt-7">
                <strong>Informations parentalles</strong>
              </h5>
              <h5 className="">Entrez vos informations parentalles ci-dessous</h5>
              <h6>
                <strong>NUMERO DE LA DEMANDE: PO-20230219-000214</strong>
              </h6>
            </div>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.prenomMere')}
                  id="client-prenomMere"
                  name="prenomMere"
                  data-cy="prenomMere"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setPrenomMere(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.nomMere')}
                  id="client-nomMere"
                  name="nomMere"
                  data-cy="nomMere"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setNomMere(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.nomPere')}
                  id="client-nomPere"
                  name="nomPere"
                  data-cy="nomPere"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setNomPere(e.target.value);
                  }}
                />
              </div>
            </div>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.prenomPere')}
                  id="client-prenomPere"
                  name="prenomPere"
                  data-cy="prenomPere"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                  onChange={e => {
                    setPrenomPere(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.formatCni')}
                  id="client-formatCni"
                  name="formatCni"
                  data-cy="formatCni"
                  type="text"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                  }}
                  onChange={e => {
                    setFormatCni(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.numeroCni')}
                  id="client-numeroCni"
                  name="numeroCni"
                  data-cy="numeroCni"
                  type="number"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    validate: v => isNumber(v) || translate('entity.validation.number'),
                  }}
                  onChange={e => {
                    setNumeroCni(Number(e.target.value));
                  }}
                />
              </div>
            </div>

            <div className="row">
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.dateDelivCni')}
                  id="client-dateDelivCni"
                  name="dateDelivCni"
                  data-cy="dateDelivCni"
                  type="date"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                  onChange={e => {
                    setDateDelivCni(e.target.value);
                  }}
                />
              </div>
              <div className="col-12 md:col-4">
                <ValidatedField
                  label={translate('pecapApp.client.dateExpCni')}
                  id="client-dateExpCni"
                  name="dateExpCni"
                  data-cy="dateExpCni"
                  type="date"
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                  }}
                  onChange={e => {
                    setDateExpCni(e.target.value);
                  }}
                />
              </div>
            </div>

            <div className="flex flex-wrap gap-7 mt-3">
              <Button
                color="default"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  goOnPage(3);
                }}
              >
                Annuler
              </Button>
              &nbsp;
              <Button
                color="default"
                data-cy="entityCreateSaveButton"
                onClick={() => {
                  handleSavePage4();
                }}
              >
                Suivant
              </Button>
            </div>
          </div>
        ) : (
          <div>
            <div className="flex flex-column align-items-center">
              <h5 id="type-procedure" className="mt-7">
                <strong>Page de paiement</strong>
              </h5>
              <h5 className="mb-8">Choisissez le mode de paiement</h5>
              <strong>Informations du paiement</strong>
              <h5 className="">
                Nom du produit : <strong>Frais de passeport</strong>
              </h5>
              <h5 className="">
                Montant : <strong>110 000 XAF</strong>
              </h5>

              <div className="row justify-content-strech">
                <div className="col-12 md:col-6">
                  <Image
                    src="content/images/om.jpg"
                    alt="Image"
                    width={imageSelected == 'Orange money' ? '100' : '150'}
                    className="img-fluid"
                    id="om-img"
                    onClick={() => {
                      handleSetSelectedImage('Orange money');
                    }}
                  />
                </div>
                <div className="col-12 md:col-6">
                  <Image
                    src="content/images/momo.jpg"
                    alt="Image"
                    width={imageSelected == 'Mobile money' ? '100' : '150'}
                    className="img-fluid"
                    id="momo-img"
                    onClick={() => {
                      handleSetSelectedImage('Mobile money');
                    }}
                  />
                </div>
              </div>
            </div>

            <div className="flex flex-column align-items-center">
              <ValidatedField
                label={translate('pecapApp.client.nom')}
                id="client-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
                onChange={e => {
                  setNom(e.target.value);
                }}
              />
              <ValidatedField
                label={translate('pecapApp.client.telephone')}
                id="client-telephone"
                name="telephone"
                data-cy="telephone"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 20, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
                onChange={e => {
                  setTelephone(e.target.value);
                }}
              />
              <div className="flex flex-wrap gap-7 mt-3">
                <Button
                  color="default"
                  data-cy="entityCreateSaveButton"
                  onClick={() => {
                    goOnPage(1);
                  }}
                >
                  Annuler
                </Button>
                &nbsp;
                <Button
                  color="primary"
                  data-cy="entityCreateSaveButton"
                  onClick={() => {
                    handleSavePage5();
                  }}
                >
                  Payer
                </Button>
              </div>
            </div>
          </div>
        )}
      </form>
    </div>
  );
};

export default Connexion;

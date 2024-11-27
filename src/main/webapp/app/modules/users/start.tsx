import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Translate } from 'react-jhipster';

import { useAppSelector } from 'app/config/store';
import { Button } from 'primereact/button';
import { CONNEXION } from 'app/shared/const/route.const';

export const Start = () => {
  const navigate = useNavigate();

  const handleClickPhone = () => {
    navigate(CONNEXION);
  };

  const handleClickEmail = () => {
    navigate(CONNEXION);
  };

  return (
    <div className="flex flex-column align-items-center">
      <h2 id="start">
        <Translate contentKey="pecapApp.client.preEnrole.start.title">Titre</Translate>
      </h2>

      <span className="mt-100">Choisissez votre support d’authentification</span>
      <div className="flex flex-wrap gap-7 mt-3">
        <Button label="Phone" raised onClick={handleClickPhone} />
        <Button label="Email" raised onClick={handleClickEmail} />
      </div>
    </div>
  );
};

export default Start;

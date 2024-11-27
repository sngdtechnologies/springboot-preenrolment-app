import React, { useState } from 'react';
import { Steps } from 'primereact/steps';

export const DefaultBreadcrumb = props => {
  const items = [
    {
      label: 'Connexion',
    },
    {
      label: 'Type de procédure',
    },
    {
      label: 'Info générale',
    },
    {
      label: 'Info personnelle',
    },
    {
      label: 'Info parentalle',
    },
    {
      label: 'Payement',
    },
  ];

  return <Steps model={items} activeIndex={props.index} />;
};

export default DefaultBreadcrumb;

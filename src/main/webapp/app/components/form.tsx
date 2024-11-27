import { Dropdown } from 'primereact/dropdown';
import React from 'react';
import { translate } from 'react-jhipster';
import { Calendar } from 'primereact/calendar';

export const LabelName = props => {
  const name = props.name;
  const required = props.required === true;
  if (required) {
    return (
      <span>
        {translate(name)}
        <span className="p-error">*</span>{' '}
      </span>
    );
  }
  return <span>{translate(name)}</span>;
};

export const ValidatedFieldLabel = props => {
  const htmlFor = props.htmlFor;
  const name = props.name;
  const required = props.required === undefined ? true : props.required;

  return (
    <label htmlFor={htmlFor} className="mb-2">
      <LabelName name={name} required={required} />
    </label>
  );
};

export const FieldErrorMessage = props => {
  const errors = props.errors;
  const name = props.name;
  if (errors && errors[name]) {
    return <small className="p-error block">{errors[name].message}</small>;
  }
  return <span></span>;
};

export const CalendarDate = props => {
  const id = props.id;
  const value = props.value;
  const onValueChange = props.onValueChange;

  const monthNavigatorTemplate = (e: any) => {
    return (
      <Dropdown
        value={e.value}
        options={e.options}
        onChange={event => e.onChange(event.originalEvent, event.value)}
        style={{ lineHeight: 1 }}
      />
    );
  };

  const yearNavigatorTemplate = (e: any) => {
    return (
      <Dropdown
        value={e.value}
        options={e.options}
        onChange={event => e.onChange(event.originalEvent, event.value)}
        className="ml-2"
        style={{ lineHeight: 1 }}
      />
    );
  };

  return (
    <Calendar
      id={id}
      value={value}
      onChange={e => onValueChange(e.value)}
      dateFormat="dd/mm/yy"
      monthNavigator
      yearNavigator
      yearRange="2010:2030"
      monthNavigatorTemplate={monthNavigatorTemplate}
      yearNavigatorTemplate={yearNavigatorTemplate}
      showButtonBar
      showIcon
    />
  );
};

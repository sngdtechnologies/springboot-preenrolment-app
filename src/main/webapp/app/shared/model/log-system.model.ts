import dayjs from 'dayjs';

export interface ILogSystem {
  id?: number;
  eventDate?: string;
  login?: string;
  message?: string;
}

export const defaultValue: Readonly<ILogSystem> = {};

import { IReaction } from 'app/shared/model/reaction.model';

export interface IJoke {
  id?: number;
  question?: string;
  answer?: string;
  reactions?: IReaction[];
}

export class Joke implements IJoke {
  constructor(public id?: number, public question?: string, public answer?: string, public reactions?: IReaction[]) {}
}

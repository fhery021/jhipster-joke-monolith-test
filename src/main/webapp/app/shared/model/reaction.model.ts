import { IJoke } from 'app/shared/model/joke.model';

export interface IReaction {
  id?: number;
  comment?: string;
  like?: boolean;
  joke?: IJoke;
}

export class Reaction implements IReaction {
  constructor(public id?: number, public comment?: string, public like?: boolean, public joke?: IJoke) {
    this.like = this.like || false;
  }
}

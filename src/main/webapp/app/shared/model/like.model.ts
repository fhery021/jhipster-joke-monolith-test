import { IJoke } from 'app/shared/model/joke.model';

export interface ILike {
  id?: number;
  userId?: number;
  jokeId?: number;
  joke?: IJoke;
}

export class Like implements ILike {
  constructor(public id?: number, public userId?: number, public jokeId?: number, public joke?: IJoke) {}
}

import { IJoke } from 'app/shared/model/joke.model';

export interface ILike {
  id?: number;
  userId?: number;
  joke?: IJoke;
}

export class Like implements ILike {
  constructor(public id?: number, public userId?: number, public joke?: IJoke) {}
}

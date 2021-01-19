import { ILike } from 'app/shared/model/like.model';

export interface IJoke {
  id?: number;
  text?: string;
  likes?: ILike[];
}

export class Joke implements IJoke {
  constructor(public id?: number, public text?: string, public likes?: ILike[]) {}
}

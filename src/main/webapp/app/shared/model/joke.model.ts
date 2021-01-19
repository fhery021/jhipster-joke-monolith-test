import { ILike } from 'app/shared/model/like.model';

export interface IJoke {
  id?: number;
  question?: string;
  answer?: string;
  likes?: ILike[];
}

export class Joke implements IJoke {
  constructor(public id?: number, public question?: string, public answer?: string, public likes?: ILike[]) {}
}

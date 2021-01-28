import { IComment } from 'app/shared/model/comment.model';
import { ILike } from 'app/shared/model/like.model';

export interface IJoke {
  id?: number;
  question?: string;
  answer?: string;
  comments?: IComment[];
  likes?: ILike[];
}

export class Joke implements IJoke {
  constructor(public id?: number, public question?: string, public answer?: string, public comments?: IComment[], public likes?: ILike[]) {}
}

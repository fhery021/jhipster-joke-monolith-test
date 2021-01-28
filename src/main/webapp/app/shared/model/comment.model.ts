import { IJoke } from 'app/shared/model/joke.model';

export interface IComment {
  id?: number;
  text?: string;
  accountId?: string;
  joke?: IJoke;
}

export class Comment implements IComment {
  constructor(public id?: number, public text?: string, public accountId?: string, public joke?: IJoke) {}
}

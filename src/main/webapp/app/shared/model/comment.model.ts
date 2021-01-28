import { IJoke } from 'app/shared/model/joke.model';

export interface IComment {
  id?: number;
  text?: string;
  joke?: IJoke;
}

export class Comment implements IComment {
  constructor(public id?: number, public text?: string, public joke?: IJoke) {}
}

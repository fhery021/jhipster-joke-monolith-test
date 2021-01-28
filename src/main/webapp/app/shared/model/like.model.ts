import { IJoke } from 'app/shared/model/joke.model';

export interface ILike {
  id?: number;
  liked?: boolean;
  accountId?: string;
  joke?: IJoke;
}

export class Like implements ILike {
  constructor(public id?: number, public liked?: boolean, public accountId?: string, public joke?: IJoke) {
    this.liked = this.liked || false;
  }
}

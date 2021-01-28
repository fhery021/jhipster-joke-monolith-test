import { IJoke } from 'app/shared/model/joke.model';

export interface ILike {
  id?: number;
  liked?: boolean;
  joke?: IJoke;
}

export class Like implements ILike {
  constructor(public id?: number, public liked?: boolean, public joke?: IJoke) {
    this.liked = this.liked || false;
  }
}

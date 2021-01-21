import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'joke',
        loadChildren: () => import('./joke/joke.module').then(m => m.JokeMonolithTestJokeModule),
      },
      {
        path: 'like',
        loadChildren: () => import('./like/like.module').then(m => m.JokeMonolithTestLikeModule),
      },
      {
        path: 'reaction',
        loadChildren: () => import('./reaction/reaction.module').then(m => m.JokeMonolithTestReactionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JokeMonolithTestEntityModule {}

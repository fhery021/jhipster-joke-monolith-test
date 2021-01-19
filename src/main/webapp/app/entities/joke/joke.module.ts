import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JokeMonolithTestSharedModule } from 'app/shared/shared.module';
import { JokeComponent } from './joke.component';
import { JokeDetailComponent } from './joke-detail.component';
import { JokeUpdateComponent } from './joke-update.component';
import { JokeDeleteDialogComponent } from './joke-delete-dialog.component';
import { jokeRoute } from './joke.route';

@NgModule({
  imports: [JokeMonolithTestSharedModule, RouterModule.forChild(jokeRoute)],
  declarations: [JokeComponent, JokeDetailComponent, JokeUpdateComponent, JokeDeleteDialogComponent],
  entryComponents: [JokeDeleteDialogComponent],
})
export class JokeMonolithTestJokeModule {}

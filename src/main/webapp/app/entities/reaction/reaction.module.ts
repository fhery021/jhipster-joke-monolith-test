import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JokeMonolithTestSharedModule } from 'app/shared/shared.module';
import { ReactionComponent } from './reaction.component';
import { ReactionDetailComponent } from './reaction-detail.component';
import { ReactionUpdateComponent } from './reaction-update.component';
import { ReactionDeleteDialogComponent } from './reaction-delete-dialog.component';
import { reactionRoute } from './reaction.route';

@NgModule({
  imports: [JokeMonolithTestSharedModule, RouterModule.forChild(reactionRoute)],
  declarations: [ReactionComponent, ReactionDetailComponent, ReactionUpdateComponent, ReactionDeleteDialogComponent],
  entryComponents: [ReactionDeleteDialogComponent],
})
export class JokeMonolithTestReactionModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JokeMonolithTestSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [JokeMonolithTestSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class JokeMonolithTestHomeModule {}

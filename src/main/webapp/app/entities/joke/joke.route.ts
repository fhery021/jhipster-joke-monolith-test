import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJoke, Joke } from 'app/shared/model/joke.model';
import { JokeService } from './joke.service';
import { JokeComponent } from './joke.component';
import { JokeDetailComponent } from './joke-detail.component';
import { JokeUpdateComponent } from './joke-update.component';

@Injectable({ providedIn: 'root' })
export class JokeResolve implements Resolve<IJoke> {
  constructor(private service: JokeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJoke> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((joke: HttpResponse<Joke>) => {
          if (joke.body) {
            return of(joke.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Joke());
  }
}

export const jokeRoute: Routes = [
  {
    path: '',
    component: JokeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.joke.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JokeDetailComponent,
    resolve: {
      joke: JokeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.joke.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JokeUpdateComponent,
    resolve: {
      joke: JokeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.joke.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JokeUpdateComponent,
    resolve: {
      joke: JokeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.joke.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

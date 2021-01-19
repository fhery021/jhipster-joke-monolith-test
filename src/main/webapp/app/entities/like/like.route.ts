import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILike, Like } from 'app/shared/model/like.model';
import { LikeService } from './like.service';
import { LikeComponent } from './like.component';
import { LikeDetailComponent } from './like-detail.component';
import { LikeUpdateComponent } from './like-update.component';

@Injectable({ providedIn: 'root' })
export class LikeResolve implements Resolve<ILike> {
  constructor(private service: LikeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILike> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((like: HttpResponse<Like>) => {
          if (like.body) {
            return of(like.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Like());
  }
}

export const likeRoute: Routes = [
  {
    path: '',
    component: LikeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.like.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LikeDetailComponent,
    resolve: {
      like: LikeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.like.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LikeUpdateComponent,
    resolve: {
      like: LikeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.like.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LikeUpdateComponent,
    resolve: {
      like: LikeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jokeMonolithTestApp.like.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

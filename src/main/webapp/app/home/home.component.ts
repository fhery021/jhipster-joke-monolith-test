import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subscription } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJoke } from 'app/shared/model/joke.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JokeService } from '../entities/joke/joke.service';
import { JokeDeleteDialogComponent } from '../entities/joke/joke-delete-dialog.component';
import { LikeService } from 'app/entities/like/like.service';
import { ILike, Like } from 'app/shared/model/like.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  isSaving = false;
  jokes: IJoke[];
  likes?: ILike[];
  jokesSubscriber?: Subscription;
  likesSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  account: Account | null = null;
  authSubscription?: Subscription;

  constructor(
    protected jokeService: JokeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    private accountService: AccountService,
    private likeService: LikeService
  ) {
    this.jokes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJokes();
    this.registerChangeInLikes();
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      if (account !== null) {
        this.loadAll();
      }
    });
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  loadAll(): void {
    this.jokeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IJoke[]>) => this.paginateJokes(res.body, res.headers));
    this.likeService.query().subscribe((res: HttpResponse<ILike[]>) => (this.likes = res.body || []));
  }

  reset(): void {
    this.page = 0;
    this.jokes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  trackId(index: number, item: IJoke): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJokes(): void {
    this.jokesSubscriber = this.eventManager.subscribe('jokeListModification', () => this.reset());
  }

  registerChangeInLikes(): void {
    this.likesSubscriber = this.eventManager.subscribe('likeListModification', () => this.loadAll());
  }

  delete(joke: IJoke): void {
    const modalRef = this.modalService.open(JokeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.joke = joke;
  }

  onLike(joke: IJoke): void {
    this.isSaving = true;

    if (this.alreadyLiked(joke)) {
      this.dislikeJoke(joke);
    } else {
      this.likeJoke(joke);
    }
  }

  dislikeJoke(joke: IJoke): void {
    if (this.account !== null) {
      const like = this.likes?.find(l => l.accountId === this.account?.login && l.joke?.id === joke.id);
      if (like !== undefined && like.id !== undefined) {
        this.subscribeToSaveResponse(this.likeService.delete(like.id));
      }
    }
  }

  likeJoke(joke: IJoke): void {
    if (this.account !== null) {
      const newLike = this.newLike(joke, this.account.login);
      this.subscribeToSaveResponse(this.likeService.create(newLike));
    }
  }

  private alreadyLiked(joke: IJoke): boolean | undefined {
    return this.likes?.some(l => l.joke?.id === joke.id && l.accountId === this.account?.login);
  }

  private newLike(joke: IJoke, accountId: string): ILike {
    return {
      ...new Like(),
      liked: true,
      joke,
      accountId,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILike>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.loadAll();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  getLikesMessage(joke: IJoke): string {
    const likes = this.likes?.filter(l => l.joke?.id === joke.id).length;
    switch (likes) {
      case 0: {
        return 'Be the first who likes this joke.';
      }
      case 1: {
        return '1 like';
      }
      default: {
        return likes + ' likes';
      }
    }
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateJokes(data: IJoke[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.jokes.push(data[i]);
      }
    }
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
    if (this.jokesSubscriber) {
      this.eventManager.destroy(this.jokesSubscriber);
    }
    if (this.likesSubscriber) {
      this.eventManager.destroy(this.likesSubscriber);
    }
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJoke } from 'app/shared/model/joke.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JokeService } from './joke.service';
import { JokeDeleteDialogComponent } from './joke-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-joke',
  templateUrl: './joke.component.html',
})
export class JokeComponent implements OnInit, OnDestroy {
  jokes: IJoke[];
  eventSubscriber?: Subscription;
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
    private accountService: AccountService
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

  loadAll(): void {
    this.jokeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IJoke[]>) => this.paginateJokes(res.body, res.headers));
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

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJokes();
    this.authSubscription = this.accountService.getAuthenticationState().subscribe({
      next: account => (this.account = account),
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  trackId(index: number, item: IJoke): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJokes(): void {
    this.eventSubscriber = this.eventManager.subscribe('jokeListModification', () => this.reset());
  }

  delete(joke: IJoke): void {
    const modalRef = this.modalService.open(JokeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.joke = joke;
  }

  like(joke: IJoke): void {
    if (this.account?.login !== undefined) {
      // this.jokeService.like(joke, this.account?.login);
    }
  }

  likes(joke: IJoke): number | undefined {
    return joke.reactions?.filter(j => j.like === true).length;
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
}

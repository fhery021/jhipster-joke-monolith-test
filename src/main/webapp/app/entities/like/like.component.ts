import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILike } from 'app/shared/model/like.model';
import { LikeService } from './like.service';
import { LikeDeleteDialogComponent } from './like-delete-dialog.component';

@Component({
  selector: 'jhi-like',
  templateUrl: './like.component.html',
})
export class LikeComponent implements OnInit, OnDestroy {
  likes?: ILike[];
  eventSubscriber?: Subscription;

  constructor(protected likeService: LikeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.likeService.query().subscribe((res: HttpResponse<ILike[]>) => (this.likes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLikes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILike): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLikes(): void {
    this.eventSubscriber = this.eventManager.subscribe('likeListModification', () => this.loadAll());
  }

  delete(like: ILike): void {
    const modalRef = this.modalService.open(LikeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.like = like;
  }
}

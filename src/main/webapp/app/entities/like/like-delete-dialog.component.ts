import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILike } from 'app/shared/model/like.model';
import { LikeService } from './like.service';

@Component({
  templateUrl: './like-delete-dialog.component.html',
})
export class LikeDeleteDialogComponent {
  like?: ILike;

  constructor(protected likeService: LikeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.likeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('likeListModification');
      this.activeModal.close();
    });
  }
}

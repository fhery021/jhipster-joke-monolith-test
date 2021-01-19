import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJoke } from 'app/shared/model/joke.model';
import { JokeService } from './joke.service';

@Component({
  templateUrl: './joke-delete-dialog.component.html',
})
export class JokeDeleteDialogComponent {
  joke?: IJoke;

  constructor(protected jokeService: JokeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jokeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jokeListModification');
      this.activeModal.close();
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILike, Like } from 'app/shared/model/like.model';
import { LikeService } from './like.service';
import { IJoke } from 'app/shared/model/joke.model';
import { JokeService } from 'app/entities/joke/joke.service';

@Component({
  selector: 'jhi-like-update',
  templateUrl: './like-update.component.html',
})
export class LikeUpdateComponent implements OnInit {
  isSaving = false;
  jokes: IJoke[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    joke: [],
  });

  constructor(
    protected likeService: LikeService,
    protected jokeService: JokeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ like }) => {
      this.updateForm(like);

      this.jokeService.query().subscribe((res: HttpResponse<IJoke[]>) => (this.jokes = res.body || []));
    });
  }

  updateForm(like: ILike): void {
    this.editForm.patchValue({
      id: like.id,
      userId: like.userId,
      joke: like.joke,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const like = this.createFromForm();
    if (like.id !== undefined) {
      this.subscribeToSaveResponse(this.likeService.update(like));
    } else {
      this.subscribeToSaveResponse(this.likeService.create(like));
    }
  }

  private createFromForm(): ILike {
    return {
      ...new Like(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      joke: this.editForm.get(['joke'])!.value,
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
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IJoke): any {
    return item.id;
  }
}

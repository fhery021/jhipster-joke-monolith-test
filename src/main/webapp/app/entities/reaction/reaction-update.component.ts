import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReaction, Reaction } from 'app/shared/model/reaction.model';
import { ReactionService } from './reaction.service';
import { IJoke } from 'app/shared/model/joke.model';
import { JokeService } from 'app/entities/joke/joke.service';

@Component({
  selector: 'jhi-reaction-update',
  templateUrl: './reaction-update.component.html',
})
export class ReactionUpdateComponent implements OnInit {
  isSaving = false;
  jokes: IJoke[] = [];

  editForm = this.fb.group({
    id: [],
    comment: [],
    like: [null, [Validators.required]],
    joke: [],
  });

  constructor(
    protected reactionService: ReactionService,
    protected jokeService: JokeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reaction }) => {
      this.updateForm(reaction);

      this.jokeService.query().subscribe((res: HttpResponse<IJoke[]>) => (this.jokes = res.body || []));
    });
  }

  updateForm(reaction: IReaction): void {
    this.editForm.patchValue({
      id: reaction.id,
      comment: reaction.comment,
      like: reaction.like,
      joke: reaction.joke,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reaction = this.createFromForm();
    if (reaction.id !== undefined) {
      this.subscribeToSaveResponse(this.reactionService.update(reaction));
    } else {
      this.subscribeToSaveResponse(this.reactionService.create(reaction));
    }
  }

  private createFromForm(): IReaction {
    return {
      ...new Reaction(),
      id: this.editForm.get(['id'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      like: this.editForm.get(['like'])!.value,
      joke: this.editForm.get(['joke'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReaction>>): void {
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

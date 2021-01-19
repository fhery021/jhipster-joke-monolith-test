import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJoke, Joke } from 'app/shared/model/joke.model';
import { JokeService } from './joke.service';

@Component({
  selector: 'jhi-joke-update',
  templateUrl: './joke-update.component.html',
})
export class JokeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    text: [null, [Validators.required]],
  });

  constructor(protected jokeService: JokeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ joke }) => {
      this.updateForm(joke);
    });
  }

  updateForm(joke: IJoke): void {
    this.editForm.patchValue({
      id: joke.id,
      text: joke.text,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const joke = this.createFromForm();
    if (joke.id !== undefined) {
      this.subscribeToSaveResponse(this.jokeService.update(joke));
    } else {
      this.subscribeToSaveResponse(this.jokeService.create(joke));
    }
  }

  private createFromForm(): IJoke {
    return {
      ...new Joke(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJoke>>): void {
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
}

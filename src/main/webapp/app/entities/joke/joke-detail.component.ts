import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJoke } from 'app/shared/model/joke.model';

@Component({
  selector: 'jhi-joke-detail',
  templateUrl: './joke-detail.component.html',
})
export class JokeDetailComponent implements OnInit {
  joke: IJoke | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ joke }) => (this.joke = joke));
  }

  previousState(): void {
    window.history.back();
  }
}

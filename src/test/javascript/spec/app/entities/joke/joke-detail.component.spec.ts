import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JokeMonolithTestTestModule } from '../../../test.module';
import { JokeDetailComponent } from 'app/entities/joke/joke-detail.component';
import { Joke } from 'app/shared/model/joke.model';

describe('Component Tests', () => {
  describe('Joke Management Detail Component', () => {
    let comp: JokeDetailComponent;
    let fixture: ComponentFixture<JokeDetailComponent>;
    const route = ({ data: of({ joke: new Joke(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JokeMonolithTestTestModule],
        declarations: [JokeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JokeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JokeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load joke on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.joke).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

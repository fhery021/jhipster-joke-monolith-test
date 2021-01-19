import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JokeMonolithTestTestModule } from '../../../test.module';
import { JokeUpdateComponent } from 'app/entities/joke/joke-update.component';
import { JokeService } from 'app/entities/joke/joke.service';
import { Joke } from 'app/shared/model/joke.model';

describe('Component Tests', () => {
  describe('Joke Management Update Component', () => {
    let comp: JokeUpdateComponent;
    let fixture: ComponentFixture<JokeUpdateComponent>;
    let service: JokeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JokeMonolithTestTestModule],
        declarations: [JokeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JokeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JokeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JokeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Joke(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Joke();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

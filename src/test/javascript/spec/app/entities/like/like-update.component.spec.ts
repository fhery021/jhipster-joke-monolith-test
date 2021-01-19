import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JokeMonolithTestTestModule } from '../../../test.module';
import { LikeUpdateComponent } from 'app/entities/like/like-update.component';
import { LikeService } from 'app/entities/like/like.service';
import { Like } from 'app/shared/model/like.model';

describe('Component Tests', () => {
  describe('Like Management Update Component', () => {
    let comp: LikeUpdateComponent;
    let fixture: ComponentFixture<LikeUpdateComponent>;
    let service: LikeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JokeMonolithTestTestModule],
        declarations: [LikeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LikeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Like(123);
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
        const entity = new Like();
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

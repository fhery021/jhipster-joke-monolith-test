import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JokeMonolithTestTestModule } from '../../../test.module';
import { LikeComponent } from 'app/entities/like/like.component';
import { LikeService } from 'app/entities/like/like.service';
import { Like } from 'app/shared/model/like.model';

describe('Component Tests', () => {
  describe('Like Management Component', () => {
    let comp: LikeComponent;
    let fixture: ComponentFixture<LikeComponent>;
    let service: LikeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JokeMonolithTestTestModule],
        declarations: [LikeComponent],
      })
        .overrideTemplate(LikeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Like(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.likes && comp.likes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

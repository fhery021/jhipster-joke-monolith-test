import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JokeMonolithTestTestModule } from '../../../test.module';
import { LikeDetailComponent } from 'app/entities/like/like-detail.component';
import { Like } from 'app/shared/model/like.model';

describe('Component Tests', () => {
  describe('Like Management Detail Component', () => {
    let comp: LikeDetailComponent;
    let fixture: ComponentFixture<LikeDetailComponent>;
    const route = ({ data: of({ like: new Like(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JokeMonolithTestTestModule],
        declarations: [LikeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LikeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LikeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load like on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.like).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

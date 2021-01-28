import { ComponentFixture, TestBed, async } from '@angular/core/testing';

import { JokeMonolithTestTestModule } from '../../test.module';
import { HomeComponent } from 'app/home/home.component';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';

describe('Component Tests', () => {
  describe('Home Component', () => {
    let comp: HomeComponent;
    let fixture: ComponentFixture<HomeComponent>;
    let accountService: AccountService;
    let loginModalService: LoginModalService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [JokeMonolithTestTestModule],
        declarations: [HomeComponent],
      })
        .overrideTemplate(HomeComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(HomeComponent);
      comp = fixture.componentInstance;
      accountService = TestBed.get(AccountService);
      loginModalService = TestBed.get(LoginModalService);
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatUpdateDialogComponent } from './cat-update-dialog.component';

describe('CatUpdateDialogComponent', () => {
  let component: CatUpdateDialogComponent;
  let fixture: ComponentFixture<CatUpdateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CatUpdateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CatUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DogUpdateDialogComponent } from './dog-update-dialog.component';

describe('DogUpdateDialogComponent', () => {
  let component: DogUpdateDialogComponent;
  let fixture: ComponentFixture<DogUpdateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DogUpdateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DogUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

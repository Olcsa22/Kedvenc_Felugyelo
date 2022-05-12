import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedsDialogComponent } from './needs-dialog.component';

describe('DogDialogComponent', () => {
  let component: NeedsDialogComponent;
  let fixture: ComponentFixture<NeedsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NeedsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NeedsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HamsterUpdateDialogComponent } from './hamster-update-dialog.component';

describe('HamsterUpdateDialogComponent', () => {
  let component: HamsterUpdateDialogComponent;
  let fixture: ComponentFixture<HamsterUpdateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HamsterUpdateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HamsterUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

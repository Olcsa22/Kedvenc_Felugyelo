import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HamsterComponent } from './hamster.component';

describe('HamsterComponent', () => {
  let component: HamsterComponent;
  let fixture: ComponentFixture<HamsterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HamsterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HamsterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

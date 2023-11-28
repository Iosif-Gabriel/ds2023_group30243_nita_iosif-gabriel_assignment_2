import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceDetailsFormComponent } from './device-details-form.component';

describe('DeviceDetailsFormComponent', () => {
  let component: DeviceDetailsFormComponent;
  let fixture: ComponentFixture<DeviceDetailsFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeviceDetailsFormComponent]
    });
    fixture = TestBed.createComponent(DeviceDetailsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

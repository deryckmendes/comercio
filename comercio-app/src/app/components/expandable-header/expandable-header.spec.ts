import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpandableHeader } from './expandable-header';

describe('ExpandableHeader', () => {
  let component: ExpandableHeader;
  let fixture: ComponentFixture<ExpandableHeader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpandableHeader]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpandableHeader);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

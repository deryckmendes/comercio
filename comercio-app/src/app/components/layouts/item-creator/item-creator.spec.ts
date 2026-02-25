import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemCreator } from './item-creator';

describe('ItemCreator', () => {
  let component: ItemCreator;
  let fixture: ComponentFixture<ItemCreator>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemCreator]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemCreator);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

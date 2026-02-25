import { TestBed } from '@angular/core/testing';

import { ItemDelete } from '../item-delete';

describe('ItemDelete', () => {
  let service: ItemDelete;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemDelete);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { ItemDeleteService } from '../item-delete-service';

describe('ItemDeleteService', () => {
  let service: ItemDeleteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemDeleteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

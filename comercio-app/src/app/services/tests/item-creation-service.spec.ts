import { TestBed } from '@angular/core/testing';

import { ItemCreationService } from '../item-creation-service';

describe('ItemCreationService', () => {
  let service: ItemCreationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemCreationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { Itemcreator } from '../itemcreator';

describe('Itemcreator', () => {
  let service: Itemcreator;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Itemcreator);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { CategoryService } from './api/category';
import { SubCategoryService } from './api/sub-category';
import { ItemType } from '../types/item-type';

@Injectable({
  providedIn: 'root',
})
export class Itemcreator {
  private createSubject = new Subject<{
    type: ItemType;
    parentId?: string;
  }>();
  create$ = this.createSubject.asObservable();

  emitCreate(type: ItemType, parentId?: string) {
    this.createSubject.next({ type, parentId });
  }
}

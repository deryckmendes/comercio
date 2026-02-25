import { Injectable } from '@angular/core';
import { CategoryService } from './api/category';
import { SubCategoryService } from './api/sub-category';
import { ItemType } from '../types/item-type';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ItemDeleteService {
  constructor(
    private categoryService: CategoryService,
    private subCategoryService: SubCategoryService,
  ) {}

  delete(type: ItemType, id: string, parentId: string): Observable<any> {
    switch (type) {
      case 'category':
        return this.categoryService.delete(id);
      case 'subcategory':
        return this.subCategoryService.delete(id);
      case 'product':
        return this.subCategoryService.deleteProduct(parentId, id);
      default:
        throw new Error(`Unknown Type: ${type}`);
    }
  }
}

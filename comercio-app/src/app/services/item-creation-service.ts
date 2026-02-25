import { Injectable } from '@angular/core';
import { CategoryService } from './api/category';
import { SubCategoryService } from './api/sub-category';
import { Observable } from 'rxjs';
import { ItemType } from '../types/item-type';

@Injectable({
  providedIn: 'root',
})
export class ItemCreationService {
  constructor(
    private categoryService: CategoryService,
    private subCategoryService: SubCategoryService,
  ) {}

  create(type: ItemType, data: { parentId: string; name: string }): Observable<any> {
    switch (type) {
      case 'category':
        return this.categoryService.create(data);
      case 'subcategory':
        return this.subCategoryService.create({ categoryId: data.parentId, name: data.name });
      case 'product':
        return this.subCategoryService.createProduct(data.parentId, { name: data.name });
      default:
        throw new Error(`Unknown Type: ${type}`);
    }
  }
}

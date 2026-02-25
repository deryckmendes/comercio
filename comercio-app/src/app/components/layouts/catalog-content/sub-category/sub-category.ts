import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SubCategoryModel } from '../../../../services/api/models/sub-category';
import { Product } from '../product/product';
import { ActionItem, ExpandableHeader } from '../../../expandable-header/expandable-header';
import { SubCategoryService } from '../../../../services/api/sub-category';
import { Itemcreator } from '../../../../services/itemcreator';
import { ItemManagerService } from '../../../../services/item-manager-service';

@Component({
  selector: 'app-sub-category',
  imports: [Product, ExpandableHeader],
  templateUrl: './sub-category.html',
  styleUrl: './sub-category.css',
})
export class SubCategory {
  @Input() subCategory!: SubCategoryModel;
  @Output() delete = new EventEmitter<any>();

  constructor(
    private subCategoryService: SubCategoryService,
    private cdr: ChangeDetectorRef,
    private itemCreatorEvent: Itemcreator,
    private itemManager: ItemManagerService,
  ) {}

  toggle() {
    this.subCategory.expanded = !this.subCategory.expanded;
  }

  onCreate() {
    this.itemCreatorEvent.emitCreate('product', this.subCategory.id);
  }

  onDelete() {
    this.subCategoryService.delete(this.subCategory.id!).subscribe({
      next: () => {
        console.log('Sub-Categoria deletada!');
        this.delete.emit(this.subCategory.id);
      },
      error: (error) => {
        throw new Error(error);
      },
    });
  }

  onDeleteProduct(productId: string) {
    this.subCategoryService.deleteProduct(this.subCategory.id!, productId).subscribe({
      next: () => {
        this.subCategory.products = this.subCategory.products?.filter(
          (products) => products.id !== productId,
        );
        this.cdr.detectChanges();
      },
      error: (error) => {
        throw new Error(error);
      },
    });
  }

  handleAction(action: ActionItem) {
    if (action.type === 'create') {
      this.onCreate();
    } else if (action.type === 'delete') {
      this.onDelete();
    }
  }
}

import { Component, effect, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';

import { SubCategory } from '../sub-category/sub-category';
import { CategoryModel } from '../../../../services/api/models/category';
import { SubCategoryService } from '../../../../services/api/sub-category';
import { ActionItem, ExpandableHeader } from '../../../expandable-header/expandable-header';
import { Itemcreator } from '../../../../services/itemcreator';
import { ItemManagerService } from '../../../../services/item-manager-service';
import { SubCategoryModel } from '../../../../services/api/models/sub-category';

@Component({
  selector: 'app-category',
  imports: [SubCategory, ExpandableHeader],
  templateUrl: './category.html',
  styleUrl: './category.css',
})
export class Category implements OnInit {
  @Input() category!: CategoryModel;

  subCategories = signal<SubCategoryModel[] | null>(null);
  loading = signal(false);

  constructor(
    private subCategoryService: SubCategoryService,
    private itemCreatorEvent: Itemcreator,
    private itemManager: ItemManagerService,
  ) {
    effect(() => {
      this.itemManager.itemRefresh$.subscribe((categoryId) => {
        console.log('chamado?');

        if (categoryId === this.category.id) {
          this.loadSubCategories();
        }
      });
    });
  }

  ngOnInit(): void {
    if (this.category.expanded && !this.category.subCategories) {
      this.loadSubCategories();
    }
  }

  toggle() {
    this.category.expanded = !this.category.expanded;
    console.log(this.category.subCategories);

    if (!this.category.subCategories) {
      this.loadSubCategories();
    }
  }

  loadSubCategories() {
    this.loading.set(true);

    this.subCategoryService.getByCategoryId(this.category.id!).subscribe((subcategories) => {
      this.category.subCategories = subcategories;
      this.loading.set(false);
    });
  }

  onDeleteSubCategory(subCategoryId: string) {
    this.category.subCategories = this.category.subCategories?.filter(
      (subCategory) => subCategory.id !== subCategoryId,
    );
  }

  onCreate() {
    this.itemCreatorEvent.emitCreate('subcategory', this.category.id);
  }

  onDelete() {
    this.itemManager.delete('category', this.category.id!);
  }

  handleAction(action: ActionItem) {
    if (action.type === 'create') {
      this.onCreate();
    } else if (action.type === 'delete') {
      this.onDelete();
    }
  }
}

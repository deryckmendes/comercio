import { Component, Input, OnInit } from '@angular/core';

import { SubCategory } from '../sub-category/sub-category';
import { ActionButton, ActionType } from '../../../buttons/action-button/action-button';
import { CategoryService } from '../../../../services/api/category';
import { CategoryModel } from '../../../../services/api/models/category';
import { SubCategoryService } from '../../../../services/api/sub-category';
import { ActionItem, ExpandableHeader } from '../../../expandable-header/expandable-header';

@Component({
  selector: 'app-category',
  imports: [SubCategory, ActionButton, ExpandableHeader],
  templateUrl: './category.html',
  styleUrl: './category.css',
})
export class Category implements OnInit {
  @Input() category!: CategoryModel;

  constructor(private subCategoryService: SubCategoryService) {}

  ngOnInit(): void {
    if (!this.category.subCategories) {
      this.subCategoryService.getByCategoryId(this.category.id!).subscribe((subcategories) => {
        this.category.subCategories = subcategories;
      });
    }
  }

  toggle() {
    this.category.expanded = !this.category.expanded;
  }

  onCreate() {
    console.log("Created!");
    
  }

  onDelete() {
    console.log("Deleted!");
    
  }

  handleAction(action: ActionItem) {
    if (action.type === 'create') {
      this.onCreate();
    } else if (action.type === 'delete') {
      this.onDelete();
    }
  }
}

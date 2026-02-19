import { Component, Input, OnInit } from '@angular/core';
import { ActionButton } from '../../../buttons/action-button/action-button';
import { SubCategoryModel } from '../../../../services/api/models/sub-category';
import { Product } from '../product/product';
import { ProductService } from '../../../../services/api/product';

@Component({
  selector: 'app-sub-category',
  imports: [ActionButton, Product],
  templateUrl: './sub-category.html',
  styleUrl: './sub-category.css',
})
export class SubCategory implements OnInit {
  @Input() subCategory!: SubCategoryModel;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    if (!this.subCategory.products) {
      this.productService.getBySubCategoryId(this.subCategory.id!).subscribe((products) => {
        this.subCategory.products = products;
      });
    }
  }

  toggle() {
    this.subCategory.expanded = !this.subCategory.expanded;
  }

  onCreate() {}
}

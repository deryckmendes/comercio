import { Component, Input } from '@angular/core';
import { ProductModel } from '../../../../services/api/models/product';
import { ActionButton } from '../../../buttons/action-button/action-button';

@Component({
  selector: 'app-product',
  imports: [ActionButton],
  templateUrl: './product.html',
  styleUrl: './product.css',
})
export class Product {
  @Input() product!: ProductModel;

  onDelete() {}
}

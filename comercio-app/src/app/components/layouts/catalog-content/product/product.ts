import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProductModel } from '../../../../services/api/models/product';
import { ActionItem, ExpandableHeader } from '../../../expandable-header/expandable-header';

@Component({
  selector: 'app-product',
  imports: [ExpandableHeader],
  templateUrl: './product.html',
  styleUrl: './product.css',
})
export class Product {
  @Input() product!: ProductModel;
  @Output() delete = new EventEmitter<any>();

  handleAction(action: ActionItem) {
    if (action.type === 'create') {
      console.log('CREATE PRODUCT');
    } else if (action.type === 'delete') {
      this.delete.emit(this.product.id);
    }
  }
}

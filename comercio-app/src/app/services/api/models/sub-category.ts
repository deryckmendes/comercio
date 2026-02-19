import { ProductModel } from './product';

export class SubCategoryModel {
  id?: string;
  categoryId!: string;
  name!: string;
  expanded?: boolean;
  products?: ProductModel[];
}

import { SubCategoryModel } from './sub-category';

export class CategoryModel {
  id?: string;
  name!: string;
  expanded?: boolean;
  subCategories?: SubCategoryModel[];
}

import { ChangeDetectorRef, Component, Input, OnInit, signal, WritableSignal } from '@angular/core';
import { Category } from '../../../../shared/components/catalog/category/category';
import { LucideAngularModule, Plus, Search, X } from 'lucide-angular';
import { CategoryService } from 'app/services/category.service';
import { SubCategoryService } from 'app/services/sub-category.service';
import { ProductService } from 'app/services/product.service';
import { forkJoin } from 'rxjs';
import { CategoryModel } from 'app/models/category';
import { DeleteEvent } from 'app/models/delete-event';
import { CreateButton } from 'app/shared/components/action-buttons/create-button/create-button';
import { CreateEvent } from 'app/models/create-event';
import { CreateForm } from 'app/shared/components/create-form/create-form';

@Component({
  selector: 'app-manage',
  standalone: true,
  imports: [Category, LucideAngularModule, CreateButton, CreateForm],
  templateUrl: './manage.html',
  styleUrl: './manage.css',
})
export class Manage implements OnInit {
  readonly Search = Search;
  readonly Delete = X;

  showCreateForm: WritableSignal<boolean> = signal(false);
  currentCreateContext?: CreateEvent | null = null;
  selectedParentId?: string = '';
  selectedType?: string = '';

  categories: CategoryModel[] = [];

  constructor(
    private categoryService: CategoryService,
    private subCategoryService: SubCategoryService,
    private productService: ProductService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    forkJoin({
      categories: this.categoryService.getAll(),
      subcategories: this.subCategoryService.getAll(),
      products: this.productService.getAll(),
    }).subscribe(({ categories, subcategories, products }) => {
      this.categories = categories.map((category) => {
        const subCategories = subcategories
          .filter((sub) => sub.categoryId === category.id)
          .map((sub) => ({
            ...sub,
            expanded: false,
            products: products.filter((prod) => prod.subCategoryId === sub.id),
          }));
        return {
          ...category,
          expanded: false,
          subCategories,
        };
      });
      this.cdr.detectChanges();

    });
  }

  toggle(item: any) {
    item.expanded = !item.expanded;

  }

  toggleCreate(event?: CreateEvent) {
    this.currentCreateContext = event ?? { type: 'category', name: '' };
    this.selectedParentId = event?.parentId || '';
    this.selectedType = event?.type || 'category';
    this.showCreateForm.update((current) => !current);
  }

  save(formData: { name: string }) {
    if (!this.currentCreateContext) return;

    const fullEvent: CreateEvent = {
      ...this.currentCreateContext,
      name: formData.name,
    };

    this.create(fullEvent)
  }

  create(data: CreateEvent) {
    switch (data.type) {
      case 'category':
        this.categoryService.create({ name: data.name }).subscribe((created) => {
          this.categories.push({
            ...created,
            expanded: false,
            subCategories: [],
          });
        });
        break;

      case 'subcategory':
        if (!data.parentId) {
          throw new Error('parentId é obrigatório');
        }

        this.subCategoryService
          .create({
            categoryId: data.parentId,
            name: data.name,
          })
          .subscribe((created) => {
            this.categories = this.categories.map((category) =>
              category.id === data.parentId
                ? {
                    ...category,
                    subCategories: [
                      ...category.subCategories!,
                      { ...created, expanded: false, products: [] },
                    ],
                  }
                : category,
            );
          });
        break;

      case 'product':
        if (!data.parentId) {
          throw new Error('parentId é obrigatório');
        }

        this.productService
          .create({
            name: data.name,
            subCategoryId: data.parentId,
          })
          .subscribe((created) => {
            this.categories = this.categories.map((category) => ({
              ...category,
              subCategories: category.subCategories?.map((sub) =>
                sub.id === data.parentId
                  ? {
                      ...sub,
                      products: [...sub.products!, created],
                    }
                  : sub,
              ),
            }));
          });
        break;
    }

    this.showCreateForm.update((current) => !current);
    window.location.reload();
  }

  delete(event: DeleteEvent) {
    switch (event.type) {
      case 'category':
        this.categoryService.delete(event.id).subscribe(() => {
          this.categories = this.categories.filter((c) => c.id !== event.id);
        });
        break;
      case 'subCategory':
        this.subCategoryService.delete(event.id).subscribe(() => {
          this.categories = this.categories.map((category) => ({
            ...category,
            subCategories: category.subCategories?.filter((sub) => sub.id !== event.id),
          }));
        });
        break;
      case 'product':
        this.productService.delete(event.id).subscribe(() => {
          this.categories = this.categories.map((category) => ({
            ...category,
            subCategories: category.subCategories?.map((sub) => ({
              ...sub,
              products: sub.products?.filter((prod) => prod.id !== event.id),
            })),
          }));
        });
        break;
    }
    window.location.reload();
  }
}

// categories = [
//   {
//     id: 1,
//     name: 'Categoria A',
//     expanded: false,
//     subCategories: [
//       {
//         id: 1,
//         name: 'Sub-categoria A1',
//         expanded: false,
//         products: [
//           { id: 1, name: 'Terra' },
//           { id: 2, name: 'Lama' },
//         ],
//       },
//       {
//         id: 2,
//         name: 'Sub-categoria A2',
//         expanded: false,
//         products: [
//           { id: 1, name: 'Terra' },
//           { id: 2, name: 'Lama' },
//         ],
//       },
//       {
//         id: 3,
//         name: 'Sub-categoria A2',
//         expanded: false,
//         products: [
//           { id: 1, name: 'Terra' },
//           { id: 2, name: 'Lama' },
//         ],
//       },
//     ],
//   },
//   {
//     id: 2,
//     name: 'Categoria B',
//     expanded: false,
//     subCategories: [
//       {
//         id: 2,
//         name: 'Sub-Categoria B2',
//         expanded: false,
//         products: [
//           { id: 1, name: 'Tinta' },
//           { id: 2, name: 'Areia' },
//         ],
//       },
//       {
//         id: 1,
//         name: 'Sub-Categoria B1',
//         expanded: false,
//         products: [
//           { id: 1, name: 'Tinta' },
//           { id: 2, name: 'Areia' },
//         ],
//       },
//     ],
//   },
// ];

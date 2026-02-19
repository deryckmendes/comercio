import { ChangeDetectorRef, Component, OnInit, signal, WritableSignal } from '@angular/core';
import { ActionButton } from '../../../components/buttons/action-button/action-button';
import { Category } from '../../../components/layouts/catalog-content/category/category';
import { CategoryService } from '../../../services/api/category';
import { CategoryModel } from '../../../services/api/models/category';

@Component({
  selector: 'app-catalog',
  imports: [ActionButton, Category],
  templateUrl: './catalog.html',
  styleUrl: './catalog.css',
})
export class Catalog implements OnInit {
  createForm: WritableSignal<boolean> = signal(false);
  // currentCreateContext?: CreateEvent | null = null; é um model create-event, tem o delete-event tambem

  categories: CategoryModel[] = [];

  constructor(
    private categoryService: CategoryService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.categoryService.getAll().subscribe((categories) => {
      this.categories = categories.map((category) => ({
        ...category,
        expanded: false,
      }));
      console.log(this.categories);

      this.cdr.detectChanges();
    });
  }

  toggleCreate() {
    //evento pra abrir a tela de criar nova categoria
  }

  onCreate() {}
}

import { Injectable, signal } from '@angular/core';
import { CategoryModel } from './api/models/category';
import { Itemcreator } from './itemcreator';
import { ItemDelete } from './item-delete';
import { ItemCreationService } from './item-creation-service';
import { ItemDeleteService } from './item-delete-service';
import { CategoryService } from './api/category';
import { ItemType } from '../types/item-type';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ItemManagerService {
  private categories = signal<CategoryModel[]>([]);
  categories$ = this.categories.asReadonly();

  private currentCreateContext = signal<{ type: ItemType; parentId?: string } | null>(null);
  createContext$ = this.currentCreateContext.asReadonly();

  private openCreateSubject = new Subject<{ type: ItemType; parentId?: string; status: boolean }>();
  openCreateModal$ = this.openCreateSubject.asObservable();

  private itemRefreshSubject = new Subject<string>();
  itemRefresh$ = this.itemRefreshSubject.asObservable();

  constructor(
    private itemCreator: Itemcreator,
    private itemDelete: ItemDelete,
    private creationService: ItemCreationService,
    private deleteService: ItemDeleteService,
    private categoryService: CategoryService,
  ) {
    this.load();

    this.itemCreator.create$.subscribe(({ type, parentId }) => {
      this.currentCreateContext.set({ type, parentId });
      this.openCreateSubject.next({ type, parentId, status: true });
    });
  }

  load() {
    this.categoryService.getAll().subscribe((categories) => {
      this.categories.update((current) => {
        const currentCats = new Set(current.map((category) => category.id));
        const newCats = categories.filter((category) => !currentCats.has(category.id));

        return [...current, ...newCats];
      });
    });
  }

  create(type: ItemType, data: any) {
    this.openCreateSubject.next({ type, parentId: undefined, status: false });
    this.creationService.create(type, data).subscribe(() => {
      if (data.parentId) {
        this.itemRefreshSubject.next(data.parentId);
      } else {
        this.load();
      }
    });
  }

  delete(type: ItemType, id: string, parentId?: string) {
    this.deleteService.delete(type, id, parentId!).subscribe(() => this.load());
  }
}

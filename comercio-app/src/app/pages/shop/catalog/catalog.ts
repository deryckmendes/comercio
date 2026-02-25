import { ChangeDetectorRef, Component, OnInit, signal, WritableSignal } from '@angular/core';
import { ActionButton } from '../../../components/buttons/action-button/action-button';
import { Category } from '../../../components/layouts/catalog-content/category/category';
import { CategoryService } from '../../../services/api/category';
import { ItemCreator } from '../../../components/layouts/item-creator/item-creator';
import { Itemcreator } from '../../../services/itemcreator';
import { ItemCreationService } from '../../../services/item-creation-service';
import { ItemType } from '../../../types/item-type';
import { ItemDelete } from '../../../services/item-delete';
import { ItemDeleteService } from '../../../services/item-delete-service';
import { ItemManagerService } from '../../../services/item-manager-service';

@Component({
  selector: 'app-catalog',
  imports: [ActionButton, Category, ItemCreator],
  templateUrl: './catalog.html',
  styleUrl: './catalog.css',
})
export class Catalog implements OnInit {
  itemCreator: WritableSignal<boolean> = signal(false);
  itemCreatorType!: ItemType;
  itemCreatorParentId!: string | undefined;
  ItemDeleteId!: string;
  ItemDeleteParentId: string | undefined;

  categories;

  constructor(
    private categoryService: CategoryService,
    private cdr: ChangeDetectorRef,
    private itemCreatorEvent: Itemcreator,
    private itemCreationService: ItemCreationService,
    private itemDeleteEvent: ItemDelete,
    private itemDeleteService: ItemDeleteService,
    private itemManager: ItemManagerService,
  ) {
    this.categories = this.itemManager.categories$;
  }

  ngOnInit(): void {
    this.itemManager.openCreateModal$.subscribe(({ type, parentId, status }) => {
      this.itemCreatorType = type;
      this.itemCreatorParentId = parentId || undefined;
      this.itemCreator.set(status);
    });
  }

  toggleCreate() {
    this.itemCreator.update((current) => !current);
  }

  onItemCreate() {
    this.itemCreatorEvent.emitCreate('category');
  }

  onDelete(data: { id: string }) {
    const payload = {
      id: data.id,
      parentId: this.ItemDeleteId,
    };

  }
}

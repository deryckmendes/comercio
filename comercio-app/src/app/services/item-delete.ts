import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ItemType } from '../types/item-type';

@Injectable({
  providedIn: 'root',
})
export class ItemDelete {
  private deleteSubject = new Subject<{
    type: ItemType;
    id: string;
    parentId?: string
  }>();
  delete$ = this.deleteSubject.asObservable();

  emitDelete(type: ItemType, id: string, parentId?: string) {
    this.deleteSubject.next({ type, id, parentId });
  }
}

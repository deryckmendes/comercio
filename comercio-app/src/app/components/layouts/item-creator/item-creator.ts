import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CustomInput } from '../../custom-input/custom-input';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItemType } from '../../../types/item-type';
import { ItemManagerService } from '../../../services/item-manager-service';

@Component({
  selector: 'app-item-creator',
  imports: [CustomInput, ReactiveFormsModule],
  templateUrl: './item-creator.html',
  styleUrl: './item-creator.css',
})
export class ItemCreator {
  @Input() type!: ItemType;
  @Input() parentId?: String;
  @Output() cancel = new EventEmitter<any>();

  createForm!: FormGroup;

  constructor(private itemManager: ItemManagerService) {
    this.createForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    });
  }

  onSave() {
    if (this.createForm.invalid) return;

    const context = this.itemManager.createContext$();
    if (!context) return;

    const payload = {
      name: this.createForm.value.name,
      parentId: context.parentId,
    };

    this.itemManager.create(context.type, payload);
  }

  onCancel() {
    this.cancel.emit();
  }
}

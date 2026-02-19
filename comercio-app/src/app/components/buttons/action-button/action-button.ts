import { NgClass } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

export type ActionType = 'create' | 'delete';

@Component({
  selector: 'app-action-button',
  imports: [NgClass],
  templateUrl: './action-button.html',
  styleUrl: './action-button.css',
})
export class ActionButton {
  @Input() label: string = '';
  @Input() icon: string = '';
  @Input() type!: ActionType;
  @Output() action = new EventEmitter<any>();

  onClick(event: Event) {
    event.stopPropagation();
    this.action.emit();
  }
}

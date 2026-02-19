import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActionButton, ActionType } from "../buttons/action-button/action-button";

export interface ActionItem {
  label: string;
  icon?: string;
  type?: ActionType;
}

@Component({
  selector: 'app-expandable-header',
  templateUrl: './expandable-header.html',
  styleUrls: ['./expandable-header.css', '../layouts/catalog-content/shared-styles.css'],
  imports: [ActionButton],
})
export class ExpandableHeader {
  @Input() label!: string;
  @Input() expanded: boolean = false;
  @Input() actions!: ActionItem[];

  @Output() toggle = new EventEmitter<void>();
  @Output() action = new EventEmitter<ActionItem>();

  onToggle() {
    this.toggle.emit();
  }

  onAction(item: ActionItem, event: Event) {
    this.action.emit(item);
  }
}

import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-auth-buttons',
  imports: [],
  templateUrl: './auth-buttons.html',
  styleUrl: './auth-buttons.css',
})
export class AuthButtons {
  @Input() loginLabel: string = '';
  @Input() signupLabel: string = '';
  @Input() processing: boolean = false;
  @Input() signupMode: boolean = false;
  @Output() login = new EventEmitter<void>();
  @Output() signup = new EventEmitter<void>();

  onLogin() {
    this.login.emit();
  }

  onSignup() {
    this.signup.emit();
  }
}

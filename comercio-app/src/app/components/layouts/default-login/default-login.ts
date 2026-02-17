import { Component, EventEmitter, Input, Output, signal, WritableSignal } from '@angular/core';

@Component({
  selector: 'app-default-login',
  imports: [],
  templateUrl: './default-login.html',
  styleUrl: './default-login.css',
})
export class DefaultLogin {
  @Input() title: string = '';
  @Input() loginButton: string = '';
  @Input() signupButton: string = '';
  @Input() processing: boolean = false;
  @Output('submit') onSubmit = new EventEmitter();
  @Output('signup') onSignup = new EventEmitter();

  signupMode: WritableSignal<boolean> = signal(false);

  submit() {
    this.onSubmit.emit();
  }

  signup() {
    this.signupMode.update((current) => !current);
    this.onSignup.emit();
  }
}

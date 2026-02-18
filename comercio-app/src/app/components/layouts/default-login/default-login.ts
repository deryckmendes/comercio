import { Component, EventEmitter, Input, Output, signal, WritableSignal } from '@angular/core';
import { Branding } from './branding/branding';
import { FormHeader } from './form-header/form-header';
import { AuthButtons } from './auth-buttons/auth-buttons';

@Component({
  selector: 'app-default-login',
  imports: [Branding, FormHeader, AuthButtons],
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
    this.onSignup.emit();

    setTimeout(() => {
      this.signupMode.update((current) => !current);
    }, 200);
  }
}

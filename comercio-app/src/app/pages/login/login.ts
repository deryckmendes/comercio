import { Component, signal, WritableSignal } from '@angular/core';
import { DefaultLogin } from '../../components/layouts/default-login/default-login';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CustomInput } from '../../components/custom-input/custom-input';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',
  imports: [DefaultLogin, ReactiveFormsModule, CustomInput],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  loginForm!: FormGroup;
  signupMode: WritableSignal<boolean> = signal(false);

  toggle() {
    this.signupMode.update((current) => {
      const next = !current;

      if (next) {
        this.loginForm.addControl(
          'name',
          new FormControl('', [Validators.required, Validators.minLength(3)]),
        );
        this.loginForm.addControl(
          'confirmPassword',
          new FormControl('', [Validators.required, Validators.minLength(8)]),
        );
      } else {
        this.loginForm.removeControl('name');
        this.loginForm.removeControl('confirmPassword');
      }
      return next;
    });
  }

  // readonly User = CircleUserRound;
  // readonly Eye = Eye;
  // readonly Lock = Lock;

  constructor(private auth: Auth) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    });
  }
  submit() {
    this.auth.login(this.loginForm.value.email, this.loginForm.value.password).subscribe({
      next: () => console.log('Success'),
      error: () => console.log('Error'),
    });
  }
}

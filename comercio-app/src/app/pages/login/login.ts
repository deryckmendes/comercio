import { Component, inject, signal, WritableSignal } from '@angular/core';
import { DefaultLogin } from '../../components/layouts/default-login/default-login';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CustomInput } from '../../components/custom-input/custom-input';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [DefaultLogin, ReactiveFormsModule, CustomInput],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  router = inject(Router);

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
    if (this.loginForm.invalid) return;

    const { email, password } = this.loginForm.value;

    if (this.signupMode()) {
      this.auth.register(email, password).subscribe({
        next: () => this.router.navigate(['/shop/catalog']),
        error: () => {},
      });
    } else {
      this.auth.login(email, password).subscribe({
        next: () => this.router.navigate(['/shop/catalog']),
        error: () => {},
      });
    }
  }
}

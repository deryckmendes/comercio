import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../types/LoginResponse';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  constructor(private httpClient: HttpClient) {}
  
  login(email: string, password: string) {
    return this.httpClient.post<LoginResponse>('/api/auth', { email, password }).pipe(
      tap((value) => {
        sessionStorage.setItem('user-token', value.token);
        sessionStorage.setItem('user-name', value.name);
      }),
    );
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { catchError, map, Observable, of, tap } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private readonly authUrl = `${environment.authUrl}`;

  constructor(private httpClient: HttpClient) {}

  register(email: string, password: string): Observable<void> {
    return this.httpClient.post<void>(
      `${this.authUrl}/register`,
      { email, password, role: 'USER' },
      { withCredentials: true },
    );
  }

  login(email: string, password: string) {
    return this.httpClient.post<void>(
      `${this.authUrl}/login`,
      { email, password },
      { withCredentials: true },
    );
  }

  isAuthenticated(): Observable<boolean> {
    return this.httpClient.get(`${this.authUrl}/logged`, { withCredentials: true }).pipe(
      map(() => true),
      catchError(() => of(false)),
    );
  }

  refresh(): Observable<void> {
    return this.httpClient.post<void>(`${this.authUrl}/refresh`, {}, { withCredentials: true });
  }

  logout(): Observable<void> {
    return this.httpClient.post<void>(`${this.authUrl}/logout`, {}, { withCredentials: true });
  }
}

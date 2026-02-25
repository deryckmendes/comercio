import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../services/auth';
import { catchError, map, of, switchMap } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth);
  const router = inject(Router);

  console.log('Verificando...');

  return authService.isAuthenticated().pipe(
    map((authenticated) => {
      if (authenticated) return true;
      throw new Error('Not authenticated');
    }),
    catchError(() =>
      authService.refresh().pipe(
        switchMap(() => authService.isAuthenticated()),
        map((authenticated) => {
          if (authenticated) return true;
          router.navigate(['/login']);
          return false;
        }),
        catchError(() => {
          router.navigate(['/login']);
          return of(false);
        }),
      ),
    ),
  );
};

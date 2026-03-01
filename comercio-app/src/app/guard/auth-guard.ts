import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../services/auth';
import { catchError, map, of, switchMap } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth);
  const router = inject(Router);

  return authService.isAuthenticated().pipe(
    map((authenticated) => {
      if (!authenticated) {
        router.navigate(['/login']);
        return false;
      }
      return true;
    }),
  );
};

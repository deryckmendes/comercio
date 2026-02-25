import {
  HttpErrorResponse,
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { Auth } from './auth';
import { Router } from '@angular/router';
import { catchError, switchMap, throwError } from 'rxjs';

export const AuthInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn) => {
  const auth = inject(Auth);
  const router = inject(Router);

  if (req.url.includes('/auth/refresh')) {
    return next(req);
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.log('Chamado, erro: ', error);

      if (error.status === 0 || error.status === 403 || error.status === 401) {
        console.log('tentando renovar');

        return auth.refresh().pipe(
          switchMap(() => next(req)),
          catchError(() => {
            router.navigate(['/login']);
            return throwError(() => error);
          }),
        );
      }
      return throwError(() => error);
    }),
  );
};

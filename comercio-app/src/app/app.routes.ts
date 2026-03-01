import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Catalog } from './pages/shop/catalog/catalog';
import { Shop } from './pages/shop/shop';
import { authGuard } from './guard/auth-guard';
import { Panel } from './pages/shop/panel/panel';
import { loginGuard } from './guard/login-guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: Login,
    canActivate: [loginGuard],
  },
  {
    path: 'shop',
    component: Shop,
    canActivate: [authGuard],
    children: [
      {
        path: 'catalog',
        component: Catalog,
        data: { title: 'Catálogo', subtitle: 'Gerencie seus produtos' },
      },
      {
        path: 'dashboard',
        component: Panel,
        data: { title: 'Painel', subtitle: 'Painel principal' },
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];

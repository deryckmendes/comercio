import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Catalog } from './pages/shop/catalog/catalog';
import { Shop } from './pages/shop/shop';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'shop',
    component: Shop,
    children: [
      {
        path: 'catalog',
        component: Catalog,
        data: { title: 'Catálogo', subtitle: 'Gerencie seus produtos' },
      },
    ],
  },
];

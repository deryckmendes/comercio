import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  auth = inject(Auth);
  router = inject(Router);

  logout() {
    this.auth.logout().subscribe(() => {
      this.router.navigate(['/login']);
    });
  }
}

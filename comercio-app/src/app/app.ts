import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Auth } from './services/auth';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  protected readonly title = signal('comercio-app');
  auth = inject(Auth);
  router = inject(Router);

  ngOnInit(): void {
    // this.auth.isAuthenticated().subscribe((authenticated) => {
    //   if (authenticated) {
    //     this.router.navigate(['/shop/catalog']);
    //   } else {
    //     this.router.navigate(['/login']);
    //   }
    // });
  }
}

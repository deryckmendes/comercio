import { Component, Input, OnInit } from '@angular/core';
import { Sidebar } from '../../components/layouts/sidebar/sidebar';
import { PageHeader } from '../../components/layouts/page-header/page-header';
import { ActivatedRoute, NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter, map, mergeMap, startWith } from 'rxjs';

@Component({
  selector: 'app-shop',
  imports: [Sidebar, PageHeader, RouterOutlet],
  templateUrl: './shop.html',
  styleUrl: './shop.css',
})
export class Shop implements OnInit {
  title: string | undefined;
  subtitle: string | undefined;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  
  ngOnInit() {
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        startWith(null),
        map(() => {
          let currentRoute = this.route.root;
          console.log(currentRoute);
          while (currentRoute.firstChild) {
            currentRoute = currentRoute.firstChild;
          }
          return currentRoute;
        }),
        mergeMap((route) => route.data),
      )
      .subscribe((data) => {
        console.log('dados: ', data);
        this.title = data['title'];
        this.subtitle = data['subtitle'];
      });
  }
}

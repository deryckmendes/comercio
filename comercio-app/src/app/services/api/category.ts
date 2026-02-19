import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { CategoryModel } from './models/category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/categories`

  getAll(): Observable<CategoryModel[]> {
    return this.http.get<CategoryModel[]>(this.baseUrl);
  }

  create(category: CategoryModel): Observable<CategoryModel> {
    return this.http.post<CategoryModel>(this.baseUrl, category);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  } 
}

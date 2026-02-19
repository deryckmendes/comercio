import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { SubCategoryModel } from './models/sub-category';

@Injectable({
  providedIn: 'root',
})
export class SubCategoryService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/subcategories`;

  getAll(): Observable<SubCategoryModel[]> {
    return this.http.get<SubCategoryModel[]>(this.baseUrl);
  }

  getByCategoryId(categoryId: string): Observable<SubCategoryModel[]> {
    return this.http.get<SubCategoryModel[]>(`${this.baseUrl}/category/${categoryId}`);
  }

  create(subcategory: SubCategoryModel): Observable<SubCategoryModel> {
    return this.http.post<SubCategoryModel>(this.baseUrl, subcategory);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}

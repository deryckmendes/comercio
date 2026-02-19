import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { ProductModel } from './models/product';
import { SubCategoryModel } from './models/sub-category';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/products`;

  getAll(): Observable<ProductModel[]> {
    return this.http.get<ProductModel[]>(this.baseUrl);
  }

  getBySubCategoryId(subCategoryId: string): Observable<ProductModel[]> {
    return this.http.get<ProductModel[]>(`${this.baseUrl}/subcategory/${subCategoryId}`)
  }

  create(product: ProductModel): Observable<ProductModel> {
    return this.http.post<ProductModel>(this.baseUrl, product);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}

import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { SubCategoryModel } from './models/sub-category';
import { ProductModel } from './models/product';

@Injectable({
  providedIn: 'root',
})
export class SubCategoryService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.catalogUrl}/subcategories`;

  getAll(): Observable<SubCategoryModel[]> {
    return this.http.get<SubCategoryModel[]>(this.baseUrl, { withCredentials: true });
  }

  getByCategoryId(categoryId: string): Observable<SubCategoryModel[]> {
    return this.http.get<SubCategoryModel[]>(`${this.baseUrl}/category/${categoryId}`, {
      withCredentials: true,
    });
  }

  create(subcategory: SubCategoryModel): Observable<SubCategoryModel> {
    console.log("cat", subcategory);
    
    return this.http.post<SubCategoryModel>(this.baseUrl, subcategory, { withCredentials: true });
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  createProduct(subCategoryId: string, product: ProductModel): Observable<ProductModel> {
    product.description = "Base"
    product.price = 1
    product.quantity = 10
    return this.http.post<ProductModel>(`${this.baseUrl}/${subCategoryId}/products`, product, {
      withCredentials: true,
    });
  }

  deleteProduct(subCategoryId: string, productId: string): Observable<void> {
    console.log('SUB:> ', subCategoryId, ' >> prod:> ', productId);
    return this.http.delete<void>(`${this.baseUrl}/${subCategoryId}/products/${productId}`, {
      withCredentials: true,
    });
  }
}

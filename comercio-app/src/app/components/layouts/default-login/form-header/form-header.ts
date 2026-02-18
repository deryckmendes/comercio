import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-form-header',
  imports: [],
  templateUrl: './form-header.html',
  styleUrl: './form-header.css',
})
export class FormHeader {
  @Input() title: string = '';
  @Input() signupMode: boolean = false;
}

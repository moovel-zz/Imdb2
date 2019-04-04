import {Component, Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class BookService {
  paramAge: String="";
  paramCountry: String="";

  constructor(private http: HttpClient) {
  }
  getAllCountries(): Observable<any> {
    return this.http.get('//localhost:8088/allCountries');
  }
  getBooks(country, age, page, size, top): Observable<any> {
    country == '' ? this.paramCountry = 'empty' : this.paramCountry = country.toLowerCase();
    age == '' ? this.paramAge = 'empty' : this.paramAge = age;
    return this.http.get('//localhost:8088/books/' + this.paramCountry +'/'+ this.paramAge + '/' + page + '/' + size + '/' + top);
  }

  saveBookRating(bookRating: any): Observable<any>  {
    return this.http.post('//localhost:8088/bookRatings', bookRating);
  }
}

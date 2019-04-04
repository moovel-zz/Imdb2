import {Component, Directive, ElementRef, HostListener, OnInit} from '@angular/core';
import {BookService} from "../shared/book/book.service";
import {PageEvent} from "@angular/material";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  countries:Array<any>;
  userCountry: String="";
  userAge: String = "";
  topButtonRate: number =0;
  books: Array<any>;
  pageEvent: PageEvent;
  pageSize:number = 10;
  length:number= 100;
  size:number = 10;
  pageIndex:number = 0;
  loaded: boolean=false;

  constructor(private bookService: BookService) { }

  ngOnInit() {
    this.getCountries();
    this.getBooks();
  }

  getCountries() {
    this.bookService.getAllCountries().subscribe(data => {
      this.countries = data;
    }, (error)=>{
      console.log(error.error.message);
    });
  }

  selectCountry(value) {
    this.userCountry = value.countryName;
  }

  setAge(event) {
    let age;
    this.userAge = event.target.value;
  }

  submit($event) {
    $event.preventDefault();
    let re = /^[\s\d]+$/;
    if (this.userAge !=='' && !this.userAge.match(re)) {
      alert('User age can contain only numbers')
      return;
    }
    this.size = 10;
    this.pageIndex = 0;
    this.topButtonRate = 0;
    this.bookService.getBooks(this.userCountry, this.userAge,this.size - this.pageSize, this.pageSize, this.topButtonRate).subscribe(data => {
      this.books = data;
      if(data.length > 0 ) {
        this.length = data[0].totalCount;
      }
      else {
        this.length = 0;
      }
    }, (error)=>{
      console.log(error.error.message);
    });
  }

  getTopRatedBooks(num) {
    this.topButtonRate = num;
    this.size = 10;
    this.pageIndex = 0;
    this.getBooks();
  }

  getBooks() {
    this.bookService.getBooks(this.userCountry, this.userAge,this.size - this.pageSize, this.pageSize, this.topButtonRate).subscribe(data => {
      this.books = data;
      this.loaded=true;
      if (this.topButtonRate) {
        this.length = this.topButtonRate;
      } else {
        this.length = data[0].totalCount;
      }
    }, (error)=>{
      console.log(error.error.message);
    });
    this.loaded=false;
  }

  setPage(event?:PageEvent) {
    if(event.pageIndex === this.pageIndex + 1){
      this.size+=10;
    }
    else if(event.pageIndex === this.pageIndex - 1) {
      this.size-=10;
    }
    this.pageIndex = event.pageIndex;

    this.bookService.getBooks(this.userCountry, this.userAge, this.size - this.pageSize, this.pageSize, this.topButtonRate).subscribe(data => {
      this.loaded=true;
      this.books = data;
    }, (error)=>{
      console.log(error.error.message);
    });
    this.loaded=false;
    return event;
  }

  giveRating(value, isbn) {
    let bookRating = {userID: 278859, bookIsbn: isbn,
      bookRating: value};
    this.bookService.saveBookRating(bookRating).subscribe(response =>{
        this.getBooks()
      },
      (error)=>{
        console.log(error.error.message);
      }
    );
  }
}

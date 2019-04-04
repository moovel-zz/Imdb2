package com.marek.domain;


import javax.persistence.*;


@Entity
@Table(name="bx_book_ratings")
public class BookRating {

    @EmbeddedId
    UserISBN id;

    @Column(name = "book_rating")
    private Integer bookRating;

    public UserISBN getId() {
        return id;
    }

    public void setId(UserISBN id) {
        this.id = id;
    }

    public Integer getBookRating() {
        return bookRating;
    }

    public void setBookRating(Integer bookRating) {
        this.bookRating = bookRating;
    }

}
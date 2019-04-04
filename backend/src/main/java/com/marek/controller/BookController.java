package com.marek.controller;


import com.marek.dto.BookDTO;
import com.marek.dto.BookRatingDTO;
import com.marek.domain.*;
import com.marek.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private static final Logger log = LogManager.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping("/allCountries")
    public List<Country> getAllCountries() {
        log.info("getAllCountries");
        return bookService.getAllCountries();
    }

    @RequestMapping("/books/{country}/{age}/{page}/{size}/{top}")
    public List<BookDTO> getBooks(@PathVariable String country, @PathVariable String age, @PathVariable Integer page,
                                  @PathVariable Integer size, @PathVariable Integer top) {
        log.info("getBooks" + " country:" + country + " age:" + age + " page:" + page + " size:" + size + " top:" + top);
        return bookService.getBooks(country, age, page, size, top);
    }

    @PostMapping("bookRatings")
    public void saveBookRating(@RequestBody @Valid BookRatingDTO bookRatingDTO) {
        log.info("saveBookRating" + " bookRatingDTO:" + bookRatingDTO);
        bookService.saveBookRating(bookRatingDTO);
    }
}

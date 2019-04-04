package com.marek.service;

import com.marek.dto.BookDTO;
import com.marek.dto.BookRatingDTO;
import com.marek.dao.BookDao;
import com.marek.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Transactional
    public void saveBookRating(BookRatingDTO bookRatingDTO) {
        bookDao.saveBookRating(bookRatingDTO);
    }

    public List<Country> getAllCountries() {
       return bookDao.getAllCountries();
    }

    public List<BookDTO> getBooks(String country, String age, Integer page, Integer size, Integer top) {
        return bookDao.getBooks(country, age, page, size, top);
    }

}

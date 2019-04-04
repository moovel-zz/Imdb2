package com.marek.dao;

import com.marek.dto.BookDTO;
import com.marek.dto.BookRatingDTO;
import com.marek.domain.Country;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao {

    void saveBookRating(BookRatingDTO bookRatingDTO);

    List<Country> getAllCountries();

    List<BookDTO> getBooks(String country, String age, Integer page, Integer size, Integer top);

}

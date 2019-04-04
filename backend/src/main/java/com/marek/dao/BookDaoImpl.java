package com.marek.dao;


import com.marek.dto.BookDTO;
import com.marek.dto.BookRatingDTO;
import com.marek.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

import static java.lang.Integer.parseInt;


@Repository
public class BookDaoImpl implements BookDao {

    private static final Logger log = LogManager.getLogger(BookDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public List<BookDTO> getBooks(String country, String age, Integer page, Integer size, Integer top) {
        log.info("getBooks" + " country:" + country + " age:" + age + " page:" + page + " size:" + size + " top:" + top);
        Session session = entityManager.unwrap(Session.class);
        String query = "SELECT rownum() as totalCount, ISBN, bookTitle, bookAuthor, yearOfPublication, publisher," +
                " imageURL, bookRating, userRating" +
                " FROM (select bx_books.isbn as ISBN, bx_books.book_title as bookTitle," +
                " bx_books.book_author as bookAuthor, bx_books.year_of_publication as yearOfPublication," +
                " bx_books.publisher as publisher, bx_books.image_url_m as imageURL," +
                " round(cast(sum(book_rating) AS DECIMAL)/count(book_rating), 2) as bookRating," +
                " (select book_rating from bx_book_ratings WHERE bx_book_ratings.isbn = bx_books.isbn and" +
                " bx_book_ratings.user_id =278859) as userRating" +
                " from bx_book_ratings " +
                " join bx_books" +
                " on bx_book_ratings.isbn=bx_books.isbn" +
                " join bx_users" +
                " on bx_book_ratings.user_id=bx_users.user_id";


        if ((country != null && !country.equals("empty")) || age != null && !age.equals("empty")) {
            query += " where ";
            boolean filterCountry = false;
            if (country != null && !country.equals("empty")) {
                query += " country=:country ";
                filterCountry = true;
            }

            if (age != null && !age.equals("empty")) {
                if (filterCountry) {
                    query += " and";
                }
                query += " age=:userAge ";
            }
        }

        query += " group by bx_books.isbn) group by isbn";
        if (top.equals(10) || top.equals(50) || top.equals(100)) {
            query += " order by bookRating desc";
        }
        NativeQuery sqlQuery = session.createNativeQuery(query);


        if (country != null && !country.equals("empty")) {
            sqlQuery = sqlQuery.setParameter("country",country);
        }

        if (age != null && !age.equals("empty")) {
            Integer userAge = parseInt(age);
            sqlQuery = sqlQuery.setParameter("userAge", userAge);
        }

        sqlQuery.addScalar("ISBN", StringType.INSTANCE);
        sqlQuery.addScalar("bookTitle", StringType.INSTANCE);
        sqlQuery.addScalar("bookAuthor", StringType.INSTANCE);
        sqlQuery.addScalar("yearOfPublication", IntegerType.INSTANCE);
        sqlQuery.addScalar("publisher", StringType.INSTANCE);
        sqlQuery.addScalar("imageURL", StringType.INSTANCE);
        sqlQuery.addScalar("bookRating", BigDecimalType.INSTANCE);
        sqlQuery.addScalar("totalCount", IntegerType.INSTANCE);
        sqlQuery.addScalar("userRating", IntegerType.INSTANCE);


        sqlQuery.setFirstResult(page);
        sqlQuery.setMaxResults(size);

        sqlQuery.setResultTransformer(Transformers.aliasToBean(BookDTO.class));
        return sqlQuery.getResultList();
    }


    public void saveBookRating(BookRatingDTO bookRatingDTO) {
        log.info("saveBookRating" + " bookRatingDTO:" + bookRatingDTO);
        Session session = entityManager.unwrap(Session.class);
        BookRatingDTO bookRatingDB = getBookRatingDTO(bookRatingDTO);
        BookRating bookRating = new BookRating();
        bookRating.setBookRating(bookRatingDTO.getBookRating());
        bookRating.setId(new UserISBN(bookRatingDTO.getUserID(), bookRatingDTO.getBookIsbn()));
        if (bookRatingDB != null) {
            session.merge(bookRating);
        } else {
            session.save(bookRating);
        }
    }

    private BookRatingDTO getBookRatingDTO(BookRatingDTO bookRatingDTO) {
        Session session = entityManager.unwrap(Session.class);
        NativeQuery query = session.createNativeQuery("SELECT user_id AS userID, isbn AS bookIsbn, " +
                "book_rating AS bookRating FROM bx_book_ratings WHERE user_id =:userID and isbn =:bookISBN");
        query.setParameter("userID", bookRatingDTO.getUserID());
        query.setParameter("bookISBN", bookRatingDTO.getBookIsbn());

        query.addScalar("bookIsbn", StringType.INSTANCE);
        query.addScalar("userID", IntegerType.INSTANCE);
        query.addScalar("bookRating", IntegerType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(BookRatingDTO.class));
        return (BookRatingDTO) query.stream().findFirst().orElse(null);
    }

    public List<Country> getAllCountries() {
        log.info("getAllCountries");
        Session session = entityManager.unwrap(Session.class);
        List<Country> countries = session.createNativeQuery("SELECT * FROM bx_countries")
                .addEntity(Country.class).getResultList();
        return countries;
    }
}

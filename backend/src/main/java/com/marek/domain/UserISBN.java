package com.marek.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserISBN implements Serializable {

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "isbn")
    private String ISBN;

    public UserISBN() {
    }

    public UserISBN(Integer userID, String ISBN) {
        this.userID = userID;
        this.ISBN = ISBN;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserISBN)) return false;
        UserISBN that = (UserISBN) o;
        return Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getISBN(), that.getISBN());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getISBN());
    }
}
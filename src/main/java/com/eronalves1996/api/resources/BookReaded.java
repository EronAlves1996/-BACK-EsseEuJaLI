package com.eronalves1996.api.resources;

public class BookReaded {
    private String user;
    private String book_id;
    private String categorie;
    
    
    public BookReaded(String user, String book_id, String categorie) {
        super();
        this.user = user;
        this.book_id = book_id;
        this.categorie = categorie;
    }
    
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getBook_id() {
        return book_id;
    }
    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    
    
}

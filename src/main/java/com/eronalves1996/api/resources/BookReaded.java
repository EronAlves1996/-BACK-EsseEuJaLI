package com.eronalves1996.api.resources;

public class BookReaded {
    private String user;
    private String book_isbn;
    private String categorie;
    private int related_points;
    
    
    public BookReaded(String user, String book_isbn, String categorie, int related_points) {
        super();
        this.user = user;
        this.book_isbn = book_isbn;
        this.categorie = categorie;
        this.setRelated_points(related_points);
        
    }
    
    public BookReaded() {
        
    }
    
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getBook_isbn() {
        return book_isbn;
    }
    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }
    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getRelated_points() {
        return related_points;
    }

    public void setRelated_points(int related_points) {
        this.related_points = related_points;
    }
    
    
}

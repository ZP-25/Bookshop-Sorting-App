/**
 * This NonFiction class represents a NonFiction book with a title, author, and genre.
 * It extends the Book class, because a NonFiction book is a type of book and has a title and author like all Book objects.
 * This class contains methods to access the genre of the NonFiction book.
 */
 
public class NonFiction extends Book{ //is also a subclass of the superclass Book
    private String genre;
    
    /**
     * This constructor constructs a NonFiction object with a title and author, and genre.
     * 
     * @param title     The title of the NonFiction book
     * @param author    The author of the NonFiction book
     * @param genre     The genre of the NonFiction book
     */
    public NonFiction(String title, String author, String genre){
        super(title, author);
        this.genre = genre;
    }
    /** 
     * This method returns the genre name of the given NonFiction object.
     * Pre-condition: The NonFiction object must be properly initialized.
     * Post-condition: The genre name of the NonFiction object is accessed.
     * 
     * @return     The genre of the NonFiction object is returned as a String.
     */
    public String getGenre(){
        return genre;
    }
}
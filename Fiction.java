/**
 * This Fiction class represents a Fiction book with a title, author, genre, and isStandalone boolean.
 * It extends the Book class, because a Fiction book is a type of book and has a title and author like all Book objects.
 * This class contains methods to access the genre of the Fiction book and the boolean of whether or not it is a standalone.
 */
 
public class Fiction extends Book{ //is a subclass of the superclass Book
    private String genre;
    private boolean isStandalone;
    
    /**
     * This constructor constructs a Fiction object with a title and author, and genre and isStandalone boolean.
     * 
     * @param title     The title of the Fiction book
     * @param author    The author of the Fiction book
     * @param genre     The genre of the Fiction book
     * @param isStandalone     The isStandalone variable of the Fiction book - if it is a standalone or not
     */
    public Fiction(String title, String author, String genre, boolean isStandalone){
        super(title, author);
        this.genre = genre;
        this.isStandalone = isStandalone;
    }
    
    /** 
     * This method returns the genre name of the given Fiction object.
     * Pre-condition: The Fiction object must be properly initialized.
     * Post-condition: The genre name of the Fiction object is accessed.
     * 
     * @return     The genre of the Fiction object is returned as a String.
     */
    public String getGenre(){
        return genre;
    }
    
    /** 
     * This method returns the isStandalone variable of the given Fiction object - it returns
     * whether or not the Fiction book object is a standalone or not.
     * Pre-condition: The Fiction object must be properly initialized.
     * Post-condition: The isStandalone variable of the Fiction object is accessed.
     * 
     * @return     The isStandalone variable of the Fiction object is returned as a String.
     */
    public boolean getIsStandalone(){
        return isStandalone;
    }
}
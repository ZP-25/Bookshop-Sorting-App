/**
 * This Book class represents a book with a title and author. 
 * It contains methods to access the title, author, and the author's last name.
 */


public class Book {
    private String title;
    private String author;

    /**
     * This constructor constructs a Book object with a title and author.
     * 
     * @param title     The title of the book
     * @param author    The author of the book
     */
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }
    
    /** 
     * This method returns the title of the given Book object.
     * Pre-condition: The Book object must be properly initialized.
     * Post-condition: The title of the Book object is accessed.
     * 
     * @return     The title of the Book object is returned as a String.
     */
    public String getTitle(){
        return title;
    }
    
    /** 
     * This method returns the author name of the given Book object.
     * Pre-condition: The Book object must be properly initialized.
     * Post-condition: The author name of the Book object is accessed.
     * 
     * @return     The author name of the Book object is returned as a String.
     */
    public String getAuthor(){
        return author;
    }
    
    /** 
     * This method returns the author's last name of the given Book object.
     * Pre-condition: The Book object must be properly initialized.
     * Post-condition: The author's last name of the Book object is accessed.
     * 
     * @return     The author's last name of the Book object is returned as a String.
     */
    public String getAuthorLastName(){
        String partsOfName[] = author.split(" "); 
        //split.() from this website https://www.geeksforgeeks.org/split-string-java-examples/
        return partsOfName[partsOfName.length-1];
    }
}
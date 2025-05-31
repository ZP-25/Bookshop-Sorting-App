//Bookshop Sorting App

/**
 * This app takes in a text file of a list of books, with their 
 * title, author, genre, and if they are standalone for fiction
 * books. This app outputs a sorted list with the books organized
 * by genre, with the Fiction category first, followed by the 
 * NonFiction. Within each category, the genres are listed by descending 
 * number of books in them. Within a genre, the books are listed by 
 * alphabetical order of the author's last name. 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class BookshopSorter //This is the main class of my Bookshop Sorting App
{
    //private instance variables. They are ArrayLists containing books, or containing genres (ArrayLists of books)
    
    private ArrayList<NonFiction> nonFictionBooks; //This ArrayList stores all the NonFiction books, read from the text file
    private ArrayList<Fiction> fictionBooks; //This ArrayList stores all the Fiction books, read from the text file
    
    //These two ArrayLists contain ArrayLists of genres - each item in these lists are the genres (which contain the books)
    private ArrayList<ArrayList<NonFiction>> nonFictionGenres;
    private ArrayList<ArrayList<Fiction>> fictionGenres;
    
    
    //These are the ArrayLists for each Fiction genre. 
    private ArrayList<Fiction> scienceFiction;
    private ArrayList<Fiction> fantasy;
    private ArrayList<Fiction> historicalFiction;
    private ArrayList<Fiction> horror;
    private ArrayList<Fiction> youngAdult;
    private ArrayList<Fiction> romance;
    private ArrayList<Fiction> mystery;
    private ArrayList<Fiction> literaryClassics;
    private ArrayList<Fiction> other; //This is the ArrayList containing Fiction books that are of another genre not listed above.

    //These are the ArrayLists for each NonFiction genre.
    private ArrayList<NonFiction> history;
    private ArrayList<NonFiction> science;
    private ArrayList<NonFiction> math;
    private ArrayList<NonFiction> philosophy;
    private ArrayList<NonFiction> socialScience;
    private ArrayList<NonFiction> art;
    private ArrayList<NonFiction> biography;
    private ArrayList<NonFiction> psychology;
    private ArrayList<NonFiction> miscellaneous; //This is the ArrayLists containing NonFiction books that are of another genre not listed above.

    /** 
     * This is the constructor for this class. It constructs a new BookshopSorter and initializes the instance variables and adds the genres to the two lists of genres.
     * Pre-condition: The ArrayLists are all declared as private instance variables.
     * Post-condition: All ArrayLists of books and genres are initialized. The ArrayLists for each genre are added to the lists of genres.
     */
    public BookshopSorter()
    {
        //Lists of books (unsorted - the books are simply placed into either category)
        nonFictionBooks = new ArrayList<>();
        fictionBooks = new ArrayList<>();
        //Lists of genres
        nonFictionGenres = new ArrayList<>();
        fictionGenres = new ArrayList<>();
        
        //initializing Fiction genres
        scienceFiction = new ArrayList<>();
        fictionGenres.add(scienceFiction);

        fantasy = new ArrayList<>();
        fictionGenres.add(fantasy);

        historicalFiction = new ArrayList<>();
        fictionGenres.add(historicalFiction);
        
        horror = new ArrayList<>();
        fictionGenres.add(horror);

        youngAdult = new ArrayList<>();
        fictionGenres.add(youngAdult);

        romance = new ArrayList<>();
        fictionGenres.add(romance);

        mystery = new ArrayList<>();
        fictionGenres.add(mystery);

        literaryClassics = new ArrayList<>();           
        fictionGenres.add(literaryClassics);

        other = new ArrayList<>();
        fictionGenres.add(other);


        //initializing NonFiction genres
        history = new ArrayList<>();
        nonFictionGenres.add(history);

        science = new ArrayList<>();
        nonFictionGenres.add(science);

        math = new ArrayList<>();
        nonFictionGenres.add(math);

        philosophy = new ArrayList<>();
        nonFictionGenres.add(philosophy);

        socialScience = new ArrayList<>();
        nonFictionGenres.add(socialScience);

        art = new ArrayList<>();
        nonFictionGenres.add(art);

        biography = new ArrayList<>();
        nonFictionGenres.add(biography);

        psychology = new ArrayList<>();
        nonFictionGenres.add(psychology);

        miscellaneous = new ArrayList<>();
        nonFictionGenres.add(miscellaneous);
    }
    
    /**
     * This method reads text data from a text file and places the books it reads into their correct fiction/nonfiction list.
     * Pre-condition: The file must exist at the specified file path and follow the format.
     * Post-condition: The text is read and books are converted to Fiction or NonFiction objects and added to their respective lists.
     * 
     * @throws  IOException if file cannot be read
     * @throws  FileNotFoundException if file doesn't exist at the specified location
     */
    public void readBooks() throws IOException{
        File file = new File("/Users/pangz/VS_cs_txt/Bookshop/BookList.txt");
        Scanner scan = new Scanner(file);
        
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            String[] partsOfLine = line.split(";"); //Splits the line at the semi-colon separators
            //https://www.w3schools.com/PYTHON/ref_string_split.asp
            
            //Set the attributes of the book to their respective variables and add the books to the correct Fiction/NonFiction category
            if (partsOfLine.length >=3){
                String title = partsOfLine[0].trim(); //.trim() removes the whitespace from parts of a string
                //https://www.w3schools.com/jsref/jsref_trim_string.asp
                String author = partsOfLine[1].trim();
                String genre = partsOfLine[2].trim();
                
                if (partsOfLine.length == 4){ //A Fiction book has 4 parts (because of isStandalone) 
                    boolean isStandalone = Boolean.parseBoolean(partsOfLine[3].trim());
                    //https://www.geeksforgeeks.org/boolean-parseboolean-method-in-java-with-examples/
                    fictionBooks.add(new Fiction(title, author, genre, isStandalone));
                }
                else //A NonFiction book has 3 parts
                {
                    nonFictionBooks.add(new NonFiction(title, author, genre));
                }
            }
        }
        scan.close();
    }
    
    /**
     * This method serves to call on the methods to distribute the books into their genres, to sort the books in the genres by alphabetical last name, and to sort the genres in each category by size (number of books in them).
     * Pre-condition: The books must have been distributed into the correct Fiction/Nonfiction categories.
     * Post-condition: All books are distributed to the genres and sorted. The genres themselves are also sorted.
     */
    public void sortBooks()
    {
        distributeFictionBooks(fictionBooks, 0);
        distributeNonFictionBooks(nonFictionBooks, 0);

        sortGenresFiction(fictionGenres, 0);
        sortGenresNonFiction(nonFictionGenres, 0);
        
        sortGenreListsFiction(fictionGenres);
        sortGenreListsNonFiction(nonFictionGenres);
    }
    
    /** 
     * This method distributes Fiction books into their respective genre lists recursively.
     * Pre-condition: the fictionBooks list must be filled with books
     * Post-condition: All Fiction books are distributed to the correct genre lists, based on their genre.
     * 
     * @param fictionBooks      List containing all Fiction books that need to be distributed.
     * @param index     Current recursion index.
     */
    public void distributeFictionBooks(ArrayList<Fiction> fictionBooks, int index){
        //Base case for recursion
        if (index == fictionBooks.size())
        {
            return;
        }
        
        //Check the genre of the book at the current index and add it to the correct genre.
        if(fictionBooks.get(index).getGenre().equals("Science Fiction")){
            scienceFiction.add(fictionBooks.get(index));
        }
        else if(fictionBooks.get(index).getGenre().equals("Fantasy")){
            fantasy.add(fictionBooks.get(index));
        }
        else if(fictionBooks.get(index).getGenre().equals("Historical Fiction")){
            historicalFiction.add(fictionBooks.get(index));
        }
        else if(fictionBooks.get(index).getGenre().equals("Horror")){
            horror.add(fictionBooks.get(index));
        }
        else if(fictionBooks.get(index).getGenre().equals("Young Adult")){
            youngAdult.add(fictionBooks.get(index));
        }
        else if(fictionBooks.get(index).getGenre().equals("Romance")){
            romance.add(fictionBooks.get(index));
        }
        else if(fictionBooks.get(index).getGenre().equals("Mystery")){
            mystery.add(fictionBooks.get(index));
        }
        else if(fictionBooks.get(index).getGenre().equals("Literary Classics")){
            literaryClassics.add(fictionBooks.get(index));
        }
        else {
            other.add(fictionBooks.get(index));
        }
        
        //Recursive call, increases index by one.
        distributeFictionBooks(fictionBooks, index + 1);
    }
    
    /** 
     * This method distributes NonFiction books into their respective genre lists recursively.
     * Pre-condition: the nonFictionBooks list must be filled with books
     * Post-condition: All NonFiction books are distributed to the correct genre lists, based on their genre.
     * 
     * @param nonFictionBooks      List containing all NonFiction books that need to be distributed.
     * @param index     Current recursion index.
     */
    public void distributeNonFictionBooks(ArrayList<NonFiction> nonFictionBooks, int index){
        //Base case for recursion
        if (index == nonFictionBooks.size())
        {
            return;
        }
        
        //Check the genre of the book at the current index and add it to the correct genre.
        if(nonFictionBooks.get(index).getGenre().equals("History")){
            history.add(nonFictionBooks.get(index));
        }
        else if(nonFictionBooks.get(index).getGenre().equals("Science")){
            science.add(nonFictionBooks.get(index));
        }
        else if(nonFictionBooks.get(index).getGenre().equals("Math")){
            math.add(nonFictionBooks.get(index));
        }
        else if(nonFictionBooks.get(index).getGenre().equals("Philosophy")){
            philosophy.add(nonFictionBooks.get(index));
        }
        else if(nonFictionBooks.get(index).getGenre().equals("Social Science")){
            socialScience.add(nonFictionBooks.get(index));
        }
        else if(nonFictionBooks.get(index).getGenre().equals("Art")){
            art.add(nonFictionBooks.get(index));
        }
        else if(nonFictionBooks.get(index).getGenre().equals("Biography")){
            biography.add(nonFictionBooks.get(index));
        }
        else if(nonFictionBooks.get(index).getGenre().equals("Psychology")){
            psychology.add(nonFictionBooks.get(index));
        }
        else {
            miscellaneous.add(nonFictionBooks.get(index));
        }
        
        //Recursive call, increases index by one.
        distributeNonFictionBooks(nonFictionBooks, index + 1);
    }
    
    /**
     * This method sorts Fiction books alphabetically (by author's last name) in a genre, 
     * using Insertion Sort. This method also recursively goes   
     * through all the genres in an ArrayList of genres.
     * Pre-condition: Each Fiction genre ArrayList must contain books distributed into them.
     * Post-condition: All Fiction genre Arraylists are sorted alphabetically by the book's author's last name.
     * 
     * @param genreList     List containing all Fiction genres to sort
     * @param num           Current recursion index 
     */
    public void sortGenresFiction(ArrayList<ArrayList<Fiction>> genreList, int num)
    {
        //Base case for recursion 
        if (num >= genreList.size()){
            return;
        }
        
        ArrayList<Fiction> myGenre = genreList.get(num);
        //Sorts the books in the genre, using Insertion Sort
        for (int index = 1; index < myGenre.size(); index++)
        {
            Fiction currentBook = myGenre.get(index);
            int sortedIndex = index-1; //Sorted element is one less than current index
            
            //traverse sorted elements
            while (sortedIndex > -1 && myGenre.get(sortedIndex).getAuthorLastName().compareToIgnoreCase(currentBook.getAuthorLastName()) > 0)
            // https://www.geeksforgeeks.org/java-string-comparetoignorecase-method/
            {
                myGenre.set(sortedIndex+1, myGenre.get(sortedIndex)); //shift each element left of the current index by one
                sortedIndex--; //decrease sorted index
            }
            myGenre.set(sortedIndex+1, currentBook); //place current index after the index that we found to be smaller 
        }
        //Recursive call. The num index increases by one.
        sortGenresFiction(genreList, num+1);
    }
    
    /**
     * This method sorts NonFiction books alphabetically (by author's last name) in a genre, 
     * using Insertion Sort. This method also recursively goes   
     * through all the genres in an ArrayList of genres.
     * Pre-condition: Each NonFiction genre ArrayList must contain books distributed into them.
     * Post-condition: All NonFiction genre Arraylists are sorted alphabetically by the book's author's last name.
     * 
     * @param genreList     List containing all NonFiction genres to sort
     * @param num           Current recursion index 
     */
    public void sortGenresNonFiction(ArrayList<ArrayList<NonFiction>> genreList, int num)
    {
        //Base case for recursion 
        if (num >= genreList.size()){
            return;
        }
        ArrayList<NonFiction> myGenre = genreList.get(num);
        //Sorts the books in the genre, using Insertion Sort
        for (int index = 1; index < myGenre.size(); index++)
        {
            NonFiction currentBook = myGenre.get(index);
            int sortedIndex = index-1; //Sorted element is one less than current index

            //traverse sorted elements
            while (sortedIndex > -1 && myGenre.get(sortedIndex).getAuthorLastName().compareToIgnoreCase(currentBook.getAuthorLastName()) > 0)
            {
                myGenre.set(sortedIndex+1, myGenre.get(sortedIndex));//shift each element left of the current index by one
                sortedIndex--; //decrease sorted index
            }
            myGenre.set(sortedIndex+1, currentBook); //place current index after the index that we found to be smaller 
        }
        //Recursive call. The num index increases by one.
        sortGenresNonFiction(genreList, num+1);
    }
    
    /**
     * This method uses Insertion Sort to order all the Fiction genres by size. They are all in an ArrayList of Fiction genres, and will be sorted with the genres containing more books in the front of the list/have smaller indices.
     * Pre-condition: Genre lists must contain books distributed into them.
     * Post-condition: Fiction genres are ordered by descending book count.
     * 
     * @param genreList   List containing Fiction genres that needs to be sorted.
     */
    public void sortGenreListsFiction(ArrayList<ArrayList<Fiction>> genreList)
    {
        for (int index = 1; index < genreList.size(); index++)
        {
            ArrayList<Fiction> currentGenre = genreList.get(index);
            int currentIndexValue = currentGenre.size();
            int sortedIndex = index-1; //Sorted element is one less than current index
            int sortedIndexValue = genreList.get(sortedIndex).size();
            
            //traverse sorted elements
            while (sortedIndex > -1 && sortedIndexValue < currentIndexValue)
            {
                genreList.set(sortedIndex+1, genreList.get(sortedIndex)); //shift each element left of the current index by one
                sortedIndex--; //decrease sorted index
            }
            genreList.set(sortedIndex+1, currentGenre); //place current index after the index that we found to be smaller 
        }
    }
    
    /**
     * This method uses Insertion Sort to order all the NonFiction genres by size. They are all in an ArrayList of NonFiction genres, and will be sorted with the genres containing more books in the front of the list/have smaller indices.
     * Pre-condition: Genre lists must contain books distributed into them.
     * Post-condition: NonFiction genres are ordered by descending book count.
     * 
     * @param genreList   List containing NonFiction genres that needs to be sorted.
     */
    public void sortGenreListsNonFiction(ArrayList<ArrayList<NonFiction>> genreList)
    {
        for (int index = 1; index < genreList.size(); index++)
        {
            ArrayList<NonFiction> currentGenre = genreList.get(index);
            int currentIndexValue = currentGenre.size();
            int sortedIndex = index-1; //Sorted element is one less than current index
            int sortedIndexValue = genreList.get(sortedIndex).size();
                        
            //traverse sorted elements
            while (sortedIndex > -1 && sortedIndexValue < currentIndexValue)
            {
                genreList.set(sortedIndex+1, genreList.get(sortedIndex)); //shift each element left of the current index by one
                sortedIndex--; //decrease sorted index
            }
            genreList.set(sortedIndex+1, currentGenre); //place current index after the index that we found to be smaller 
        }
    }
    
    /**
     * This method writes the sorted books into a file to output.
     * Pre-condition: The lists and books are sorted.
     * Post-condition: An output file with the sorted books is generated in the specified path location.
     * 
     * @throws IOException  If the file cannot be written
     */
    public void writeBooks() throws IOException{
        FileWriter writer = new FileWriter("/Users/pangz/VS_cs_txt/Bookshop/BookListSORTED.txt");
        String fileContent = "";
        
        //Printing Fiction List
        fileContent += "Fiction List\n\n";
        int indexOfOtherGenre = -1;
        //Printing all genres other than Other genre
        for (int i = 0; i<fictionGenres.size(); i++)
        {
            if (fictionGenres.get(i).size()>0)
            {
                String genreName = fictionGenres.get(i).get(0).getGenre();
                if (genreName.equals("Science Fiction") || genreName.equals("Fantasy") || genreName.equals("Young Adult") || genreName.equals("Horror") || genreName.equals("Historical Fiction") || genreName.equals("Romance") || genreName.equals("Mystery") || genreName.equals("Literary Classics"))
                {
                    fileContent += genreName + "\n";
                    for (int j = 0; j<fictionGenres.get(i).size(); j++)
                    {
                        fileContent += fictionGenres.get(i).get(j).getTitle() +  "; " + fictionGenres.get(i).get(j).getAuthor() + "; ";
                        if (fictionGenres.get(i).get(j).getIsStandalone() == true) 
                        {
                            fileContent += "Is standalone\n";
                        }
                        else
                        {
                            fileContent += "Is not standalone\n";
                        }
                    }
                    fileContent += "\n";
                }
                else
                {
                    indexOfOtherGenre = i;
                }
            }
        }
        
        //Printing Other genre last
        if (fictionGenres.get(indexOfOtherGenre).size()>0)
        {
            fileContent += "Other\n";
            for (int j = 0; j<fictionGenres.get(indexOfOtherGenre).size(); j++)
            {
                fileContent += fictionGenres.get(indexOfOtherGenre).get(j).getTitle() +  "; " + fictionGenres.get(indexOfOtherGenre).get(j).getAuthor() + "; " + fictionGenres.get(indexOfOtherGenre).get(j).getGenre() + "; ";
                if (fictionGenres.get(indexOfOtherGenre).get(j).getIsStandalone() == true) 
                {
                    fileContent += "Is standalone\n";
                }
                else
                {
                    fileContent += "Is not standalone\n";
                }
            }
            fileContent += "\n";
        }
        
        //Printing NonFiction List
        fileContent+="\nNon-Fiction List\n";
        int indexOfMiscellaneousGenre = -1;
        //Printing all other genres other than Miscellaneous
        for (int i = 0; i<nonFictionGenres.size(); i++)
        {
            if (nonFictionGenres.get(i).size()>0)
            {
                String genreName = nonFictionGenres.get(i).get(0).getGenre();
                if (genreName.equals("History") || genreName.equals("Social Science") || genreName.equals("Philosophy") || genreName.equals("Science") || genreName.equals("Math") || genreName.equals("Biography") || genreName.equals("Psychology") || genreName.equals("Art"))
                {
                    fileContent += genreName + "\n";
                    for (int j = 0; j<nonFictionGenres.get(i).size(); j++)
                    {
                        fileContent += nonFictionGenres.get(i).get(j).getTitle() + "; " + nonFictionGenres.get(i).get(j).getAuthor() + "; " + nonFictionGenres.get(i).get(j).getAuthor() + "\n";
                    }
                }
                else
                {
                    indexOfMiscellaneousGenre = i;
                }
                fileContent += "\n";
            }
        }
        //Printing Miscellaneous Genre last
        if (nonFictionGenres.get(indexOfMiscellaneousGenre).size()>0)
        {
            fileContent += "Miscellaneous\n";
            for (int j = 0; j<nonFictionGenres.get(indexOfMiscellaneousGenre).size(); j++)
            {
                fileContent += nonFictionGenres.get(indexOfMiscellaneousGenre).get(j).getTitle() + "; " + nonFictionGenres.get(indexOfMiscellaneousGenre).get(j).getAuthor() + "; " + nonFictionGenres.get(indexOfMiscellaneousGenre).get(j).getGenre() + "\n";
            }
            fileContent += "\n";
        }
        
        //Write file and close writer
        writer.write(fileContent);
        writer.close();
    }
    
    /**
     * This is the Main Method - Executes the Bookshop Sorting App.
     * Pre-condition: The input file must exist in the specified path.
     * Post-condition: Sorted output file is generated.
     * 
     * @param args
     * @throws IOException      If file-reading fails
     */
    public static void main(String[] args) throws IOException
    {
        BookshopSorter app = new BookshopSorter();
        app.readBooks();
        app.sortBooks();
        app.writeBooks();
    }
}
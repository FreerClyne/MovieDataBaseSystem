package ui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import helperLibrary.StandardMessage;
import helperLibrary.ValidationProcessor;
import models.Movie;
import models.MovieList;

/**
 * MovieDatabase serves as a client class whose job is 
 * providing user interface for users, recieving command,
 * and transferring data between users and stored data.
 * 
 * @author Weinan 
 * @version 24-May-2017
 */

public class MovieDatabase
{
    private MovieList movieList;
    private Scanner console;
    
    private static final String FILE_NAME = "myvideos.txt";
    private static final int MOVIE_INFO_LENGTH = 6;
    private static final int MIN_RATING = 0;
    private static final int MAX_RATING = 10;
    private static final int ACTOR_NUMBER = 3;
    
    public MovieDatabase()
    {
        movieList = new MovieList();
        console = new Scanner(System.in);
    }
    
    /**
     * Receive data as a movie and add into
     * the movie list.
     */
    public void addMovie()
    {
        String[] movieDetail = null;
        String[] actor = new String[ACTOR_NUMBER];
        //actorNum records the number of actors has been input.
        int actorNum = 0;
        
        System.out.println("Enter the movie's detail in a sequence of (comma ',' is needed)");
        System.out.println("Title, Director, Actor1, Actor2, Actor3, Rating");
        movieDetail = console.nextLine().split(",");

        if (movieDetail.length != 6)
        {
            StandardMessage.errorMessage("detailLength");
            return;
        }

        //split data to a string array
        for (int i = 0; i < movieDetail.length; i++)
        {
            if (2 <= i && i <= 4)
            {
                actor[i-2] = movieDetail[i];
                if (!actor[i-2].isEmpty())
                    actorNum++;
            }
            else
            {
                if (ValidationProcessor.isEmpty(movieDetail[i]))
                {
                    StandardMessage.errorMessage("detailEmpty");
                    return;
                }
            }
        }
        
        if (movieList.movieExist(movieDetail[0]))
        {
            StandardMessage.errorMessage("movieExists");
            return;
        }
            
        if (actorNum == 0)
        {
            StandardMessage.errorMessage("actorNum");
            return;
        }

        if (!ValidationProcessor.isNumber(movieDetail[5]) || !ratingCheck(movieDetail[5]))
        {
            StandardMessage.errorMessage("rating");
            return;
        }

        Movie movie = new Movie(movieDetail[0], movieDetail[1], actor, Integer.parseInt(movieDetail[5].trim()));
        movieList.addMovie(movie);

        StandardMessage.successMessage("add");
    }
    
    /**
     * @return the movie has been selected by user 
     * from a list of title-matched movies.
     */
    public Movie chooseMovie()
    {
        String title = console.nextLine();
        title = title.trim();

        if (ValidationProcessor.isEmpty(title))
        {
            StandardMessage.errorMessage("empty");
            return null;
        }
        
        ArrayList<Movie> movies = movieList.getMoviesByTitle(title);

        if (movies.size() == 0)
        {
            return null;
        }

        System.out.print("Which one to choose [input number]: ");
        String num = console.nextLine();
        num = num.trim();

        if (ValidationProcessor.isEmpty(num) 
            || !ValidationProcessor.isNumber(num) 
            || !(Integer.parseInt(num) <= movies.size()))
        {
            return null; 
        }

        return movies.get(Integer.parseInt(num) - 1);
    }
    
    /**
     * Remove movie from the movie list.
     */
    public void deleteMovie()
    {
        System.out.print("Input title of the movie to be deleted: ");
        Movie target = chooseMovie();

        if (target == null)
        {
            StandardMessage.errorMessage("movie");
            return;
        }
        
        System.out.print("Sure to delete '" + target.getTitle() + "' (Y/N): ");
        String confirm = console.nextLine();
        confirm = confirm.trim().toLowerCase();

        if (confirm.equals("y"))
        {
            movieList.deleteMovie(target);
            StandardMessage.successMessage("delete");
        }
            
    }
    
    /**
     * Display favorite movies.
     */
    public void displayFavorite()
    {
        System.out.print("Input rating: ");
        String userInput = console.nextLine();
        userInput = userInput.trim();

        if (ValidationProcessor.isEmpty(userInput) 
            || !ValidationProcessor.isNumber(userInput) 
            || !ValidationProcessor.isRating(userInput))
        {
            StandardMessage.errorMessage("");
            return;
        }
        
        movieList.displayByRating(Integer.parseInt(userInput));
    }
    
    /**
     * Edit either acotos or the rating of a movie
     */
    public void editMovie()
    {
        System.out.print("Input title of the movie to be edited: ");
        Movie target = chooseMovie();

        if (target == null)
        {
            StandardMessage.errorMessage("movie");
            return;
        }
        
        target.display();
        System.out.print("edit actor or rating [a/r]: ");
        String choose = console.nextLine();
        choose = choose.trim().toLowerCase();

        if (ValidationProcessor.isEmpty(choose))
        {
            StandardMessage.errorMessage("empty");
            return;
        }
        
        if (choose.equals("a"))
        {
            editActor(target);
        }
        else if (choose.equals("r"))
        {
            editRating(target);
        }
        else
            StandardMessage.errorMessage("");
    }

    /**
     * Edit the rating of the selected movie
     */
    public void editRating(Movie target)
    {
        System.out.print("Enter new rating ["+ MIN_RATING + "-" + MAX_RATING + "]: ");
        String newRating = console.nextLine();
        newRating = newRating.trim();
        
        if (ValidationProcessor.isEmpty(newRating) 
            || !ValidationProcessor.isNumber(newRating) 
            || !ValidationProcessor.isRating(newRating))
        {
            StandardMessage.errorMessage("rating");
            return;
        }

        target.setRating(Integer.parseInt(newRating));
        StandardMessage.successMessage("update");
    }

    /**
     * Edit acotors of the selected movie
     */
    public void editActor(Movie target)
    {
        String[] actor = new String[ACTOR_NUMBER];
        int actorNum = 0;
        
        for (int i = 0; i < ACTOR_NUMBER; i++)
        {
            System.out.print("input actor " + (i + 1) +": ");
            actor[i] = console.nextLine();
            if (!ValidationProcessor.isEmpty(actor[i]))
                actorNum++;
        }
        
        if (actorNum == 0)
        {
            StandardMessage.errorMessage("actorNum");
            System.out.println("Need at least one actor!");
            return;
        }

        for (int i = 0; i < ACTOR_NUMBER; i++)
        {
            target.setActor(i, actor[i]);
        }

        StandardMessage.successMessage("update");
    }
    
    /**
     * Read movies from disk and stores them in a list.
     */
    public void initialMovieList() 
    {
        try
        {
            FileReader inputFile = new FileReader(FILE_NAME);
            Scanner parser = new Scanner(inputFile);
            String[] readList;
            String[] actor = new String[ACTOR_NUMBER];
            
            while (parser.hasNextLine())
            {
                readList = parser.nextLine().split(",");
                for (int i = 2; i < MOVIE_INFO_LENGTH - 1; i++)
                {
                    actor[i-2] = readList[i];
                }
                Movie movie = new Movie(readList[0], readList[1], actor, 
                                            Integer.parseInt(readList[5]));
                movieList.addMovie(movie);
            }
            
            parser.close();
            inputFile.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(FILE_NAME + " not found");
        }
        catch(IOException e)
        {
            System.out.println("Unexpected I/O error occured");
        } 
    }
    
    /**
     * Display menu for users.
     */
    public void menuDisplay()
    {
        System.out.println("\nWelcome to the Movie Database System");
        System.out.println("================================");
        System.out.println("(1) Search Movies");
        System.out.println("(2) Add Movie");
        System.out.println("(3) Delete Movie");
        System.out.println("(4) Edit Movie");
        System.out.println("(5) Display Favourite Movies");
        System.out.println("(6) Exit System");
        System.out.print("> ");
    }
    
    /**
     * @return the status of whether the input rating is
     * valid.
     */
    public boolean ratingCheck(String rating)
    {
        if (MIN_RATING <= Integer.parseInt(rating) && Integer.parseInt(rating) <= MAX_RATING)
            return true;
        else
            return false;
    }
    
    /**
     * Write all the movies with their details into the 
     * hard drive.
     */
    public void saveMovieList() 
    {
        try
        {
            PrintWriter outputFile = new PrintWriter("test.txt");
            String writeMovie = "";
            
            for (Movie m : movieList.getMovies())
            {
                writeMovie = m.getTitle() + ',' + m.getDirector() + ',' 
                                + m.getActor() + ',' + Integer.toString(m.getRating());
                outputFile.println(writeMovie);
            }
            
            outputFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unexpected I/O error occured");
        }
    }

    /**
     * Search the movie according to user's query condition. 
     */
    public void searchMovie()
    {
        System.out.print("Search by title or directory [t/d]: ");
        String userInput = console.nextLine();
        userInput = userInput.trim().toLowerCase();

        if (ValidationProcessor.isEmpty(userInput))
        {
            StandardMessage.errorMessage("empty");
            return;
        }
        
        if (userInput.equals("t"))
        {
            System.out.print("Enter the movie's title: ");
            userInput = console.nextLine();

            if (ValidationProcessor.isEmpty(userInput))
            {
                StandardMessage.errorMessage("empty");
                return;
            }

            movieList.getMoviesByTitle(userInput);
        }
        else if (userInput.equals("d"))
        {
            System.out.print("Enter the directors, seperated by ',': ");
            userInput = console.nextLine();

            if (ValidationProcessor.isEmpty(userInput))
            {
                StandardMessage.errorMessage("empty");
                return;
            }

            String[] directors = userInput.split(",");
            movieList.getMoviesByDirector(directors); 
        }
        else
            StandardMessage.errorMessage("");
    }


    /**
     * Activate each menu function
     */
    public void menuActivate(String userInput)
    {
        switch(Integer.parseInt(userInput))
        {
            case 1: searchMovie() ; break;
            case 2: addMovie(); break;
            case 3: deleteMovie(); break;
            case 4: editMovie(); break;
            case 5: displayFavorite(); break;
            case 6: ; break;
            default: StandardMessage.errorMessage("number");
        }
    }
    
    /**
     * Start the movie database system.
     */
    public void systemStart()
    {

        initialMovieList();
        String userInput;

        do
        {
            menuDisplay();
            userInput = console.nextLine();

            if (ValidationProcessor.isEmpty(userInput) || !ValidationProcessor.isNumber(userInput))
            {
                StandardMessage.errorMessage("");
                userInput = "0";
                continue;
            }

            menuActivate(userInput);
        } while (Integer.parseInt(userInput) != 6);
    }

    /**
     * Exit the movie database system.
     */
    public void systemExit() 
    {
        saveMovieList();
        StandardMessage.exitMessage();
    }
}

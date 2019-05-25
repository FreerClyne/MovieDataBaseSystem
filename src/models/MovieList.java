package models;

import java.util.ArrayList;

/**
 * The class stores a collection of movies and 
 * links all movie enities together as one unit. 
 * 
 * @author Weinan 
 * @version 24-May-2017
 */

public class MovieList
{
    private ArrayList<Movie> movies;
    
    public MovieList()
    {
        movies = new ArrayList<>();
    }
    
    /**
     * Add a movie to the movie list.
     */
    public void addMovie(Movie newMovie)
    {
        movies.add(newMovie);
    }
    
    /**
     * Delete a movie from the movie list.
     */
    public void deleteMovie(Movie target)
    {
        movies.remove(target);
    }
    
    /**
     * Display movies which meet the rating requirement.
     */
    public void displayByRating(int rating)
    {
        for (Movie m : movies)
            if (m.getRating() >= rating)
                m.display();
    }
    
    /**
     * Display all movies in the list.
     */
    public void displayList()
    {
        for (Movie m : movies)
        {
            m.display();
        }
    }
    
    /**
     * Display title-matched movies.
     * @return a list of title-matched movies.
     * @param title is the tile that movies need to match with.
     */
    public ArrayList<Movie> getMoviesByTitle(String title)
    {
        ArrayList<Movie> targets = new ArrayList<>();
        int i = 1;
        for (Movie m : movies)
        {
            if (m.getTitle().toUpperCase().contains(title.trim().toUpperCase()))
            {
                System.out.print(i + ". ");
                m.display();
                targets.add(m);
                i++;
            }
        }
        
        return targets;
    }
    
    public boolean movieExist(String title)
    {
        for (Movie m : movies)
        {
            if (m.getTitle().toUpperCase().equals(title.trim().toUpperCase()))
            {
                m.display();
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @return the movie list.
     */
    public ArrayList<Movie> getMovies()
    {
        return movies;
    }
    
    /**
     * Search movie which is filmed by the certain director.
     * @param director are directors need to be matched.
     */
    public void getMoviesByDirector(String[] director)
    {
        for (int i = 0; i < director.length; i++)
        {
            for (Movie m : movies)
            {
                if (m.getDirector().toUpperCase().contains(director[i].trim().toUpperCase()))
                {
                    m.display();
                }
            }
        }
    }
}

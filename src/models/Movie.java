package models;

import java.util.ArrayList;

/**
 * Movie class is the structure that stores every
 * relavent data of movies in the disk.
 * 
 * @author Weinan 
 * @version 24-May-2017
 */

public class Movie
{
    private String title;
    private String director;
    private ArrayList<Actor> actors;
    private int rating;
    
    private static final int MIN_RATING = 0;
    private static final int MAX_RATING = 10;
    
    public Movie()
    {
        title = "Unknown";
        director = "Unknown";
        actors = new ArrayList<>();
        rating = 0;
    }
    
    public Movie(String aTitle, String aDirector, String[] actorList, int aRating)
    {
       if (!aTitle.trim().isEmpty())
           title = aTitle;
       else
           title = "Unknown";
            
       if (!aDirector.trim().isEmpty())
           director = aDirector;
       else
           director = "Unknown";
            
       actors = new ArrayList<>();
        
       for (int i = 0; i < actorList.length; i++)
       {
           Actor actor = new Actor(actorList[i]);
           actors.add(actor);
       }
       
       if (MIN_RATING <= aRating && aRating <= MAX_RATING)
           rating = aRating;
       else
           rating = 0;
    }
    
    /**
     * Display all detials of the movie.
     */
     public void display()
    {
        System.out.println(getTitle() + ',' + getDirector() + ',' + getActor() + ',' + getRating());
    }
    
    /**
     * Set an actor.
     * @return setting status
     * @param newActor is the actor need to be set.
     */
    public boolean setActor(int index, String newActor)
    {
        if (0 <= index && index <= actors.size())
        {
            actors.get(index).setActor(newActor);
            return true;
        }
        else
            return false;
    }
    
    /**
     * Set a director.
     * @return setting status
     * @param newDirector is the director need to be set.
     */
    public boolean setDirector(String newDirector)
    {
        if (!newDirector.trim().isEmpty())
        {
            director = newDirector;
            return true;
        }
        else
            return false;
    }
    
    /**
     * Set a rating.
     * @return setting status
     * @param newrating is the actor need to be set.
     */
    public boolean setRating(int newRating)
    {
        if (MIN_RATING <= newRating && newRating <= MAX_RATING)
        {
            rating = newRating;
            return true;
        }
        else
            return false;
    }
    
    /**
     * Set a Title.
     * @return setting status
     * @param newTitle is the title need to be set.
     */
    public boolean setTitle(String newTitle)
    {
        if (!newTitle.trim().isEmpty())
        {
            title = newTitle;
            return true;
        }
        else
            return false;
    }
    
    /**
     * @return all actors in a single string.
     */
    public String getActor()
    {
        String actor = "";
        for (int i = 0; i < actors.size(); i++)
        {
            if (i == actors.size() - 1 )
                actor += actors.get(i).getActor();
            else
                actor += actors.get(i).getActor() + ',';
        }
        
        return actor;
    }
    
    /**
     * @return the director.
     */
    public String getDirector()
    {
        return director;
    }
    
    /**
     * @return the rating.
     */
    public int getRating()
    {
        return rating;
    }
    
    /**
     * @return the title.
     */
    public String getTitle()
    {
        return title;
    }
}

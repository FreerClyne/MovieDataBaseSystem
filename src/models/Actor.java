package models;

/**
 * This class is used for storing actor information
 * of each movies.
 * 
 * @author Weinan 
 * @version 24-May-2017
 */
public class Actor
{
    private String actor;
    
    public Actor()
    {
        actor = "";
    }
    
    public Actor(String aActor)
    {
        actor = aActor;
    }
    
    public void setActor(String newActor)
    {
        actor = newActor;
    }
    
    public String getActor()
    {
        return actor;
    }
}

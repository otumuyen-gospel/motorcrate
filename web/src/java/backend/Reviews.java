/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author user1
 */
public class Reviews{
    private int id;
    private String name;
    private String rating;
    private String review;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setRating(String rating){
        this.rating = rating;
    }
    public String getRating(){
        return rating;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setReview(String review){
        this.review = review;
    }
    public String getReview(){
        return review;
    }
    
    
}

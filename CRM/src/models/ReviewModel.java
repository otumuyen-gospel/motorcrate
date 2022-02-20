package models;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ReviewModel{
    private final StringProperty id =new SimpleStringProperty(this,"id",null);
    private final StringProperty name =new SimpleStringProperty(this,"name",null);
    private final StringProperty rating = new SimpleStringProperty(this,"rating",null);
    private final StringProperty review = new SimpleStringProperty(this,"review",null);
    public ReviewModel(String id , String name, String rating, String review){
        this.id.set(id);
        this.name.set(name);
        this.rating.set(rating);
        this.review.set(review);
    }
    public void setId(String id){
       idProperty().set(id);
    }
    public String getId(){
        return id.get();
    }
    public final StringProperty idProperty(){
        return id;
    }
    public void setName(String name){
       nameProperty().set(name);
    }
    public String getName(){
        return name.get();
    }
    public final StringProperty nameProperty(){
        return name;
    }

    public void setRating(String rating){
       ratingProperty().set(rating);
    }
    public String getRating(){
        return rating.get();
    }
    public final StringProperty ratingProperty(){
        return rating;
    }
    
    public void setReview(String review){
       reviewProperty().set(review);
    }
    public String getReview(){
        return review.get();
    }
    public final StringProperty reviewProperty(){
        return review;
    }

}
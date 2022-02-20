package models;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class AccountModel{
    private final StringProperty id =new SimpleStringProperty(this,"id",null);
    private final StringProperty firstName =new SimpleStringProperty(this,"firstName",null);
    private final StringProperty lastName = new SimpleStringProperty(this,"lastName",null);
    private final StringProperty email = new SimpleStringProperty(this,"email",null);
    private final StringProperty subscription = new SimpleStringProperty(this,"subscription",null);
    private final StringProperty shippingName = new SimpleStringProperty(this,"shipping Name",null);
    private final StringProperty country = new SimpleStringProperty(this,"country",null);
    private final StringProperty state = new SimpleStringProperty(this,"state",null);
    private final StringProperty city = new SimpleStringProperty(this,"city",null);
    private final StringProperty zip = new SimpleStringProperty(this,"zip code",null);
    private final StringProperty street = new SimpleStringProperty(this,"street",null);
    private final StringProperty landmark = new SimpleStringProperty(this,"landmark",null);
    private final StringProperty duration = new SimpleStringProperty(this,"duration",null);
    private final DoubleProperty cost = new SimpleDoubleProperty(this,"cost",0);
    
    public AccountModel(String id ,String firstName, String lastName, String email,String sub,
            String shippingName, String country,String state,String city,String zip,String street,
            String landmark, String duration, double cost){
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.email.set(email);
        this.subscription.set(sub);
        this.shippingName.set(shippingName);
        this.country.set(country);
        this.state.set(state);
        this.city.set(city);
        this.zip.set(zip);
        this.street.set(street);
        this.landmark.set(landmark);
        this.duration.set(duration);
        this.cost.set(cost);
        
    }
    public void setShippingName(String name){
       shippingNameProperty().set(name);
    }
    public String getShippingName(){
        return shippingName.get();
    }
    public final StringProperty shippingNameProperty(){
        return shippingName;
    }
    public void setCountry(String country){
       countryProperty().set(country);
    }
    public String getCountry(){
        return country.get();
    }
    public final StringProperty countryProperty(){
        return country;
    }
    public void setState(String state){
       stateProperty().set(state);
    }
    public String getState(){
        return state.get();
    }
    public final StringProperty stateProperty(){
        return state;
    }
    public void setCity(String city){
       cityProperty().set(city);
    }
    public String getCity(){
        return city.get();
    }
    public final StringProperty cityProperty(){
        return city;
    }
    public void setStreet(String street){
       streetProperty().set(street);
    }
    public String getStreet(){
        return street.get();
    }
    public final StringProperty streetProperty(){
        return street;
    }
    public void setZip(String zip){
       zipProperty().set(zip);
    }
    public String getZip(){
        return zip.get();
    }
    public final StringProperty zipProperty(){
        return zip;
    }
    public void setLandmark(String landmark){
       landmarkProperty().set(landmark);
    }
    public String getLandmark(){
        return landmark.get();
    }
    public final StringProperty landmarkProperty(){
        return landmark;
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
    public void setFirstName(String name){
       firstNameProperty().set(name);
    }
    public String getFirstName(){
        return firstName.get();
    }
    public final StringProperty firstNameProperty(){
        return firstName;
    }

    public void setLastName(String name){
       lastNameProperty().set(name);
    }
    public String getLastName(){
        return lastName.get();
    }
    public final StringProperty lastNameProperty(){
        return lastName;
    }
    
    public void setEmail(String email){
       emailProperty().set(email);
    }
    public String getEmail(){
        return email.get();
    }
    public final StringProperty emailProperty(){
        return email;
    }
    public void setSubscription(String sub){
       subscriptionProperty().set(sub);
    }
    public String getSubscription(){
        return subscription.get();
    }
    public final StringProperty subscriptionProperty(){
        return subscription;
    }
    
    public void setDuration(String duration){
       durationProperty().set(duration);
    }
    public String getDuration(){
        return duration.get();
    }
    public final StringProperty durationProperty(){
        return duration;
    }
    
    public void setCost(double cost){
       costProperty().set(cost);
    }
    public double getCost(){
        return cost.get();
    }
    public final DoubleProperty costProperty(){
        return cost;
    }
    


}
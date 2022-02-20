package models;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class FaqModel{
    private final StringProperty id =new SimpleStringProperty(this,"id",null);
    private final StringProperty question =new SimpleStringProperty(this,"question",null);
    private final StringProperty answer = new SimpleStringProperty(this,"answer",null);
    public FaqModel(String id , String question, String answer){
        this.id.set(id);
        this.answer.set(answer);
        this.question.set(question);
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
    public void setQuestion(String question){
       questionProperty().set(question);
    }
    public String getQuestion(){
        return question.get();
    }
    public final StringProperty questionProperty(){
        return question;
    }

    public void setAnswer(String answer){
       answerProperty().set(answer);
    }
    public String getAnswer(){
        return answer.get();
    }
    public final StringProperty answerProperty(){
        return answer;
    }

}
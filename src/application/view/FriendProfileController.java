package application.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.*;
import application.Controller;
import application.Main;
import application.model.Friend;
import application.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FriendProfileController   extends Controller{
	
	private String css="-fx-cursor: hand;"
			+ "	 -fx-background-color:linear-gradient(to right, #ff7e5f , #feb47b)";

    @FXML
    private Label txtFirstName;

    @FXML
    private Label txtLastName;

    @FXML
    private Label txtMajor;

    @FXML
    private Label txtBirthDate;

    @FXML
    private Label txtPhone;
    
    private Stage dialogStage;
    
    @FXML
    private AnchorPane pane;
    
    private User user;
    
    private MainController mainController;
    
    public boolean addFriend=true;
    
    Button b;
    
    public void setUser(User user,User friend) {
    	this.user=user;
    	txtFirstName.setText(user.getFirstName());
    	txtLastName.setText(user.getLastName());
    	txtMajor.setText(user.getMajor());
    	txtPhone.setText(user.getPhone());
    	txtBirthDate.setText(user.getBirthday());
    	
    	for(Friend f:db.findAllFriend()) {
   		 if(f.getPk_id()==user.getPk_id()&&f.getFk_user_id()==friend.getPk_id()) {
   			 addFriend=false;
   		 }
   	   }
    	if(addFriend) {
    		b=new Button("Add Friend");
    		pane.getChildren().add(b);
    	    b.setLayoutX(40);
    	    b.setLayoutY(180);
    	    b.setStyle(css);
    	    b.setPrefWidth(170);
    	    b.setOnAction((event)->{
    	    	mainController.addFrient(friend);
    	    	closeDialogStage();
    	    });
    	}else {
    		b=new Button("Unfriend");
    		pane.getChildren().add(b);
    	    b.setLayoutX(40);
    	    b.setLayoutY(180);
    	    b.setStyle(css);
    	    b.setPrefWidth(170);
    	    b.setOnAction((event)->{
    	    	if(db.deleteFriend(user.getPk_id(), friend.getPk_id())) {
    	    		alertMessage(true,"Naizaas amjilttai haslaa");
    	    		closeDialogStage();
    	    	}else {
    	    		alertMessage(false,"Aldaa garlaa dahiad oroldono uu");
    	    	}
    	    });
    	}
    	
    }
    
    public void setMainController(MainController mainController) {
    	this.mainController=mainController;
    }
    
    /**
     * Naiziin medeelliig haruulah tsonhiig  onoono
     * @param dialogStage = Hereglegchiin medeelliig haruulah tsonh
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void closeDialogStage() {
    	dialogStage.close();
    }

}

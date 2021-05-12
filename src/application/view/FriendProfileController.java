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
/**
 * Busad hereglegchiin delgerengvi medeelliig haruulah angi
 * @author enkherdene
 *
 */
public class FriendProfileController   extends Controller{
	
	private String css="-fx-cursor: hand;"
			+ "	 -fx-background-color:linear-gradient(to right, #ff7e5f , #feb47b)";

    @FXML
    private Label txtFirstName; //user-iin firstname-g awah label

    @FXML
    private Label txtLastName;//user-iin LastName-g awah label

    @FXML
    private Label txtMajor;//user-iin Major-g awah label

    @FXML
    private Label txtBirthDate;//user-iin BirthDate-g awah label

    @FXML
    private Label txtPhone;//user-iin Phone-g awah label
    
    private Stage dialogStage;
    
    @FXML
    private AnchorPane pane;
    
    /**
     * Login hiisen hereglegch
     */
    private User user;
    /**
     * Main Controller class-in object
     */
    private MainController mainController;
    /**
     * Herew tuhain user ni login hiisen hereglegchtei naiz bish bol true bolno
     */
    public boolean addFriend=true;
    /**
     * Naiz nemj eswel hasah button
     */
    Button b;
    /**
     * Delgerengvi medeelliig haruulah user-iin medeelliig label-uudad onoono
     * @param user = login hiisen hereglegch
     * @param friend = medeelliig ni harah user
     */
    public void setUser(User user,User friend) {
    	this.user=user;
    	//delgerengvi medeelliig ni haruulah user-iin medeelliig label-uudad onoono
    	txtFirstName.setText(user.getFirstName());
    	txtLastName.setText(user.getLastName());
    	txtMajor.setText(user.getMajor());
    	txtPhone.setText(user.getPhone());
    	txtBirthDate.setText(user.getBirthday());
    	
    	//login hiisen hereglegchtei naiz esehiig shalgana
    	for(Friend f:db.findAllFriend()) {
   		 if(f.getPk_id()==user.getPk_id()&&f.getFk_user_id()==friend.getPk_id()) {
   			 addFriend=false;
   		 }
   	   }
    	//herew naiz mon bol add friend vgvi bol unfriend button-g vvsgene
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
    /**
     * Main controller angiin objectiig onoono
     * @param mainController =  Main controller angiin object
     */
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
    /**
     * Tsonhiig haana
     */
    public void closeDialogStage() {
    	dialogStage.close();
    }

}

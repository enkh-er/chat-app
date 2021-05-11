package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Controller;
import application.model.*;

public class NewFriend  extends Controller{
	
	@FXML
    private Label lblName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtPort;
    
   private Stage dialogStage;
   
   private User user;
   
   private User friend;
    
    private MainController mainController;
    
    public void setMainController(MainController mainController) {
    	this.mainController=mainController;
    }
    
    /**
     * Hereglegchiin medeelliig haruulah tsonhiig  onoono
     * @param dialogStage = Hereglegchiin medeelliig haruulah tsonh
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Ajilchnii medeelliig haruulah tsonhiig haana
     */
    public void closeDialogStage(){
        dialogStage.close();
    }
    
    public void setUserIDfriendID(User u1, User f) {
    	user=u1;
    	friend=f;
    	lblName.setText(friend.getFirstName()+" "+friend.getLastName());
    }
    
    public boolean isValid() {
    	 String errorMessage = "";

         if (txtAddress.getText() == null || txtAddress.getText().length() == 0) {
             errorMessage += "IP address oruulna uu!\n";
         }
         else if (txtPort.getText() == null || txtPort.getText().length() == 0) {
             errorMessage += "Port-iin dugaaraa oruulna uu!\n";
         }
         if (errorMessage.length() == 0) {
             return true;
         } else {
             alertMessage(false,errorMessage);
             return false;
         }
    }
    
    public void handleNewFriend() {
    	if(isValid()) {
    		Friend f1=new Friend();
    		f1.setPk_id(user.getPk_id());
    		f1.setFk_user_id(friend.getPk_id());
    		f1.setIpAddress(txtAddress.getText());
    		f1.setPort(Integer.parseInt(txtPort.getText()));
    		if(db.saveFriend(f1)) {
    			alertMessage(true,"Amjilttai");
    			owner.getFriends().add(f1);
    			mainController.writeChat(f1,friend.getFirstName());
    			closeDialogStage();
    		}
    		else {
    			alertMessage(false,"Aldaa garlaa. Ta dahin oroldono uu!");
    		}
    		
    	}
    }
    
    @FXML
    void initialize() {
    	
    }

}

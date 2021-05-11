package application.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.Controller;
import application.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileEditController extends Controller{

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtMajor;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPhone;

    @FXML
    private DatePicker dpBirth;
    
    private User user;
    
    private Stage dialogStage;
    
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
    
    public void setUser(User user) {
    	this.user=user;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
    	txtFirstName.setText(user.getFirstName());
    	txtUserName.setText(user.getName());
    	txtLastName.setText(user.getLastName());
    	txtMajor.setText(user.getMajor());
    	txtPhone.setText(user.getPhone());
    	dpBirth.setValue(LocalDate.parse(user.getBirthday(),formatter));
    }
    
    private boolean isValid(){
    	  String errorMessage = "";

          if (txtUserName.getText() == null || txtUserName.getText().length() == 0) {
              errorMessage += "Newtreh neree oruulna uu!\n";
          }
          else if (txtFirstName.getText() == null || txtFirstName.getText().length() == 0) {
              errorMessage += "Neree oruulna uu!\n";
          }
          else if (txtLastName.getText() == null || txtLastName.getText().length() == 0) {
              errorMessage += "Owogoo oruulna uu!\n";
          }
          else if (dpBirth.getValue() == null ) {
              errorMessage += "Torson odoroo oruulna uu!\n";
          }
          if (errorMessage.length() == 0) {
              return true;
          } else {
              alertMessage(false,errorMessage);
              return false;
          }
    }
    
    public void updateProfile() {
    	User u=null;
    	if(isValid()) {
    			 u = new User();
    			 u.setPk_id(user.getPk_id());
                 u.setName(txtUserName.getText());
                 u.setPhone(txtPhone.getText());
                 u.setBirthday(dpBirth.getValue().toString());
                 u.setMajor(txtMajor.getText());
                 u.setFirstName(txtFirstName.getText());
                 u.setLastName(txtLastName.getText());
                 if(db.updateUser(u)) {
                 	alertMessage(true,"Amjilttai oorchilloo");
                 	mainController.setUser(u);
                 	closeDialogStage();
                 }else {
                 	alertMessage(false,"Aldaa garlaa. Ta dahin oroldono uu!");
                 }
    		
    	}
    	
    }

    @FXML
    void initialize() {
    	
    }
}

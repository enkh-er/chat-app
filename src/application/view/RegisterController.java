package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import application.Controller;
import application.model.User;

import java.io.IOException;

public class RegisterController extends Controller {

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

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPasswordRepeat;
    /**
     * @throws IOException
     */
    public void handleRegister() throws IOException{
            if(isValid()) {
                    User user = new User();
                    user.setName(txtUserName.getText());
                    user.setPhone(txtPhone.getText());
                    user.setBirthday(dpBirth.getValue().toString());
                    user.setMajor(txtMajor.getText());
                    user.setFirstName(txtFirstName.getText());
                    user.setLastName(txtLastName.getText());
                    user.setPassword(txtPassword.getText());
                    if(db.saveUser(user)) {
                    	alertMessage(true,"Amjilttai hadgallaa");
                    	owner.getUsers().add(user);
                    }else {
                    	alertMessage(false,"Aldaa garlaa. Ta dahin oroldono uu!");
                    }
            }
    }

    /**
     * Hereglegchiin medeelel zow esehiig shalgana
     * @return herew zow bol true, else false
     */
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
        else if (txtPassword.getText() == null || txtPassword.getText().length() <4) {
            errorMessage += "Nuuts vgee oruulna uu!\n";
        }
        else if(!txtPassword.getText().equals(txtPasswordRepeat.getText())){
                errorMessage += "Nuuts vgee zow oruulna uu!\n";
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

    public void handleBack(){
        parentStage.close();
        owner.initLogin();
    }
    @FXML
    void initialize() {
    }

}

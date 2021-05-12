package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import application.Controller;
import application.model.User;

import java.io.IOException;
/**
 * Login fxml-iin controllor class
 * @author enkherdene
 *
 */
public class LoginController extends Controller {

    @FXML
    private TextField txtName; //login hiisen user-iin newtreh ner

    @FXML
    private TextField txtPassword;//login hiisen user-iin password
    
    @FXML
    private TextField txtIp;//login hiisen user-iin ip address

    /**
     * Login button daragdah ved duudagdana
     * Hereglegchiin medeellig shalgaad zow bol programiin vndsen huudasruu shiljuulne
     */
    public void handleLogin(){
    	if(isValid()) {
    		User user=chechUser();
    		if(user==null) {
    			alertMessage(false,"Newtreh ner, eswel nuuts vg buruu baina");
    			return;
    		}
    		owner.initRootLayout(user,txtIp.getText());
    	}

    }
    /**
     * Hereglegchiin medeelel zow esehiig shalgana
     * @return herew zow bol true, else false
     */
    private boolean isValid(){
        String errorMessage = "";

        if (txtName.getText() == null || txtName.getText().length() == 0) {
            errorMessage += "Newtreh neree oruulna uu!\n";
        }
        else if (txtPassword.getText() == null || txtPassword.getText().length() == 0) {
            errorMessage += "Neree oruulna uu!\n";
        }
        else if (txtIp.getText() == null || txtIp.getText().length() == 0) {
            errorMessage += "IP hayagaa oruulna uu!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            alertMessage(false,errorMessage);
            return false;
        }
    }
    /**
     * Hereglegchiin medeellig ogogdliin sangaas shalgana
     * @return = zow bol true, buruu bol false
     */
    public User chechUser() {
    	User u=null;
    	for(User user:db.findAllUser()) {
    		if(user.getName().toLowerCase().equals(txtName.getText().toLowerCase())&&user.getPassword().equals(txtPassword.getText())) {
    			u=user;
    		}
    	}
    	return u;
    }
    /**
     * Register button der daragdah ved duudagdana
     * Register huudsiig duudna
     */
    public void handleRegister(){
            parentStage.close();
            owner.userRegister();
    }

    @FXML
    void initialize() {
    }
}
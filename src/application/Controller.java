package application;

import application.database.ChatDatabase;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Controller {
	/**
	 * Etseg stage-iig awna
	 */
    protected Stage parentStage;
    /**Programiin main class-g awna*/
    protected Main owner;
    /**Ugugdliin santai haritsah object*/
    protected ChatDatabase db;
    
    /**
     * Ugugdliin santai haritsah objectiig onoono
     * @param db = ogogdliin san
     */
    public void setDb(ChatDatabase db) {
    	this.db=db;
    }
    
    /**
     * Main объект үүсгэх метод
     * @param owner Main class-аас дамжуулах объект
     */
    public void setOwner(Main owner) { this.owner = owner; }

    /**
     * Эцэг stage утгыг оноож өгөх метод
     * @param parentStage эцэг буюу үүсгэсэн Stage
     */
    public void setParentStage(Stage parentStage) { this.parentStage = parentStage; }
    /**
     * Өгөгдсөн мессежийг харуулах метод
     * @param isSuccess амжилттай эсвэл алдаа гэдгийг илэрхийлэх
     * @param message илэрхийлэх мессеж
     */
    public static void alertMessage(boolean isSuccess, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if(isSuccess) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Амжилттай");
            alert.setHeaderText(message);
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Алдаа");
            alert.setHeaderText(message);
        }
        alert.showAndWait();
    }
}

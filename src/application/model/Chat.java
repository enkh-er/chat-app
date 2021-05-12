package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Hereglegchiin bichsen chatiig medeelliig awah class
 * @author enkherdene
 */
public class Chat {
	/**
	 * chatnii id
	 */
    private IntegerProperty pk_id;
    /**Chat bichsen user-iin id*/
    private IntegerProperty fk_user_id;
    /**Chat bichsen naiziin id*/
    private IntegerProperty fk_friend_id;
    /**2 hereglegchin Bichsen message*/
    private StringProperty message;
    /**Chat bichsen ognoo*/
    private StringProperty send_date;

    public Chat() {
        this.pk_id = new SimpleIntegerProperty(-1);
        this.fk_user_id = new SimpleIntegerProperty(-1);
        this.fk_friend_id = new SimpleIntegerProperty(-1) ;
        this.message = new SimpleStringProperty("");
        this.send_date = new SimpleStringProperty("");
    }

    public Chat(int pk_id, int fk_user_id, int fk_friend_id, String message, String send_date) {
        this.pk_id = new SimpleIntegerProperty(pk_id);
        this.fk_user_id = new SimpleIntegerProperty(fk_user_id);
        this.fk_friend_id = new SimpleIntegerProperty(fk_friend_id);
        this.message = new SimpleStringProperty(message) ;
        this.send_date = new SimpleStringProperty(send_date);
    }

    public int getPk_id() {
        return pk_id.get();
    }

    public IntegerProperty pk_idProperty() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id.set(pk_id);
    }

    public int getFk_user_id() {
        return fk_user_id.get();
    }

    public IntegerProperty fk_user_idProperty() {
        return fk_user_id;
    }

    public void setFk_user_id(int fk_user_id) {
        this.fk_user_id.set(fk_user_id);
    }

    public int getFk_friend_id() {
        return fk_friend_id.get();
    }

    public IntegerProperty fk_friend_idProperty() {
        return fk_friend_id;
    }

    public void setFk_friend_id(int fk_friend_id) {
        this.fk_friend_id.set(fk_friend_id);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getSend_date() {
        return send_date.get();
    }

    public StringProperty send_dateProperty() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date.set(send_date);
    }
}

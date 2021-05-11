package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Hereglegchiin naizuudiin medeellig aguulah class
 * @author  enkherdene
 */
public class Friend {
    private IntegerProperty pk_id;
    private IntegerProperty fk_user_id;
    private StringProperty ipAddress;
    private IntegerProperty port;

    public Friend() {
        this.pk_id = new SimpleIntegerProperty(-1);
        this.fk_user_id = new SimpleIntegerProperty(-1);
        this.ipAddress = new SimpleStringProperty("") ;
        this.port = new SimpleIntegerProperty(-1);
    }

    public Friend(int pk_id, int fk_user_id, String ipAddress, int port) {
        this.pk_id = new SimpleIntegerProperty(pk_id);
        this.fk_user_id = new SimpleIntegerProperty(fk_user_id);
        this.ipAddress = new SimpleStringProperty(ipAddress) ;
        this.port = new SimpleIntegerProperty(port);
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

    public String getIpAddress() {
        return ipAddress.get();
    }

    public StringProperty ipAddressProperty() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress.set(ipAddress);
    }

    public int getPort() {
        return port.get();
    }

    public IntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }
}

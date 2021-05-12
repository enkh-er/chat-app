package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import application.database.ChatDatabase;
import application.view.*;
import application.model.*;

import java.io.IOException;
/**
 * Programmiin vndsen main class
 * @author enkherdene
 *
 */
public class Main extends Application {

	/**Vndsen huudasnii controller*/
	private MainController controller;
	/**Vndsen stage*/
    private Stage primaryStage;
    /**Vndsen huudasnii aguulagch*/
    private BorderPane rootLayout;
    /**Ogogdliin sangiin medeelliig aguulah object*/
    private ChatDatabase db;
    /**Niit hereglegchiin medeelliig awna*/
    private ObservableList<User> users = FXCollections.observableArrayList();
    /**Niit naizuudiin medeelliig awna*/
    private ObservableList<Friend> friends = FXCollections.observableArrayList();

    /**
     * Constructor method
     * ogogdliin sang, vvsgej ogogdliin sangaas ogogdliig tataj awna
     */
    public Main(){
    	db=new ChatDatabase();
    	initData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Chat");
        //programiin vndsen stage-iig tohiruulna
        this.primaryStage=primaryStage;
        //program ehlehed login page-iig duudna
        initLogin();
    }

    /**
     * Login tsonhiig duudaj haruulna
     */
    public void initLogin() {
        try {
        	//login fxml duudna
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("./view/login.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            // Scene beldej bui heseg
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("User Login");
            // login -iin controller beldej bui heseg
            LoginController controller = loader.getController();
            controller.setOwner(this);
            controller.setParentStage(primaryStage);
            controller.setDb(db);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register tsonhiig duudaj haruulna
     */
    public void userRegister() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("./view/register.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            // Scene beldej bui heseg
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("User Register");
            //Register -iin controller beldej bui heseg
            RegisterController controller = loader.getController();
            controller.setOwner(this);
            controller.setParentStage(primaryStage);
            controller.setDb(db);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Hereglegch newtersnii daraa duudah programiin vndsen tsonh vvsgeh function
     */
    public void initRootLayout(User user,String ip) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("./view/main.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Scene beldej bui heseg
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle(user.getFirstName()+" "+user.getLastName());
            // Main fxml -iin controller beldej bui heseg
            controller = loader.getController();         
            controller.setIp(ip);
            controller.setUser(user);
            controller.setDb(db);
            controller.setOwner(this);    
            controller.setParentStage(primaryStage);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    /**
     * Hereglegchiin medeellig butsaah method
     * @return Hereglegchiin medeelel
     */
    public ObservableList<User> getUsers() {
		return users;
	}

    /**
     * Naiziin ogogdliig butsaah method
     * @return Naiziin medeelel
     */
	public ObservableList<Friend> getFriends() {
		return friends;
	}

	/**
	 * User bolon Friend-iin medeellig program ehlehed ogogdliin sangaas awna
	 */
    public void initData() {
        for (User item : db.findAllUser())
            users.add(item);
        for (Friend item : db.findAllFriend())
        	friends.add(item);
    }

	public static void main(String[] args) {
        launch(args);
    }
}

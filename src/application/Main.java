package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import application.database.ChatDatabase;
import application.view.*;
import application.model.*;

import java.io.IOException;

public class Main extends Application {

	private MainController controller;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ChatDatabase db;
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Friend> friends = FXCollections.observableArrayList();
    private ObservableList<Chat> chats = FXCollections.observableArrayList();

    public Main(){
    	db=new ChatDatabase();
    	initData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Chat");
        this.primaryStage=primaryStage;
        initLogin();
    }
    
    @Override
    public void stop() throws Exception{
    	
    }

    /**
     * Root Layout vvsgeh function
     */
    public void initLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("./view/login.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            // Scene beldej bui heseg
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("User Login");
            // Root Layout -iin controller beldej bui heseg
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
     * Root Layout vvsgeh function
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
            // Root Layout -iin controller beldej bui heseg
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
     * Root Layout vvsgeh function
     * @throws IOException
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
            // Root Layout -iin controller beldej bui heseg
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

    public ObservableList<User> getUsers() {
		return users;
	}

	public ObservableList<Friend> getFriends() {
		return friends;
	}

	public ObservableList<Chat> getChats() {
		return chats;
	}

    public void initData() {
        for (User item : db.findAllUser())
            users.add(item);
        for (Friend item : db.findAllFriend())
        	friends.add(item);
        for (Chat item : db.findAllChat())
        	chats.add(item);
    }

	public static void main(String[] args) {
        launch(args);
    }
}

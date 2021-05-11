package application.view;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.io.*;

import application.Controller;
import application.Main;
import application.model.*;
import javafx.util.*;
import javafx.event.*;
import java.net.*;
import java.time.LocalDate;
public class MainController extends Controller{

	@FXML
    private Label lblUserName;
	
	@FXML
    private Label lblFriendName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> colUserName;

    @FXML
    private TableColumn<User, Void> colBtn;
    
    @FXML
    private TableColumn<User, Void> friendProfile;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextArea txtMsg;
    
	private User user;
	
	private Friend chatFriend;
	
	private String friendName;
	
	private boolean send=false;
	
	private boolean running=false;
	
	private String message="";
	
	private String ipAdress="localhost";
	
	private ObservableList<User> users = FXCollections.observableArrayList();
	

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
   

	public User getUser() {
		return user;
	}
	
	public void setIp(String ipAdress) {
		this.ipAdress=ipAdress;
	}
	
	public void setUser(User user) {
		this.user = user;
		initMain();		
	}
	public void initMain() {
		lblUserName.setText(user.getFirstName()+" "+user.getLastName());
	}
	
	  /**
     * Хүснэгтэд өөрчлөх товчлуурыг нэмнэ
     */
    private void addButtonToTable() {

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                		 private final Button btn = new Button("Зурвас Бичих");
                         {
                             //Butten deer darah ved duudagdana. Tuhain mornii user-iin medeelliig 
                             btn.setOnAction((ActionEvent event) -> {
                            	 User friend= table.getItems().get(getIndex());
                            	 //naiz mon esehiig shalgana
                            	 for(Friend f:owner.getFriends()) {
                            		 if(f.getPk_id()==user.getPk_id()&&f.getFk_user_id()==friend.getPk_id()) {
                            			 User u=db.userFindById(f.getFk_user_id());
                            			 writeChat(f,u.getFirstName());
                            			 return;
                            		 }
                            	 }
                            	 //naiz bish bol naizaar nemneenh
                            	 addFrient(friend);
                             });
                         }
                   
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
    }
    
	  /**
     * Busad hereglegchidiin profile-ig haruulah buttong vvsgene
     */
    private void addFriendProBtn() {

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                		 private final Button btn = new Button("Профайл");
                         {
                             //Butten deer darah ved duudagdana. Tuhain mornii user-iin medeelliig 
                             btn.setOnAction((ActionEvent event) -> {
                            	 User friend= table.getItems().get(getIndex());
                            	 //naiz mon esehiig shalgana
                            	 setFriendPro(friend);
                             });
                         }
                   
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        friendProfile.setCellFactory(cellFactory);
    }
    
    /**
     * Songoson hereglegchiin medeelliig haruulna
     */
    public void setFriendPro(User f) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("friendProfile.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Friend information");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            FriendProfileController controller = loader.getController();
            controller.setDb(db);
            controller.setDialogStage(stage);
            controller.setMainController(this);
            controller.setOwner(owner);
            controller.setUser(user,f);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeChat(Friend f,String name) {
    	message="";
    	friendName=name;
    	lblFriendName.setText(friendName);
    	chatFriend=f;
    	setUserChats(f.getPk_id(),f.getFk_user_id());
    	send=true;
    }
    
    /**
     * Hereglegchdiin omno bichsen zurwasiig ogogdliin sangaas unshij textarea-d hiine
     * @param userID - hereglegchiin id
     * @param friendID = naiziin id
     */
    public void setUserChats(int userID, int friendID) {
    	  txtArea.setText("");
    	  for (Chat item : db.findByIdChat(userID, friendID)) {
    		  txtArea.appendText(item.getMessage() + "\n");
    	  }
	  }
    
    
    /**
     * Edit profile button der darah ved hereglegchiin medeelliig oorchloh tsonh garch irne
     */
    public void editProfile() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profileEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("User information");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            ProfileEditController controller = loader.getController();
            controller.setDb(db);
            controller.setDialogStage(stage);
            controller.setUser(user);
            controller.setOwner(owner);
            controller.setMainController(this);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Edit profile button der darah ved hereglegchiin medeelliig oorchloh tsonh garch irne
     */
    public void addFrient(User f) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newFriend.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Friend");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            NewFriend controller = loader.getController();
            controller.setDb(db);
            controller.setDialogStage(stage);
            controller.setUserIDfriendID(user,f);
            controller.setOwner(owner);
            controller.setMainController(this);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void logout() {
    	 parentStage.close();
         owner.initLogin();
    }
    Thread taskThread ;
	@FXML
    void initialize() {
		colUserName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	    addButtonToTable();
	    addFriendProBtn();
	    running=true;
	    
	    taskThread= new Thread(new Runnable() {
	    	
	        @Override
	        public void run() {
	        	try {
	    		    socket = new Socket(ipAdress, 2345);
	                System.out.println("Socket is connected with server!");
	                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                writer = new PrintWriter(socket.getOutputStream(), true);
	                while(running){
	  	        	  try {
	  	        		  message = reader.readLine();
	  	        	  } catch (IOException e) {
	  	      	        e.printStackTrace();
	  	      	      }

	  	            try {
	  	              Thread.sleep(100);
	  	            } catch (InterruptedException e) {
	  	              e.printStackTrace();
	  	            }

	  	            Platform.runLater(new Runnable() {
	  	              @Override
	  	              public void run() {
	  	            	if(send) {
	  	            		txtArea.appendText(message + "\n");
	  	            	}
	  	              }
	  	            });
	  	          }
	                writer.close();
	                reader.close();
	                socket.close();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        
	    	 }
	      });
	    taskThread.setDaemon(true);
	    taskThread.start();
    }
	
	  public void sendMsg() {
	    	if(send) {
	    		String msg = txtMsg.getText();
	            writer.println(user.getFirstName() + ": " + msg);
	            writer.flush();
	    		txtMsg.setText("");
	    		Chat chat=new Chat();
	    		chat.setFk_user_id(user.getPk_id());
	    		chat.setFk_friend_id(chatFriend.getFk_user_id());
	    		chat.setMessage(user.getFirstName()+": "+msg);
	    		chat.setSend_date(LocalDate.now().toString());
	    		if(db.saveChat(chat)) {
	    			System.out.println("chat save");
	    		}
	    		
	    	}
	    	else {
	    		alertMessage(false,"Chat bichih naizaa songono uu");
	    	}
	   }
	
	  /**
	   * Hereglegchiin owog, nereer haina
	   */
	  public void searchUser() {
		    String name=txtSearch.getText();
	        FilteredList<User> filteredData = new FilteredList<>(owner.getUsers(), b -> true);
	        //Haih talbaruud hooson baiwal hooson utga butsaana
	        if(name==null&&name.trim().isEmpty()){
	            return;
	        }       
	        //haih talbariin oruulsan utgaar hailt hiine
	         filteredData.setPredicate(user -> {
	              String lowerCaseFilter = name.toLowerCase();
	               if (user.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	                    return true; // Filter matches customer name
	                }
	                else if(user.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	                	 return true;
	                }
	                else   return false; // Does not match.
	            });      
	        SortedList<User> sortedData = new SortedList<>(filteredData);
	        sortedData.comparatorProperty().bind(table.comparatorProperty());
	        addButtonToTable();
		    addFriendProBtn();
	        table.setItems(sortedData);
	  }
	  
	/**
     * Main class-ыг тохируулна
     * @param owner Main class-аас дамжуулах объект
     */
    @Override
    public void setOwner(Main owner) {
        this.owner = owner;
        users.addAll(owner.getUsers());
        table.setItems(users);
    }
	

}

package application.view;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
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

/**
 * Vndsen main fxml-iin controller class
 * @author enkherdene
 *
 */
public class MainController extends Controller{

	@FXML
    private Label lblUserName;//hereglegchiin neriig awna
	
	@FXML
    private Label lblFriendName; //Chat bichij bui naiziin neriig awna

    @FXML
    private TextField txtSearch; //user haih textfield

    @FXML
    private TableView<User> table; // user-uudiig haruulah table

    @FXML
    private TableColumn<User, String> colUserName; //user-iin neriig haruulah column

    @FXML
    private TableColumn<User, Void> colBtn; //zurwas bichih button-g haruulah column
    
    @FXML
    private TableColumn<User, Void> friendProfile; //profile harah button haruulah column

    @FXML
    private TextArea txtArea;

    @FXML
    private TextArea txtMsg;
    /**Login hiisen user*/
	private User user;
	/**zurwas bichij bui naiz*/
	private Friend chatFriend;
	/**zurwas bichij bui naiziin ner*/
	private String friendName;
	/**
	 * Zurwas bichih naizaa songoson esehiig zaana, songoson bol true
	 */
	private boolean send=false;
	
	private boolean running=false;
	/**
	 * Hereglegchiin bichsen zurwas
	 */
	private String message="";
	/**User-iin ip address*/
	private String ipAdress="localhost";
	
	/**
	 * Table haruulah hereglegchdiin medeelel
	 */
	private ObservableList<User> users = FXCollections.observableArrayList();
	/**
	 * Socket-oos ogogdol unshig object
	 */
    BufferedReader reader;
    /**
	 * Socket-oos ogogdol bichih object
	 */
    PrintWriter writer;
    Socket socket;
   
    /**
     * Login hiisen hereglegchiig butsaana
     * @return
     */
	public User getUser() {
		return user;
	}
	/**
	 * Login hiisen hereglegchiin ip address-g tohiruulna
	 * @param ipAdress
	 */
	public void setIp(String ipAdress) {
		this.ipAdress=ipAdress;
	}
	/**
	 * Login hiisen user-iig onoono
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
		initMain();		
	}
	/**
	 * Login hiisen user-iin neriig label-d onoono
	 */
	public void initMain() {
		lblUserName.setText(user.getFirstName()+" "+user.getLastName());
	}
	
	  /**
     * Хүснэгтэд zurwas bichih товчлуурыг нэмнэ
     */
    private void addButtonToTable() {

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                		 private final Button btn = new Button("Зурвас Бичих");
                         {
                             //Butten deer darah ved duudagdana. Tuhain mornii user-iin medeelliig awna
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
                            	 //naiz bish bol naizaar nemeh huudsiig duudna
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
     * Busad hereglegchidiin profile-ig haruulah button-g vvsgene
     */
    private void addFriendProBtn() {

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                		 private final Button btn = new Button("Профайл");
                         {
                             //Butten deer darah ved duudagdana. Tuhain mornii user-iin medeelliig awna
                             btn.setOnAction((ActionEvent event) -> {
                            	 User friend= table.getItems().get(getIndex());
                            	 //delgerengvi medeellig haruulah tsonhruu shiljine
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
     * Songoson hereglegchiin medeelliig haruulah tsonhiig duudna
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
            //friendProfile controller-iig onoono
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
    /**
     * Hereglegch chat bichih naizaa songoson bol duudagdana
     * textarea-g hooson bolgono
     * chat bichig naiziin neriig label-d onoono
     * 2 hereglegchiin bichij bsn chatig ogogdliin sangaas olno
     * @param f = chat bichig naiz
     * @param name = login hiisen user
     */
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
     * Shineer naiz nemeh tsonh garch irne
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
    /**
     * Logout hiisen tul login huudastuu shiljine
     */
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
	    //shine thread vvsgene
	    taskThread= new Thread(new Runnable() {
	    	
	        @Override
	        public void run() {
	        	try {
	        		//Login hiisen hereglegchid  2345 port, localhost der shine socket vvsgene servert holbogdoh hvselt ilgeene
	    		    socket = new Socket(ipAdress, 2345);
	                System.out.println("Socket is connected with server!");
	                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                writer = new PrintWriter(socket.getOutputStream(), true);
	                //busad hereglegchidtei haritsana
	               //Busad hereglegchdiin bichsen zurwasiig message huwisagchid onoono
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
	  	            //Busad hereglegchdiin bichsen zurwasiig haruulna
	  	            Platform.runLater(new Runnable() {
	  	              @Override
	  	              public void run() {
	  	            	  //hereglegch zurwas bichih naizaa songoson baih yostoi
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
	    //thread-g ehlvvlne
	    taskThread.start();
    }
	/**
	 * Hereglegchiin bichsen zurwasiig svljeend holbogdson hereglegchdedrvv damjuulna
	 */
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
        users.remove(this.user);
        table.setItems(users);
    }
	

}

package application.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import application.model.Friend;
import application.model.Chat;
import application.model.User;
/**
 * Ugugdliin santai haritsah class
 * @author enkherdene
 *
 */
public class ChatDatabase {
	Connection conn=MySqlDaoFactory.createConnection();
	ResultSet rs=null;
    PreparedStatement ps=null;
    public ChatDatabase(){
    }
    /**
     * Bvh hereglegchiin medeelliig ogogdliin sangaas olj butsaana
     * @return Bvh hereglegchiin medeelel
     */
	public List<User> findAllUser() {
    	List<User> list=new ArrayList<User>();
		
		try {
			ps=conn.prepareStatement("Select * from user");
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				  list.add(new User(rs.getInt("pk_id"),rs.getString("name"),rs.getString("password"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("major"),rs.getString("date_of_birth"),rs.getString("mobile_number")));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Parametreer ogogdson id-tai hereglegchiig olno
	 * @param id = haih hereglegchiin id
	 * @return - Parametreer ogogdson id-tai hereglegch
	 */
	public User userFindById(int id) {
		String sql="Select * from user where pk_id=?";
		User user=null;
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1,id);
			rs=ps.executeQuery();
			while(rs.next()) {
				user=new User(rs.getInt("pk_id"),rs.getString("name"),rs.getString("password"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("major"),rs.getString("date_of_birth"),rs.getString("mobile_number"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Parametreer ogogdson hereglegchiig ogogdliin sand hadgalna 
	 * @param user = hadgalah hereglegch
	 * @return = amjilttai hadgalsan bol true, vgvi bol false
	 */
	public boolean saveUser(User user) {
		String sql ="insert into user (name,password,firstname,lastname,major,date_of_birth,mobile_number) values (?,?,?,?,?,?,?)";
    	try {
	    		ps=conn.prepareStatement(sql);
	    		ps.setString(1, user.getName());
	    		ps.setString(2, user.getPassword());
	    		ps.setString(3, user.getFirstName());
	    		ps.setString(4, user.getLastName());
	    		ps.setString(5, user.getMajor());
	    		ps.setString(6, user.getBirthday());
	    		ps.setString(7, user.getPhone());
 	    		ps.execute();
	    		return true;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
	}
	/**
	 * Parametreer ogogdson hereglegchiin medeellig oorchilno
	 * @param user = oorchiloh hereglegch
	 * @return =  amjilttai bol true, vgvi bol false
	 */
	public boolean updateUser(User user) {
		System.out.println(user.getFirstName());
		String sql="update user set name=?,firstname=?,lastname=?,major=?,date_of_birth=?,mobile_number=? where pk_id=?";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getName());
    		ps.setString(2, user.getFirstName());
    		ps.setString(3, user.getLastName());
    		ps.setString(4, user.getMajor());
    		ps.setString(5, user.getBirthday());
    		ps.setString(6, user.getPhone());
    		ps.setInt(7, user.getPk_id());
	    	ps.execute();
    		return true;
		}
		catch(Exception e){
			e.printStackTrace();
    		return false;
		}
	}
	/**
	 * Parametreer ogogdson hereglegchiig ogogdliin sangaas ustgana
	 * @param u = ustgah hereglegch
	 * @return amjilttai bol true, vgvi bol false
	 */
	public boolean delete(User u) {
		String sql = "delete from user where pk_id = ?";
		try {
			ps=conn.prepareStatement(sql);
    		ps.setInt(1, u.getPk_id());
	    	ps.execute();
    		return true;
		}
		catch(Exception e){
			e.printStackTrace();
    		return false;
		}
		
	}
	/**
	 * Bvh friend-iin medeelllig butsaan a
	 * @return = bvh friend-iin medeelel
	 */
	public List<Friend> findAllFriend() {
    	List<Friend> list=new ArrayList<Friend>();
		
		try {
			ps=conn.prepareStatement("Select * from friend");
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				  list.add(new Friend(rs.getInt("pk_id"),rs.getInt("fk_user_id"),rs.getString("ip_address"),rs.getInt("port")));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Shine friend-ig ogogdliin sand hadgalna
	 * @param friend = shine friend
	 * @return amjilttai bol true, vgvi bol false
	 */
	public boolean saveFriend(Friend friend) {
		String sql ="insert into friend (pk_id,fk_user_id,ip_address,port) values (?,?,?,?)";
    	try {
	    		ps=conn.prepareStatement(sql);
	    		ps.setInt(1, friend.getPk_id());
	    		ps.setInt(2, friend.getFk_user_id());
	    		ps.setString(3, friend.getIpAddress());
	    		ps.setInt(4, friend.getPort());
 	    		ps.execute();
	    		return true;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
	}
	/**
	 * Parametreer ogogdson 2 id-tai friend-g ustgana
	 * @param id1 = hereglegchiin id
	 * @param id2 = naiziin id
	 * @return = amjilttai bol true, vgvi bol false
	 */
	public boolean deleteFriend(int id1, int id2) {
		String sql = "delete from friend where  pk_id = ? and fk_user_id=?";
		try {
			ps=conn.prepareStatement(sql);
    		ps.setInt(1, id1);
    		ps.setInt(2, id2);
	    	ps.execute();
    		return true;
		}
		catch(Exception e){
			e.printStackTrace();
    		return false;
		}
		
	}
	/**
	 * parametreer damjij irsen chatiig hadgalna
	 * @param chat = bichsen chat
	 * @return amjilttai bol true, vgvi bol false
	 */
	public boolean saveChat(Chat chat) {
		String sql ="insert into chat (fk_user_id,fk_friend_id,message,send_date) values (?,?,?,?)";
    	try {
	    		ps=conn.prepareStatement(sql);
	    		ps.setInt(1, chat.getFk_user_id());
	    		ps.setInt(2, chat.getFk_friend_id());
	    		ps.setString(3, chat.getMessage());
	    		ps.setString(4, chat.getSend_date());
 	    		ps.execute();
	    		return true;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
	}
	/**
	 * Bvh chatiig olno
	 * @return bvh chat
	 */
	public List<Chat> findAllChat() {
    	List<Chat> list=new ArrayList<Chat>();
		
		try {
			ps=conn.prepareStatement("Select * from chat");
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				  list.add(new Chat(rs.getInt("pk_id"),rs.getInt("fk_user_id"),rs.getInt("fk_friend_id"),rs.getString("message"),rs.getString("send_date")));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * Ogogdson 2 tai hereglegchiin chatiig olno
	 * @param id1 = hereglegch 1
	 * @param id2 = naiziin id
	 * @return = 2 hereglegchiin hoorondoo bichsen chat
	 */
	public List<Chat> findByIdChat(int id1, int id2) {
    	List<Chat> list=new ArrayList<Chat>();
		try {
			ps=conn.prepareStatement("SELECT * FROM chat WHERE (fk_user_id = ? and fk_friend_id = ?)or(fk_user_id = ? and fk_friend_id = ?)");
			ps.setInt(1, id1);
    		ps.setInt(2,id2);
    		ps.setInt(3, id2);
    		ps.setInt(4,id1);
    		rs=ps.executeQuery();
			while(rs.next()) {
				  list.add(new Chat(rs.getInt("pk_id"),rs.getInt("fk_user_id"),rs.getInt("fk_friend_id"),rs.getString("message"),rs.getString("send_date")));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

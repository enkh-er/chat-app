package application.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import application.model.Friend;
import application.model.Chat;
import application.model.User;

public class ChatDatabase {
	Connection conn=MySqlDaoFactory.createConnection();
	ResultSet rs=null;
    PreparedStatement ps=null;
    public ChatDatabase(){
    	
    }
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

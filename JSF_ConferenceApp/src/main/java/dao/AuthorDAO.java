package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dao.Author;
import dao.Database;
import dao.Paper;

public class AuthorDAO {

	 public static ArrayList authorList() {
	        try (Connection con = Database.getConnection()) {
	            Statement stmt=con.createStatement();  
	            ResultSet rs=stmt.executeQuery("select * from author"); 
	            ArrayList authors = new ArrayList();
	            while(rs.next()) {
	                Author a = new Author();
	                a.setEmail(rs.getString("au_email"));
	                a.setFirst_name(rs.getString("first_name"));
	                a.setLast_name(rs.getString("last_name"));
	                authors.add(a);
	            }
	            System.out.println("Authors added to list!");
	            return authors;
	        } catch (Exception ex) {
	            System.out.println("AuthorDAO-> authorsList() : " + ex.getMessage());
	            return null;
	        }
	    }
	 
	 public static ArrayList authorPaperList(String email) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("SELECT * FROM paper as p WHERE p.au_email = ?;");  
	            stmt.setString(1, email);
	            ResultSet rs=stmt.executeQuery(); 
	            ArrayList papers = new ArrayList();
	            while(rs.next()) {
	                Paper p = new Paper();
	                p.setPaper_id(rs.getInt("paper_id"));
	                p.setTitle(rs.getString("title"));
	                p.setFile_name(rs.getString("file_name"));
	                p.setTheAbstract(rs.getString("abstract"));
	                papers.add(p);
	            }
	            System.out.println("Author Papers added to list!");
	            return papers;
	        } catch (Exception ex) {
	            System.out.println("AuthorDAO-> authorPaperList() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	 
	 public static String authorName(String email) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from author where au_email=?");  
	            stmt.setString(1, email);
	            ResultSet rs=stmt.executeQuery();
	            rs.next();
	            return rs.getString("first_name");
	        } catch (Exception ex) {
	            System.out.println("AuthorDAO-> authorName() : " + ex.getMessage());
	            return null;
	        }
	 }
	// Used to save author record
	    public static void save(Author a){
	        int result = 0;
	        try{
	        	Connection conn = Database.getConnection();
	            PreparedStatement stmt = conn.prepareStatement("insert into author(au_email, first_name,last_name) values(?,?,?)");
	            stmt.setString(1, a.getEmail());
	            stmt.setString(2, a.getFirst_name());
	            stmt.setString(3,a.getLast_name());
	            result = stmt.executeUpdate();
	            System.out.println("Author saved successfully!");
	            conn.close();
	        }catch(Exception e){
	        	System.out.println("AuthorDAO->save() : " + e.getMessage());
	        }
	    }
	    
	    // Used to fetch record to update
	    public static Author edit(String email){
	        Author a= null;
	        try{
	        	Connection conn = Database.getConnection();
	        	//PreparedStatement ps = con.prepareStatement("select * from author where au_email =?");
	            
	            Statement stmt=conn.createStatement();  
	            ResultSet rs=stmt.executeQuery("select * from author where au_email = '"+(email)+"'");
	            rs.next();
	            a = new Author();
	            a.setFirst_name(rs.getString("first_name"));
	            a.setLast_name(rs.getString("last_name"));
	            a.setEmail(email);
	            System.out.println("Author data updated!");
	            conn.close();
	            return a;
	        }catch(Exception e){
	        	System.out.println("AuthorDAO->edit() : " + e.getMessage());
	        	return null;
	        }       
	    }

	    public static boolean editAuthor(Author author) {
	        try (Connection con = Database.getConnection()) {
	            PreparedStatement ps = con.prepareStatement("update author set first_name=?, last_name=? where au_email=?");
	            ps.setString(1, author.getFirst_name());
	            ps.setString(2, author.getLast_name());
	            ps.setString(3, author.getEmail());
	            System.out.println("Author updated!");
	            int count = ps.executeUpdate();
	            return count == 1;
	        } catch (Exception ex) {
	            System.out.println("AuthorDAO->editAuthor() : " + ex.getMessage());
	            return false;
	        }
	    }
	    
	   
	// Used to delete author record
	    public static void delete(String email){
	        try{
	        	Connection conn = Database.getConnection();  
	            PreparedStatement stmt = conn.prepareStatement("delete from author where au_email = "+email);  
	            stmt.executeUpdate();  
	            System.out.println("Author deleted successfully");
	        }catch(Exception e){
	        	System.out.println("AuthorDAO->delete() : " + e.getMessage());
	        }
	    }
	    
	     public static void saveAssignAuthor(String email, int paper_id){
	        try{
	        	Connection conn = Database.getConnection();
	            PreparedStatement stmt = conn.prepareStatement("insert into writes(au_email, paper_id) values(?,?)");
	            stmt.setString(1, email);
	            stmt.setInt(2, paper_id);
	            int result = stmt.executeUpdate();
	            System.out.println("Author-Paper saved successfully!");
	            conn.close();
	        }catch(Exception e){
	        	System.out.println("AuthorDAO->saveAssignAuthor() : " + e.getMessage());
	        }
	    }
	    
	
}

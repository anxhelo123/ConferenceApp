package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReviewerDAO {
	
	//Function that creates a list with all reviewers
	public static ArrayList reviewersList() {
        try (Connection con = Database.getConnection()) {// Connect with database
            Statement stmt=con.createStatement();  //create statement that will translate to SQL 
            ResultSet rs=stmt.executeQuery("select * from reviewer"); // Line that will get the results and save them
            ArrayList reviewers = new ArrayList();
            while(rs.next()) {
                Reviewer r = new Reviewer();
                r.setEmail(rs.getString("rv_email"));
                r.setFirst_name(rs.getString("first_name"));
                r.setLast_name(rs.getString("last_name"));
                r.setMobile(rs.getString("mobile"));
                r.setAffiliation(rs.getString("affiliation"));
                r.setTopic_interest(rs.getString("topic_interest"));
                reviewers.add(r);
            }
            System.out.println("Reviewers added to list!");
            return reviewers;
        } catch (Exception ex) {
            System.out.println("ReviewerDAO-> reviewersList() : " + ex.getMessage());
            return null;
        }
    }
	
	public static ArrayList reviewerPapersList(String email) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from reviewer as rv\r\n" + 
	            		"join reviews as r on rv.rv_email = r.rv_email\r\n" + 
	            		"join paper as p on p.paper_id = r.paper_id\r\n" + 
	            		"where r.rv_email = ?;");  
	            stmt.setString(1, email);
	            ResultSet rs=stmt.executeQuery(); 
	            ArrayList papers = new ArrayList();
	            while(rs.next()) {
	                Paper p = new Paper();
	                p.setPaper_id(rs.getInt("paper_id"));
	                p.setTitle(rs.getString("title"));
	                p.setTheAbstract(rs.getString("abstract"));
	                p.setEmail(rs.getString("au_email"));
	                papers.add(p);
	            }
	            System.out.println("Reviewer Papers added to list!");
	            return papers;
	        } catch (Exception ex) {
	            System.out.println("ReviewerDAO-> reviewerPapersList() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	 public static String reviewerName(String email) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from reviewer\r\n" + 
	            		"where rv_email = ?;");  
	            stmt.setString(1, email);
	            ResultSet rs=stmt.executeQuery();
	            rs.next();
	            return rs.getString("first_name");
	        } catch (Exception ex) {
	            System.out.println("ReviewerDAO-> reviewerName() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	// Used to delete director record
   public static void delete(String email){
       try{
       	Connection conn = Database.getConnection();  
           PreparedStatement stmt = conn.prepareStatement("delete from reviewer where rv_email = "+email);  
           stmt.executeUpdate();  
           System.out.println("Reviewer deleted successfully");
       }catch(Exception e){
       	System.out.println("ReviewerDAO->delete() : " + e.getMessage());
       }
   }
		
   // Used to save director record
   public static void save(Reviewer r){
       int result = 0;
       try{
       	Connection conn = Database.getConnection();
           PreparedStatement stmt = conn.prepareStatement("insert into reviewer(rv_email,first_name,last_name,mobile,affiliation,topic_interest) values(?,?,?,?,?,?)");
           stmt.setString(1, r.getEmail());
           stmt.setString(2, r.getFirst_name());
           stmt.setString(3, r.getLast_name());
           stmt.setString(4, r.getMobile());
           stmt.setString(5, r.getAffiliation());
           stmt.setString(6, r.getTopic_interest());
           result = stmt.executeUpdate();
           System.out.println("Reviewer saved successfully!");
           conn.close();
       }catch(Exception e){
       	System.out.println("ReviewerDAO->save() : " + e.getMessage());
       }
   }
   
   // Used to fetch record to update
   public static Reviewer edit(String email){
       Reviewer r = null;
       try{
       	   Connection conn = Database.getConnection();
       	   
           Statement stmt=conn.createStatement();  
           ResultSet rs=stmt.executeQuery("select * from reviewer where rv_email =  '" +(email) + "'");
           rs.next();
           r = new Reviewer();
           r.setEmail(email);
           r.setFirst_name(rs.getString("first_name"));
           r.setLast_name(rs.getString("last_name"));
           r.setMobile(rs.getString("mobile"));
           r.setAffiliation(rs.getString("affiliation"));
           r.setTopic_interest(rs.getString("topic_interest"));
           System.out.println("Reviewer data updated!");
           conn.close();
           return r;
       }catch(Exception e){
       	System.out.println("ReviewerDAO->edit() : " + e.getMessage());
       	return null;
       }       
   }

   public static boolean editReviewer(Reviewer reviewer) {
       try (Connection con = Database.getConnection()) {
           PreparedStatement ps = con.prepareStatement("update reviewer set first_name=?, last_name=?, mobile=?, affiliation=?, topic_interest=? where rv_email=?");
           ps.setString(1, reviewer.getFirst_name());
           ps.setString(2, reviewer.getLast_name());
           ps.setString(3, reviewer.getMobile());
           ps.setString(4, reviewer.getAffiliation());
           ps.setString(5, reviewer.getTopic_interest());
           ps.setString(6, reviewer.getEmail());
           System.out.println("Reviewer updated!");
           int count = ps.executeUpdate();
           return count == 1;
       } catch (Exception ex) {
           System.out.println("ReviewerDAO->editDirector() : " + ex.getMessage());
           return false;
       }
   }
   /* This function will not be needed since the assignment of Reviewer to Paper is done at ReviewDAO. 
   public static void saveAssignReviewer(String rv_email, String role, int film_id){
       try{
       	Connection conn = Database.getConnection();
           PreparedStatement stmt = conn.prepareStatement("insert into film_regjizor(fid, rid, roli) values(?,?,?)");
           stmt.setInt(1, film_id);
           stmt.setInt(2, regjizor_id);
           stmt.setString(3, role);
           int result = stmt.executeUpdate();
           System.out.println("Director-Movie saved successfully!");
           conn.close();
       }catch(Exception e){
       	System.out.println("DirectorDAO->saveAssignDirector() : " + e.getMessage());
       }
   }
   */
}

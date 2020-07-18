package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PaperDAO {
		
	public static ArrayList paperList() {
        try (Connection con = Database.getConnection()) {
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from paper"); 
            ArrayList papers = new ArrayList();
            while(rs.next()) {
                Paper p = new Paper();
                p.setPaper_id(rs.getInt("paper_id"));
                p.setTitle(rs.getString("title"));
                p.setFile_name(rs.getString("file_name"));
                p.setTheAbstract(rs.getString("abstract"));
                p.setEmail(rs.getString("au_email"));
                papers.add(p);
            }
            System.out.println("Papers added to list!");
            return papers;
        } catch (Exception ex) {
            System.out.println("PaperDAO-> paperList() : " + ex.getMessage());
            return null;
        }
    }
	
	
	public static ArrayList paperAuthorsList(int paper_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from paper where paper.paper_id=?");  
	            stmt.setInt(1, paper_id);
	            ResultSet rs=stmt.executeQuery(); 
	            ArrayList authors = new ArrayList();
	            while(rs.next()) {
	                Author a = new Author();
	                a.setEmail(rs.getString("au_email"));
	                a.setFirst_name(rs.getString("first_name"));
	                a.setLast_name(rs.getString("last_name"));
	                authors.add(a);
	            }
	            System.out.println("Paper Authors added to list!");
	            return authors;
	        } catch (Exception ex) {
	            System.out.println("PaperDAO-> paperAuthorsList() : " + ex.getMessage());
	            return null;
	        }
	 }
	
	
	public static String paperTitle(int paper_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from paper where paper_id=?");  
	            stmt.setInt(1, paper_id);
	            ResultSet rs=stmt.executeQuery();
	            rs.next();
	            return rs.getString("title");
	        } catch (Exception ex) {
	            System.out.println("PaperDAO-> paperAuthorssList() : " + ex.getMessage());
	            return null;
	        }
	 }
	
	
	public static ArrayList paperReviewersList(int paper_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from paper as p\r\n" + 
	            		"	join reviews as rv ON p.paper_id = rv.paper_id\r\n" + 
	            		"	join reviewer as r ON rv.rv_email = r.rv_email\r\n" + 
	            		"	where p.paper_id = ?");  
	            stmt.setInt(1, paper_id);
	            ResultSet rs=stmt.executeQuery(); 
	            ArrayList reviewers = new ArrayList();
	            while(rs.next()) {
	                Reviewer rv = new Reviewer();
	                rv.setEmail(rs.getString("rv_email"));
	                rv.setFirst_name(rs.getString("first_name"));
	                rv.setLast_name(rs.getString("last_name"));
	                rv.setMobile(rs.getString("mobile"));
	                rv.setAffiliation(rs.getString("affiliation"));
	                rv.setTopic_interest(rs.getString("topic_interest"));
	                
	                reviewers.add(rv);
	            }
	            System.out.println("Paper Reviewers added to list!");
	            return reviewers;
	        } catch (Exception ex) {
	            System.out.println("PaperDAO-> paperReviewersList() : " + ex.getMessage());
	            return null;
	        }
	 }
	
	
	 // Used to save paper record
    public static void save(Paper p){
        int result = 0;
        try{
        	Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into paper(title, abstract, file_name, au_email) values(?,?,?,?)");
            stmt.setString(1, p.getTitle());
            stmt.setString(2, p.getTheAbstract());
            stmt.setString(3, p.getFile_name());
            stmt.setString(4, p.getEmail());
            result = stmt.executeUpdate();
            System.out.println("Paper saved successfully!");
            conn.close();
        }catch(Exception e){
        	System.out.println("PaperDAO->save() : " + e.getMessage());
        }
    }
    
    // Used to fetch record to update
    public static Paper edit(int paper_id){
        Paper p = null;
        try{
        	Connection conn = Database.getConnection();
            Statement stmt=conn.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from paper where paper_id = "+(paper_id));
            rs.next();
            p = new Paper();
            p.setTitle(rs.getString("title"));
            p.setFile_name(rs.getString("file_name"));
            p.setTheAbstract(rs.getString("abstract"));
            p.setEmail(rs.getString("au_email"));
            System.out.println("Paper data updated!");
            conn.close();
            return p;
        }catch(Exception e){
        	System.out.println("PaperDAO->edit() : " + e.getMessage());
        	return null;
        }       
    }
    
    // Used to delete paper record
    public static void delete(int paper_id){
        try{
        	Connection conn = Database.getConnection();  
            PreparedStatement stmt = conn.prepareStatement("delete from paper where paper_id = "+paper_id);  
            stmt.executeUpdate();  
            System.out.println("Paper deleted successfully");
        }catch(Exception e){
        	System.out.println("PaperDAO->delete() : " + e.getMessage());
        }
    }

    public static boolean editPaper(Paper p, int paper_id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("update paper set title=?, file_name=?, abstract=?, au_email=? where paper_id=?");
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getFile_name());
            ps.setString(3, p.getTheAbstract());
            ps.setString(4, p.getEmail());
            ps.setInt(5, p.getPaper_id());
            System.out.println("Paper updated!");
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("PaperDAO->editPaper() : " + ex.getMessage());
            return false;
        }
    }
}

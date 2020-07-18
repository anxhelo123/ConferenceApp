package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReviewDAO {
		
	//Create a list of all reviews.
	public static ArrayList reviewsList() {
        try (Connection con = Database.getConnection()) {
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from reviews"); 
            ArrayList reviews = new ArrayList();
            while(rs.next()) {
                Review r = new Review();
                r.setPaper_id(rs.getInt("paper_id"));
                r.setAuthor_comments(rs.getString("author_comments"));
                r.setCommitee_comments(rs.getString("commitee_comments"));
                r.setRecommendation(rs.getString("recommendation"));
                r.setTechnical_merit(rs.getString("technical_merit"));
                r.setReadability(rs.getString("readability"));
                r.setOriginality(rs.getString("originality"));
                r.setRelevance(rs.getString("relevance"));
                r.setEmail(rs.getString("rv_email"));
                reviews.add(r);
            }
            System.out.println("Reviewes added to list!");
            return reviews;
        } catch (Exception ex) {
            System.out.println("ReviewDAO-> reviewsList() : " + ex.getMessage());
            return null;
        }
    }
 
	//Listing reviews made by Reviewers
 public static ArrayList reviewReviewrsList(String rv_email) {
	 try (Connection con = Database.getConnection()) {
            PreparedStatement stmt=con.prepareStatement("select * from reviews as r join reviewer as rv ON r.rv_email = rv.rv_email where rv.rv_email = ?;");  
            stmt.setString(1, rv_email);
            ResultSet rs=stmt.executeQuery(); 
            ArrayList reviewes = new ArrayList();
            while(rs.next()) {
                Review r = new Review();
                r.setPaper_id(rs.getInt("paper_id"));
                r.setAuthor_comments(rs.getString("author_comments"));
                r.setCommitee_comments(rs.getString("commitee_comments"));
                r.setRecommendation(rs.getString("recommendation"));
                r.setTechnical_merit(rs.getString("technical_merit"));
                r.setReadability(rs.getString("readability"));
                r.setOriginality(rs.getString("originality"));
                r.setRelevance(rs.getString("relevance"));
                r.setEmail(rs.getString("rv_email"));
                reviewes.add(r);
            }
            System.out.println("Reviewer Reviewes added to list!");
            return reviewes;
        } catch (Exception ex) {
            System.out.println("ReviewDAO-> reviewReviewrsList() : " + ex.getMessage());
            return null;
        }
 }
 
 /* Would use this function if the reviews had titles
 public static String movieTitle(int film_id) {
	 try (Connection con = Database.getConnection()) {
            PreparedStatement stmt=con.prepareStatement("select * from film where film_id=?");  
            stmt.setInt(1, film_id);
            ResultSet rs=stmt.executeQuery();
            rs.next();
            return rs.getString("titulli");
        } catch (Exception ex) {
            System.out.println("MovieDAO-> movieActorsList() : " + ex.getMessage());
            return null;
        }
 }
 */
 
 
 /*This one is the same with reviewReviewrsList
 public static ArrayList movieDirectorsList(int film_id) {
	 try (Connection con = Database.getConnection()) {
            PreparedStatement stmt=con.prepareStatement("select * from film_regjizor,regjizor where film_regjizor.rid= regjizor.regjizor_id and film_regjizor.fid=?");  
            stmt.setInt(1, film_id);
            ResultSet rs=stmt.executeQuery(); 
            ArrayList directors = new ArrayList();
            while(rs.next()) {
                Director d = new Director();
                d.setDirector_id(rs.getInt("regjizor_id"));
                d.setDatelindja(rs.getString("datelindja"));
                d.setEmri(rs.getString("emri"));
                directors.add(d);
            }
            System.out.println("Movie Directors added to list!");
            return directors;
        } catch (Exception ex) {
            System.out.println("MovieDAO-> movieDirectorsList() : " + ex.getMessage());
            return null;
        }
 }
 */
 
 
 // Used to save review record
public static void save(Review r){
    int result = 0;
    try{
    	Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("insert into reviews(paper_id, author_comments,commitee_comments,recommendation ,technical_merit, readability, originality,relevance, rv_email ) values(?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1, r.getPaper_id());
        stmt.setString(2, r.getAuthor_comments());
        stmt.setString(3, r.getCommitee_comments());
        stmt.setString(4, r.getRecommendation());
        stmt.setString(5, r.getTechnical_merit());
        stmt.setString(6, r.getReadability());
        stmt.setString(7, r.getOriginality());
        stmt.setString(8, r.getRelevance());
        stmt.setString(9, r.getEmail());
        result = stmt.executeUpdate();
        System.out.println("Review saved successfully!");
        conn.close();
    }catch(Exception e){
    	System.out.println("ReviewDAO->save() : " + e.getMessage());
    }
}

// Used to fetch record to update
public static Review edit(int paper_id){
    Review r = null;
    try{
    	Connection conn = Database.getConnection();
        Statement stmt=conn.createStatement();  
        ResultSet rs=stmt.executeQuery("select * from reviews where paper_id = "+(paper_id));
        rs.next();
        r = new Review();
        r.setPaper_id(rs.getInt("paper_id"));
        r.setAuthor_comments(rs.getString("author_comments"));
        r.setCommitee_comments(rs.getString("commitee_comments"));
        r.setRecommendation(rs.getString("recommendation"));
        r.setTechnical_merit(rs.getString("technical_merit"));
        r.setReadability(rs.getString("readability"));
        r.setOriginality(rs.getString("originality"));
        r.setRelevance(rs.getString("relevance"));
        r.setEmail(rs.getString("rv_email"));
        System.out.println("Review data updated!");
        conn.close();
        return r;
    }catch(Exception e){
    	System.out.println("ReviewDAO->edit() : " + e.getMessage());
    	return null;
    }       
}

// Used to delete review record
public static void delete(int paper_id){
    try{
    	Connection conn = Database.getConnection();  
        PreparedStatement stmt = conn.prepareStatement("delete from film where paper_id = "+paper_id);  
        stmt.executeUpdate();  
        System.out.println("Review deleted successfully");
    }catch(Exception e){
    	System.out.println("ReviewDAO->delete() : " + e.getMessage());
    }
}

//Updating a review
public static boolean editReview(Review r, int paper_id) {
    try (Connection con = Database.getConnection()) {
        PreparedStatement ps = con.prepareStatement("update film set author_comments=?, commitee_comments=?, recommendation=?, technical_merit=?, readability=?, originality=?, relevance=?, rv_email=? where paper_id=?");
        ps.setString(1, r.getAuthor_comments());
        ps.setString(2, r.getCommitee_comments());
        ps.setString(3, r.getRecommendation());
        ps.setString(4, r.getTechnical_merit());
        ps.setString(5, r.getOriginality());
        ps.setString(7, r.getRelevance());
        ps.setString(8, r.getEmail());
        ps.setInt(9, r.getPaper_id());
        System.out.println("Review updated!");
        int count = ps.executeUpdate();
        return count == 1;
    } catch (Exception ex) {
        System.out.println("ReviewDAO->editReview() : " + ex.getMessage());
        return false;
    }
}
}

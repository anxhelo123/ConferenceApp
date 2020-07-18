package dao;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped  
public class Review {
	private int paper_id;
	private String author_comments, commitee_comments, recommendation, technical_merit, readability, originality, relevance, rv_email, message;
	
	ArrayList reviewsList, reviewReviewrsList;
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
	public int getPaper_id() {
		return paper_id;
	}
	public void setPaper_id(int paper_id) {
		this.paper_id = paper_id;
	}
	public String getAuthor_comments() {
		return author_comments;
	}
	public void setAuthor_comments(String author_comments) {
		this.author_comments = author_comments;
	}
	public String getCommitee_comments() {
		return commitee_comments;
	}
	public void setCommitee_comments(String commitee_comments) {
		this.commitee_comments = commitee_comments;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getTechnical_merit() {
		return technical_merit;
	}
	public void setTechnical_merit(String technical_merit) {
		this.technical_merit = technical_merit;
	}
	public String getReadability() {
		return readability;
	}
	public void setReadability(String readability) {
		this.readability = readability;
	}
	public String getOriginality() {
		return originality;
	}
	public void setOriginality(String originality) {
		this.originality = originality;
	}
	public String getRelevance() {
		return relevance;
	}
	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}
	public String getEmail() {
		return rv_email;
	}
	public void setEmail(String rv_email) {
		this.rv_email = rv_email;
	}
	
	public ArrayList reviewsList(){
		reviewsList = ReviewDAO.reviewsList();
		
		return reviewsList;
	}
	
	public String list() {
		return "/user_panel/review?faces-redirect=true";
	}
	
	public ArrayList reviewReviewrsList(String rv_email){
		reviewReviewrsList = ReviewDAO.reviewReviewrsList(rv_email);
		return reviewReviewrsList;
	}
	/*Reviews dont have titles
	public String reviews(int paper_id) {
		String title = MovieDAO.movieTitle(film_id);
		sessionMap.put("film_id", film_id);
		sessionMap.put("title", title);

		return "/user_panel/movie_actors?faces-redirect=true";
	}
	*/
	
    
    public String adminList() {
    	return "/admin_panel/review_index?faces-redirect=true";
    }
    
 // Used to delete user record
    public void delete(int paper_id){
        ReviewDAO.delete(paper_id);
    }
    
    // Used to create new paper record
    public String saveReview() {
    	ReviewDAO.save(this);
    	message = "Success! New review created!";
    	return "review_create";
    }
    
    // Used to edit paper record
    public String edit(int paper_id) {
    	Review review = ReviewDAO.edit(paper_id);
    	this.paper_id = paper_id;
    	this.author_comments = review.getAuthor_comments();
    	this.commitee_comments =review.getCommitee_comments();
    	this.recommendation= review.getRecommendation();
    	this.technical_merit = review.getTechnical_merit();
    	this.readability = review.getReadability();
    	this.originality = review.getOriginality();
    	this.relevance = review.getRelevance();
    	this.rv_email = review.getEmail();
    	return "review_edit";
    }
    
    public String editMovie(int paper_id) {
    	boolean done = ReviewDAO.editReview(this, paper_id);
        if ( done ) {
        	 message = "Success! Review updated!";
             return "movie_index";
        }else {
             message = "Sorry! Could not update review. Please try again!";
             return "review_edit";
        }
    }
    
	
}

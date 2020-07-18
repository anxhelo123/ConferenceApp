package dao;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped 
public class Reviewer {
	
	private ArrayList reviewersList, reviewerPapersList;
	
	private String email, first_name, last_name, mobile, affiliation, topic_interest, message;

	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getTopic_interest() {
		return topic_interest;
	}

	public void setTopic_interest(String topic_interest) {
		this.topic_interest = topic_interest;
	}
	
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList reviewersList(){
		reviewersList = ReviewerDAO.reviewersList();
		
		return reviewersList;
	}
	
	public String list() {
		return "/user_panel/reviewer?faces-redirect=true";
	}
	
	public ArrayList reviewerPapersList(String rv_email){
		reviewerPapersList = ReviewerDAO.reviewerPapersList(rv_email);
		return reviewerPapersList;
	}
	
	public String papers(String email) {
		String name = ReviewerDAO.reviewerName(email);
		sessionMap.put("rv_email", email);
		sessionMap.put("first_name", first_name);
		sessionMap.put("last_name", last_name);

		return "/user_panel/reviewer_papers?faces-redirect=true";
	}

	public String adminList() {
    	return "/admin_panel/reviewer_index?faces-redirect=true";
    }
    
    // Used to delete director record
    public void delete(String rv_email){
        ReviewerDAO.delete(rv_email);
    }
    
    // Used to create new director record
    public String saveReviewer() {
    	ReviewerDAO.save(this);
    	message = "Success! New reviewer created!";
    	return "reviewer_create";
    }
    
    // Used to edit director record
    public String edit(String email) {
    	Reviewer r = ReviewerDAO.edit(email);
    	this.email = email;
    	this.first_name = r.getFirst_name();
    	this.last_name = r.getLast_name();
    	this.mobile = r.getMobile();
    	this.affiliation = r.getAffiliation();
    	this.topic_interest = r.getTopic_interest();
    	return "reviewer_edit";
    }
    
    public String editReviewer(Reviewer reviewer) {
    	boolean done = ReviewerDAO.editReviewer(reviewer);
        if ( done ) {
        	 message = "Success! Reviewer updated!";
             return "reviewer_index";
        }else {
             message = "Sorry! Could not update reviewer. Please try again!";
             return "reviewer_edit";
        }
    }
	
	
	
}

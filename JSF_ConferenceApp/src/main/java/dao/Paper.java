package dao;

import java.util.ArrayList;
import java.util.Map;
import java.sql.Connection;
import java.util.Properties;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped 
public class Paper {
	private int paper_id;
	private String title, file_name, theAbstract, email, message;
	
	
	ArrayList paperList, paperAuthorsList, paperReviewersList;
	
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
	public int getPaper_id() {
		return paper_id;
	}
	public void setPaper_id(int paper_id) {
		this.paper_id = paper_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getTheAbstract() {
		return theAbstract;
	}
	public void setTheAbstract(String theAbstract) {
		this.theAbstract = theAbstract;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList paperList(){
		paperList = PaperDAO.paperList();
		
		return paperList;
	}
	
	public String list() {
		return "/user_panel/paper?faces-redirect=true";
	}
	
	public ArrayList paperAuthorsList(int paper_id){
		paperAuthorsList = PaperDAO.paperAuthorsList(paper_id);
		return paperAuthorsList;
	}
	//Look it up again
	public String authors(int paper_id) {
		String title = PaperDAO.paperTitle(paper_id);
		sessionMap.put("paper_id", paper_id);
		sessionMap.put("title", title);

		return "/user_panel/paper_authors?faces-redirect=true";
	}
	
	public ArrayList paperReviewersList(int paper_id){
		paperReviewersList = PaperDAO.paperReviewersList(paper_id);
		return paperReviewersList;
	}
	
    
    public String adminList() {
    	return "/admin_panel/paper_index?faces-redirect=true";
    }
    
 // Used to delete user record
    public void delete(int paper_id){
        PaperDAO.delete(paper_id);
    }
    
    // Used to create new movie record
    public String savePaper() {
    	PaperDAO.save(this);
    	message = "Success! New paper created!";
    	return "paper_create";
    }
    
    // Used to edit movie record
    public String edit(int paper_id) {
    	Paper paper = PaperDAO.edit(paper_id);
    	this.paper_id = paper_id;
    	this.title = paper.getTitle();
    	this.file_name = paper.getFile_name();
    	this.theAbstract = paper.getTheAbstract();
    	this.email = paper.getEmail();
    	return "paper_edit";
    }
    
    public String editPaper(int paper_id) {
    	boolean done = PaperDAO.editPaper(this, paper_id);
        if ( done ) {
        	 message = "Success! Paper updated!";
             return "paper_index";
        }else {
             message = "Sorry! Could not update paper. Please try again!";
             return "paper_edit";
        }
    }
    
	
}

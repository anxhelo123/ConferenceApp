package dao;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dao.AuthorDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;


@ManagedBean
@RequestScoped 
public class Author {
	
	private String email, first_name, last_name, message;
	ArrayList authorList, authorPaperList;
	
	
	
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
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
	public ArrayList authorList(){
		authorList = AuthorDAO.authorList();
		
		return authorList;
	}
	
	public String list() {
		return "/user_panel/author?faces-redirect=true";
	}
	
	public ArrayList actorMoviesList(String au_email){
		authorPaperList = AuthorDAO.authorPaperList(au_email);
		return authorPaperList;
	}
	
	public String papers(String email) {
		String name = AuthorDAO.authorName(email);
		sessionMap.put("au_email", email);
		sessionMap.put("first_name", first_name);
		sessionMap.put("last_name", last_name);

		return "/user_panel/author_papers?faces-redirect=true";
	}

	public String adminList() {
    	return "/admin_panel/author_index?faces-redirect=true";
    }
	
	// Used to delete author record
    public void delete(String email){
        AuthorDAO.delete(email);
    }
    
 // Used to create new author record
    public String saveAuthor() {
    	AuthorDAO.save(this);
    	message = "Success! New author created!";
    	return "author_create";
    }
    
    // Used to edit author record
    public String edit(String email) {
    	Author a = AuthorDAO.edit(email);
    	this.first_name = a.getFirst_name();
    	this.last_name = a.getLast_name();
    	this.email = a.getEmail();
    	return "author_edit";
    }
    
    public String editAuthor(Author author) {
    	boolean done = AuthorDAO.editAuthor(author);
        if ( done ) {
        	 message = "Success! Author updated!";
             return "author_index";
        }else {
             message = "Sorry! Could not update author. Please try again!";
             return "author_edit";
        }
    }
    
    public String saveAssignAuthor(int paper_id) {
    	AuthorDAO.saveAssignAuthor(this.email,paper_id);
    	message = "Success! New assign author created!";
    	return "paper_assign_authors";
    }
}

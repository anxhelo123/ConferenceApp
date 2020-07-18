package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@ManagedBean
@RequestScoped 
public class User {

	ArrayList usersList ;
	Connection connection;
	private String username, name, lastname, password, fullname, email,mobile, message, newPassword, role,profession;
	private int id;
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
  
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String register() {
        boolean done = UserDAO.register(this);
        if ( done )
             return "login?faces-redirect=true";
        else {
             message = "Sorry! Could not register. Please try again!";
             return "register";
        }
    }

     public String login() {
        User u = UserDAO.login(username,password);
        if ( u != null ) {
            Util.addToSession("username", username);
            Util.addToSession("fullname", u.getFullname());
            if(u.getRole().equals("user")) {
            	if(u.getProfession().equals("author")) {
            		return "/user_panel/author_panel/authorDashboard?faces-redirect=true";				//simple user panel
            	}else {
            		return "/user_panel/reviewer_panel/reviewerDashboard?faces-redirect=true";
            	}
            	
            }
            else {
            	return "/admin_panel/adminHome?faces-redirect=true";				//admin user panel
            }
        }
        else {
             message = "Sorry! Invalid Login!";
             return "login";
        }
    }

     
    public void changePassword() {
        boolean done = UserDAO.changePassword(Util.getUsername(),password, newPassword);
        if (done) {
            message="Password has been changed successfully!";
        }
        else {
             message = "Sorry! Could not change password. Old passwod may be incorrect!";
        }
    }
     
    public String logout() {
        Util.terminateSession();
        return "/all/login?faces-redirect=true";
    }
   
    public void recoverPassword(ActionEvent evt){
        User  u = UserDAO.getUser(username, email);
        if( u == null) {
            message  = "Sorry! Could not find user with the given username or email address!";
            return;
        }
        
        // send mail with details
        
        String body = "Dear " + u.fullname + ",<p/>" +
                   "Please use the following details to login.<p/>" + 
                   "Username : " + u.username + "<br/>" +
                   "Password : " + u.password + "<p/>" +
                   "Team,<br/>ConferenceManagement.Com";
        
         try {
            Properties props = System.getProperties();
            Session session = Session.getDefaultInstance(props, null);
            // construct the message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("anxhelolame@gmail.com"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(u.email));
            msg.setDataHandler(new DataHandler(new String(body), "text/html"));
            msg.setSubject("Password Recovery");
            // send message
            Transport.send(msg);
            message="A mail has been sent with your details. Please use those details to login again!";
        } catch (Exception ex) {
            System.out.println("Error sending mail : " + ex.getMessage());
            message="Sorry! Could not send mail! Please try again!";
        }      
    }

    @Override
    public String toString() {
        return "User{" + "uname=" + username + ", password=" + password + ", fullname=" + fullname + ", email=" + email + ", mobile=" + mobile + ",profession=" + profession +", message=" + message + ", newPassword=" + newPassword + '}';
    }
    
    public String profile() {
    	User u = UserDAO.getProfile();

    	this.name = u.getName();
		this.lastname = u.getLastname();
		this.email = u.getEmail();
		this.mobile = u.getMobile();
		this.fullname = u.getFullname();

		return "profile";
    }
    
    public String update() {
    	boolean done = UserDAO.update(this);
        if ( done ) {
        	 message = "Success! Your profile updated!";
             return "profile";
        }else {
             message = "Sorry! Could not update your profile. Please try again!";
             return "profile";
        }
    }
    
    // Used to fetch all records
    public ArrayList usersList(){
        usersList = UserDAO.usersList();
 
        return usersList;
    }

    public String adminList() {
    	return "/admin_panel/user_index?faces-redirect=true";
    }
    
    // Used to delete user record
    public void delete(int id){
        UserDAO.delete(id);
    }
    
    // Used to create new user record
    public String saveUser() {
    	UserDAO.save(this);
    	message = "Success! New user created!";
    	return "user_create";
    }
    
    // Used to edit user record
    public String edit(int user_id) {
    	User user = UserDAO.edit(user_id);
    	this.id = user_id;
    	this.name = user.getName();
    	this.lastname=user.getLastname();
    	this.fullname = user.getFullname();
    	this.username = user.getUsername();
    	this.email = user.getEmail();
    	this.password = user.getPassword();
    	this.mobile = user.getMobile();
    	this.role = user.getRole();
    	this.profession = user.getProfession();
    	return "user_edit";
    }
    
    public String editUser(int id) {
    	boolean done = UserDAO.editUser(this, id);
        if ( done ) {
        	 message = "Success! User updated!";
             return "user_index";
        }else {
             message = "Sorry! Could not update user. Please try again!";
             return "user_edit";
        }
    }
		
	
}

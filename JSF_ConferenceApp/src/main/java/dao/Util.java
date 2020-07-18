package dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class Util implements Serializable {
    

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	public static String getParameter(String name) {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		return params.get(name);
	}

	public static void addToSession(String key, String value) {
		HttpSession s = getSession();
		s.setAttribute(key, value);
	}

	public static String getFullname() {
		HttpSession s = getSession();
		return s.getAttribute("fullname").toString();
	}

	public static String getUsername() {
		HttpSession s = getSession();
		return s.getAttribute("username").toString();
	}

	public static void terminateSession() {
		HttpSession s = getSession();
		s.invalidate();
	}
    
    public void validateEmail(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {
        String emailStr = (String) value;
        if (-1 == emailStr.indexOf("@")) {
            FacesMessage message = new FacesMessage("Invalid email address");
            throw new ValidatorException(message);
        }
    }

    public String addConfirmedUser() {
        // This method would call a database or other service and add the 
        // confirmed user information.
        // For now, we just place an informative message in request scope
        FacesMessage doneMessage = 
                new FacesMessage("Successfully added new user");
        FacesContext.getCurrentInstance().addMessage(null, doneMessage);
        return "done";
    }
}


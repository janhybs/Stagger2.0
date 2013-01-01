package cz.edu.x3m.steps;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import cz.edu.x3m.Main;
import cz.edu.x3m.Prefs;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author Jan
 */
public class LoginStep {

    protected HtmlPage page;

    public LoginStep(HtmlPage page) {
        this.page = page;
        Main.LOGGER.log(Level.INFO, "Is logged In: {0}", !hasLoginForm());
    }

    public HtmlPage login() {
        try {
            HtmlForm form = getLoginForm();

            Main.LOGGER.log(Level.INFO, "Filling");
            form.getInputByName("wps.portlets.userid").setValueAttribute(Prefs.USERNAME);
            form.getInputByName("password").setValueAttribute(Prefs.PASSWORD);
            DomNodeList<HtmlElement> inputs = form.getElementsByTagName("input");
            Main.LOGGER.log(Level.INFO, "Loggining");
            HtmlPage loggedInPage = (HtmlPage) inputs.get(2).click();

            return loggedInPage;
        } catch (IOException ex) {
            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public HtmlForm getLoginForm() {
        try {
            return page.getFormByName("LoginForm");
        } catch (ElementNotFoundException ex) {
            Main.LOGGER.log(Level.INFO, "no login form");
            return null;
        }
    }

    public final boolean hasLoginForm() {
        return getLoginForm() != null;
    }
}

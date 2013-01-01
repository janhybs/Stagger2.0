package cz.edu.x3m;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.logging.Level;

/**
 *
 * @author Jan
 */
public class Client extends WebClient {
    
    public Client() {
        super(BrowserVersion.FIREFOX_3_6);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        getOptions().setThrowExceptionOnScriptError(false);
        getOptions().setThrowExceptionOnFailingStatusCode(false);
        getOptions().setCssEnabled(false);
    }

    public HtmlPage getLoginPage() {
        try {
            Main.LOGGER.log(Level.INFO, "Loading login page");
            return (HtmlPage) getPage("http://stag-new.tul.cz/wps/portal/");
        } catch (Exception ex) {
            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public HtmlPage getSendEmailPage(Result result) {
        try {
            Main.LOGGER.log(Level.INFO, "Loading send e-mail page");
            return (HtmlPage) getPage("http://stagger.jan-hybs.cz/?to=" + Prefs.EMAIL + "&change=" + result.type.toString());
        } catch (Exception ex) {
            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }
}

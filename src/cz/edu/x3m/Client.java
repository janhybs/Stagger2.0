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

    public HtmlPage getSendEmailPage(StageChangeResult result) {
        StringBuilder changes = new StringBuilder();
        changes.append("?to=");
        changes.append(Prefs.EMAIL);
        changes.append("&change=");
        changes.append(result.changes.size());
        changes.append("&info=");

        for (int i = 0; i < result.changes.size(); i++) {
            changes.append("\t");
            changes.append(result.changes.get(i).toString());
            changes.append("\r\n");
        }

        try {
            Main.LOGGER.log(Level.INFO, "Loading send e-mail page");
            return (HtmlPage) getPage("http://stagger.jan-hybs.cz/" + changes.toString());
        } catch (Exception ex) {
            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }
}

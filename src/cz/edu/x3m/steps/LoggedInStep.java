package cz.edu.x3m.steps;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import cz.edu.x3m.Main;
import cz.edu.x3m.net.objects.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Jan
 */
public class LoggedInStep extends LoginStep {

    public LoggedInStep(HtmlPage page) {
        super(page);
    }

    public List<Subject> getTerms() {
        Main.LOGGER.log(Level.INFO, "Parsing");
        List<DomElement> tds = page.getElementsByTagName("td");
        List<Subject> items = new ArrayList<>();

        for (int i = 0; i < tds.size(); i++) {
            DomElement td = tds.get(i);
            if (td.hasAttribute("class") && td.getAttribute("class").equals("zpazk_bothead")) {
                items.add(new Subject(tds.get(i++), tds.get(i++), tds.get(i++)));
            }
        }
        return items;
    }
}

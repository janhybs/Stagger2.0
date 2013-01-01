package cz.edu.x3m;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import cz.edu.x3m.net.objects.Subject;
import cz.edu.x3m.steps.LoggedInStep;
import cz.edu.x3m.steps.LoginStep;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class Main {

    public static final Logger LOGGER = Logger.getLogger("cz.edu.x3m");
    public static final File DATA = new File(".terms");
    public static final long SLEEP_TIME = 5 * 1000;

    public Main() throws InterruptedException {
        while (true) {
            doWork();
            Main.LOGGER.log(Level.INFO, "sleeping");
            synchronized (this) {
                wait(SLEEP_TIME);
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Main();
    }

    private boolean doWork() {
        Client client = new Client();
        HtmlPage LoginPage = client.getLoginPage();

        LoginStep loginStep = new LoginStep(LoginPage);
        HtmlPage loggedInPage = loginStep.login();

        LoggedInStep loggedInStep = new LoggedInStep(loggedInPage);
        List<cz.edu.x3m.net.objects.Subject> currTerms = loggedInStep.getTerms();


        List<Subject> prevTerms = loadPrevTerms();
        Result result = TermComparator.compareSubjects(prevTerms, currTerms);

        Main.LOGGER.log(Level.INFO, result.type.toString());
        if (result.type == Result.ResultType.NO_CHANGE) {
            return false;
        } else {
            saveCurrTerms(currTerms);
            sendEMail(result);
            return true;
        }


    }

    private List<Subject> loadPrevTerms() {
        BufferedReader reader = null;
        List<Subject> result = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(DATA));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                result.add(new Subject(line));
            }
            return result;
        } catch (IOException ex) {
            return result;
        }
    }

    private void saveCurrTerms(List<Subject> items) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(DATA));
            for (int i = 0; i < items.size(); i++) {
                Subject subject = items.get(i);
                writer.write(subject.asOutput());
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            return;
        }
    }

    private void sendEMail(Result result) {
        Client c = new Client();
        c.getSendEmailPage(result);
    }
}

package cz.edu.x3m;

import cz.edu.x3m.net.objects.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jan
 */
public class TermComparator {

    public static StageChangeResult compareSubjects(List<Subject> prev, List<Subject> curr) {
        final int prevSize = prev.size();
        final int currSize = curr.size();
        final StageChangeResult r = new StageChangeResult();
        final Map<String, Boolean> checked = new HashMap<>();


        for (int i = 0; i < prevSize; i++) {
            Subject ps = prev.get(i);
            boolean subjectFound = false;
            checked.put(ps.acronym, true);

            for (int j = 0; j < currSize; j++) {
                Subject cs = curr.get(j);

                if (ps.isSameSubjectAs(cs)) {
                    if (cs.count > ps.count) {
                        r.changes.add(new StagChange(cs, StagChange.StagChangeType.TERM_ADDED, ps.count, cs.count));
                    } else if (cs.count < ps.count) {
                        r.changes.add(new StagChange(cs, StagChange.StagChangeType.TERM_REMOVED, ps.count, cs.count));
                    }
                    subjectFound = true;
                    break;
                }
            }

            if (!subjectFound) {
                r.changes.add(new StagChange(ps, StagChange.StagChangeType.SUBJECT_REMOVED));
            }
        }

        for (int j = 0; j < currSize; j++) {
            Subject cs = curr.get(j);
            if (!checked.containsKey(cs.acronym)) {
                r.changes.add(new StagChange(cs, StagChange.StagChangeType.SUBJECT_ADDED));
            }
        }

        return r;
    }
}

class StagChange {

     enum StagChangeType {

        NO_CHANGE {
            @Override
            public String toString() {
                return "No change";
            }
        }, SUBJECT_ADDED {
            @Override
            public String toString() {
                return "Subject added";
            }
        }, SUBJECT_REMOVED {
            @Override
            public String toString() {
                return "Subject removed";
            }
        }, TERM_ADDED {
            @Override
            public String toString() {
                return "Term added";
            }
        }, TERM_REMOVED {
            @Override
            public String toString() {
                return "Term removed";
            }
        }
    }
    public final Subject subject;
    public final Object oldValue, newValue;
    public final StagChangeType type;
    private boolean hasValues = true;

    public StagChange(Subject subject, StagChangeType type, Object oldValue, Object newValue) {
        this.subject = subject;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.type = type;
    }

    public StagChange(Subject subject, StagChangeType type) {
        this.subject = subject;
        this.oldValue = "";
        this.newValue = "";
        this.type = type;
        hasValues = false;
    }

    @Override
    public String toString() {
        if (hasValues) {
            return String.format("%s: %s, %s -> %s", type.toString(), subject.acronym,
                    oldValue.toString(), newValue.toString());
        }
        return String.format("%s: %s", type.toString(), subject.acronym);
    }
}

class StageChangeResult {

    public final ArrayList<StagChange> changes = new ArrayList<>();

    @Override
    public String toString() {
        return changes.toString();
    }
}
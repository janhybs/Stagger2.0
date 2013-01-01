package cz.edu.x3m;

import cz.edu.x3m.steps.Subject;
import java.util.List;

/**
 *
 * @author Jan
 */
public class TermComparator {

    public static Result compareSubjects(List<Subject> prev, List<Subject> curr) {
        final int prevSize = prev.size();
        final int currSize = curr.size();

        if (currSize > prevSize) {
            return new Result(Result.ResultType.MORE_SUBJECTS);
        } else if (currSize < prevSize) {
            return new Result(Result.ResultType.LESS_SUBJECTS);
        }


        for (int i = 0; i < prevSize; i++) {
            Subject ps = prev.get(i);

            for (int j = 0; j < curr.size(); j++) {
                Subject cs = curr.get(j);

                if (ps.isSameSubjectAs(cs)) {
                    if (cs.count > ps.count) {
                        return new Result(Result.ResultType.MORE_TERMS);
                    } else if (cs.count < ps.count) {
                        return new Result(Result.ResultType.LESS_TERMS);
                    }
                }
            }
        }

        return new Result(Result.ResultType.NO_CHANGE);
    }
}

class Result {

    enum ResultType {

        MORE_SUBJECTS, LESS_SUBJECTS, MORE_TERMS, LESS_TERMS, NO_CHANGE
    }
    public final ResultType type;

    public Result(ResultType type) {
        this.type = type;
    }
}

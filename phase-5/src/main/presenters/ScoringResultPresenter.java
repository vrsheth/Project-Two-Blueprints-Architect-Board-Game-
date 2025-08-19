package main.presenters;

import main.exceptions.checked.ScoringResultPresenterException;
import main.scoringresult.ScoringResult;

public interface ScoringResultPresenter {

    void present(ScoringResult result) throws ScoringResultPresenterException;
}

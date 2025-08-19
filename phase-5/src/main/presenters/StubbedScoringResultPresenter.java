package main.presenters;

import main.exceptions.checked.ScoringResultPresenterException;
import main.scoringresult.ScoringResult;

/**
 * Illustrates how a class can implement the ScoringResultPresenter
 * interface.
 * 
 * Stubs out the interface's only method in a very simple way.
 */
public class StubbedScoringResultPresenter implements ScoringResultPresenter {

    @Override
    public void present(ScoringResult result) throws ScoringResultPresenterException {
        System.out.println("Presenting " + result);
    }
}

package algorithms.search;

import java.util.ArrayList;

public class Solution {
    private ArrayList<AState> solutionPath;

    public Solution() {
        this.solutionPath = new ArrayList<>();
    }

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    // FUNC that check the state and add to the solution path
    public void addState(AState state){
        if (state == null)
            return;
        solutionPath.add(state);

    }
}

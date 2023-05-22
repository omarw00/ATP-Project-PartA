package algorithms.search;
import java.util.*;
public class BreadthFirstSearch extends ASearchingAlgorithm{
    protected Queue<AState> openlist;


    public BreadthFirstSearch() {
        super();
        this.openlist = new LinkedList<>();

    }

    public BreadthFirstSearch(Queue<AState> openlist) {
        super();
        this.openlist = openlist;
    }

    @Override
    public Solution solve(ISearchable domain) {
        openlist.clear();
        if (domain == null)
            return null;
        AState start = domain.getStartState();
        AState end = domain.getGoalState();
        if (start == null || end == null)
            return null;
        HashMap<String,AState> map = new HashMap<>();
        openlist.add(start);
        AState currentNode = null;
        while (!openlist.isEmpty()){
            setCountvisited(getCountvisited()+1);
            currentNode = openlist.poll();
            map.put(currentNode.toString(),currentNode);

            if(currentNode.equals(end)){
                //TODO: changed!
                end.setLastmove(currentNode.getLastmove());
                break;
            }
            ArrayList<AState> neigbors = domain.getAllPossibleStates(currentNode);
            for (AState state : neigbors){
                //TODO: if the list contains that state dont add!!!!  DONE!
                if (!map.containsKey(state.toString())){
                    openlist.add(state);
                    state.setLastmove(currentNode);
                }



            }
        }
        return solutionPath(domain);
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}

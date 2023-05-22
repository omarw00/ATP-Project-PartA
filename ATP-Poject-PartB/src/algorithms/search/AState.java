package algorithms.search;


import java.io.Serializable;

public abstract class AState implements Serializable {
    private int cost;
    private AState lastmove;
    private boolean isVisited;
    //private Object position;

    public AState(int cost) {
        this.cost = cost;
        lastmove = null;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setLastmove(AState lastmove) {
        this.lastmove = lastmove;
    }

    public AState getLastmove() {
        return lastmove;
    }

    public int getCost() {
        return cost;
    }

    //public abstract Object getStatePosition();
    public void setCost(int cost) {
        this.cost = cost;
    }
    public abstract String toString();
    @Override
    public abstract boolean equals(Object obj);

}

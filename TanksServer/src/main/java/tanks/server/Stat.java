package tanks.server;

public class Stat {
    private int id;
    private int shots;
    private int hits;
    private int misses;

    public Stat(int id, int shots, int hits, int misses) {
        this.id = id;
        this.shots = shots;
        this.hits = hits;
        this.misses = misses;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

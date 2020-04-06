package Model;

public class PacketPrice implements Comparable<PacketPrice> {
    private int noItems;
    private double cost;

    public PacketPrice(int noItems, double cost) {
        this.noItems = noItems;
        this.cost = cost;
    }

    public int getNoItems() {
        return noItems;
    }

    public void setNoItems(int noItems) {
        this.noItems = noItems;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    //todo i have to remove if it is not needed
    @Override
    public int compareTo(PacketPrice o) {
        return Integer.compare(this.noItems, o.getNoItems());
    }
}

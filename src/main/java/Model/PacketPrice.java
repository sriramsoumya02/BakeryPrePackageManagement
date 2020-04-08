package Model;

/**
 * PacketPrice class contains packet related information
 *
 * @author Soumya Sriram
 */
public class PacketPrice {
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

}

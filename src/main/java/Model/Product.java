package Model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Product {
    private String productName;
    private String productCode;
    private Map<Integer, PacketPrice> packets;

    public Product(String productName, String productCode) {
        this.productName = productName;
        this.productCode = productCode;
        packets = new HashMap<Integer, PacketPrice>();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void addPacket(PacketPrice packet) {
        packets.put(packet.getNoItems(), packet);
    }

    public boolean removePacket(int packSize) {
        if (packets.containsKey(packSize)) {
            packets.remove(packSize);
            return true;
        }
        return false;
    }

    public List<Integer> sortedPacketSizeList() {
        return packets.keySet().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    public double getPriceOfPacket(int packSize) {
        if (packets.containsKey(packSize))
            return packets.get(packSize).getCost();
        else
            return 0.0d;
    }
}

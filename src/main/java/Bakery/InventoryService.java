package Bakery;

import Exceptions.InvalidFileName;
import Model.PacketPrice;
import Model.Product;
import Util.CSVReader;
import Util.Constants;

import java.util.Map;
import java.util.stream.Collectors;

public class InventoryService {
    private static InventoryService instance;
    private Map<String, Product> availableProducts;

    private InventoryService(String productFile, String packetsFile) {
        loadProducts(productFile);
        loadPackets(packetsFile);
    }

    public static InventoryService getInstance() {
        if (instance == null)
            instance = new InventoryService(Constants.PRODUCT_FILE, Constants.PACKET_FILE);
        return instance;
    }

    private void loadProducts(String productFile) {
        try {
            availableProducts = CSVReader.readFileData(productFile).stream()
                    .map(this::createProduct).collect(Collectors.toMap(Product::getProductCode, product -> product));
        } catch (InvalidFileName invalidFileName) {
            invalidFileName.getMessage();
        }
    }

    private Product createProduct(String productInfo) {
        String[] productDetails = productInfo.split(Constants.CSV_SPLIT);
        return new Product(productDetails[0], productDetails[1]);
    }

    private void loadPackets(String packetsFile) {
        try {
            CSVReader.readFileData(packetsFile).forEach(this::createPacket);
        } catch (InvalidFileName invalidFileName) {
            invalidFileName.getMessage();
        }
    }

    private void createPacket(String packetInfo) {
        String[] packetDetails = packetInfo.split(Constants.CSV_SPLIT);
        PacketPrice packetPrice = new PacketPrice(Integer.parseInt(packetDetails[1]), Double.parseDouble(packetDetails[2]));
        availableProducts.get(packetDetails[0]).addPacket(packetPrice);
    }

    public Product getProduct(String productCode) {
        return availableProducts.get(productCode);
    }
}

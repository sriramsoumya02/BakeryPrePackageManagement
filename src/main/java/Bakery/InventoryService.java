package Bakery;

import Exceptions.InvalidFileName;
import Model.PacketPrice;
import Model.Product;
import Util.CSVReader;
import Util.Constants;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * The InventoryService program takes care of available products and packet sizes related information
 *
 * @author Soumya Sriram
 * @since @2020-04-06
 */
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

    /**
     * loadProducts -loads the product information from file
     *
     * @param productFile product filename
     */
    private void loadProducts(String productFile) {
        try {
            availableProducts = CSVReader.readFileData(productFile).stream()
                    .map(this::createProduct).collect(Collectors.toMap(Product::getProductCode, product -> product));
        } catch (InvalidFileName invalidFileName) {
            invalidFileName.getMessage();
        }
    }

    /**
     * createProduct -creates products with product information
     *
     * @param productInfo contains products info productName,productCode
     * @return Product object
     */
    private Product createProduct(String productInfo) {
        String[] productDetails = productInfo.split(Constants.CSV_SPLIT);
        return new Product(productDetails[0], productDetails[1]);
    }

    /**
     * loadPackets -loads the packetPrice information from file
     *
     * @param packetsFile contains packet file name
     */
    private void loadPackets(String packetsFile) {
        try {
            CSVReader.readFileData(packetsFile).forEach(this::createPacket);
        } catch (InvalidFileName invalidFileName) {
            invalidFileName.getMessage();
        }
    }

    /**
     * createPacket -creates packetPrice object with packet information and maps that with respective product in available products
     *
     * @param packetInfo contains packet info productCode,packetSize,Price
     */
    private void createPacket(String packetInfo) {
        String[] packetDetails = packetInfo.split(Constants.CSV_SPLIT);
        PacketPrice packetPrice = new PacketPrice(Integer.parseInt(packetDetails[1]), Double.parseDouble(packetDetails[2]));
        if (availableProducts.containsKey(packetDetails[0]))
            availableProducts.get(packetDetails[0]).addPacket(packetPrice);
    }

    /**
     * getProduct -returns available product
     *
     * @param productCode - product code
     * @return Product object
     */
    public Product getProduct(String productCode) {
        return availableProducts.get(productCode);
    }

    /**
     * isValidProduct -checks whether the product is available or not
     *
     * @param productCode - product code
     * @return boolean
     */
    public boolean isValidProduct(String productCode) {
        return availableProducts.containsKey(productCode);
    }
}

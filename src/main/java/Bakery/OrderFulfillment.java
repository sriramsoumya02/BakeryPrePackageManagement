package Bakery;

import Model.Product;
import Util.Constants;
import Util.Validations;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The OrderFulfillment program takes care of all the related order fulfillment process
 * like taking order,prepackaging,and printing bill
 *
 * @author Soumya Sriram
 * @since @2020-04-06
 */
public class OrderFulfillment {
    private InventoryService inventoryService;
    private List<String> userInput;


    public OrderFulfillment(List<String> userInput) {
        inventoryService = InventoryService.getInstance();
        this.userInput = userInput;
    }

    /**
     * processOrderByProduct - processes every product of the order and validates it
     * if product is valid then it forwards the request
     * else it gives proper error message to the user
     *
     * @return String order-bill
     */
    public String processOrderByProduct() {
        StringBuilder bill = new StringBuilder();
        for (String s : userInput) {
            String[] productDetails = s.split(Constants.CSV_SPLIT);
            if (productDetails.length >= 2 && Validations.isParsableInteger(productDetails[0]) && (Integer.parseInt(productDetails[0]) > 0) && inventoryService.isValidProduct(productDetails[1])) {
                int quantity = Integer.parseInt(productDetails[0]);
                String productCode = productDetails[1];
                Product product = inventoryService.getProduct(productCode);
                List<Integer> packetsList = product.sortedPacketSizeList();
                if (packetsList.size() > 0) {
                    Map<Integer, Integer> productPacketCount = getProductPacketCombination(packetsList, quantity);
                    bill.append(printBill(productPacketCount, product, quantity));
                } else {
                    bill.append(Constants.NEW_LINE).append(productCode).append(Constants.SPACE).append(Constants.COLON).append(Constants.SPACE).append(Constants.PRODUCTS_NOT_AVAILABLE);
                }
            } else {
                bill.append(Constants.NEW_LINE).append(s).append(Constants.SPACE).append(Constants.COLON).append(Constants.SPACE).append(Constants.VALID_PRODUCT_DETAILS);
            }
        }
        return bill.toString();
    }

    /**
     * getTotalAmountByProduct-it gives total amount for each product of the order
     *
     * @param product            takes product object
     * @param productPacketCount takes productPacketValues and related quantity
     * @return double total amount for each product of the order
     */
    private double getTotalAmountByProduct(Map<Integer, Integer> productPacketCount, Product product) {
        double totalAmount = 0;
        for (Map.Entry<Integer, Integer> entry : productPacketCount.entrySet()) {

            totalAmount = totalAmount + entry.getValue() * product.getPriceOfPacket(entry.getKey());
        }
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;
        return totalAmount;
    }

    /**
     * printBill- gives information to be printed in the console
     *
     * @param product            takes product object
     * @param productPacketCount takes productPacketValues and related quantity
     * @param quantity           integer
     * @return String information to be printed in the console
     */
    private String printBill(Map<Integer, Integer> productPacketCount, Product product, int quantity) {
        StringBuilder bill = new StringBuilder();
        String productCode = product.getProductCode();
        if (productPacketCount.containsKey(0))
            bill.append(Constants.NEW_LINE).append(productCode).append(Constants.SPACE).append(Constants.COLON).append(Constants.SPACE).append(Constants.INVALID_QUANTITY);
        else {
            double totalAmountByProduct = getTotalAmountByProduct(productPacketCount, product);
            bill.append(Constants.NEW_LINE).append(quantity).append(Constants.SPACE).append(productCode).append(Constants.SPACE).append(Constants.CURRENCY).append(totalAmountByProduct);
            productPacketCount.forEach((k, v) -> bill.append(Constants.NEW_LINE).append(Constants.TAB).append(v).append(Constants.SPACE).append(Constants.CROSS).append(Constants.SPACE).append(k).append(Constants.SPACE).append(Constants.CURRENCY).append(product.getPriceOfPacket(k)));

        }
        return bill.toString();
    }

    /**
     * getProductPacketCombination- it processes every order by product code and gives product packets quantities
     *
     * @param packets  list of packets available for that product
     * @param quantity quantity for that product
     * @return Map     gives product packets quantities
     */
    private Map<Integer, Integer> getProductPacketCombination(List<Integer> packets, int quantity) {
        Prepackaging processor = new Prepackaging(packets);
        int remainingQuantity = processor.determinePacketsCount(quantity, 0);
        Map<Integer, Integer> productPacketCombination = processor.getPacketsCount();
        if (remainingQuantity > 0) {
            productPacketCombination.clear();
            productPacketCombination.put(0, 0);
        }
        return productPacketCombination;
    }

    /**
     * Prepackaging class takes care of determining the packet value combinations for each product
     */
    private static class Prepackaging {
        private Map<Integer, Integer> packetsCount =
                new TreeMap<>(Collections.reverseOrder());
        List<Integer> packets;

        public Prepackaging(List<Integer> packets) {
            this.packets = packets;
        }

        public Map<Integer, Integer> getPacketsCount() {
            return packetsCount;
        }

        /**
         * determinePacketsCount - processes every order by product code and
         * gives whether this quantity combination can be achieved with available packet sizes
         * stores packet size value-packet count information in packetsCount variable
         * Process
         * 1.if quantity is divisible by packet size & still some more quantity to process --> store it in map
         * 1.1 if there are other other packetsSizes available -->process with that packet size for the remaining quantity
         * 1.2  still there is quantity to process --> reduce the packet size value and process again
         * 2 if quantity is not divisible by packet size -->continue 1.1
         * 3.if there is no quantity to process -->stop and return
         * 4.still we have some quantity to process -->return quantity
         *
         * @param quantity/remaining quantity
         * @param idx                -index from which it has to process.
         * @return integer-whether this quantity combination can be achived with available packets or not
         */
        public int determinePacketsCount(int quantity, int idx) {
            int currentPacket = packets.get(idx);
            int noOfPackets = quantity / currentPacket;
            int remainingQuantity = quantity % currentPacket;
            int size = packets.size();
            if (remainingQuantity > 0 && noOfPackets > 0) {
                if (packetsCount.containsKey(currentPacket))
                    packetsCount.replace(currentPacket, noOfPackets + packetsCount.get(currentPacket));
                else
                    packetsCount.put(currentPacket, noOfPackets);
                if (idx + 1 < size) {
                    idx++;
                    quantity = remainingQuantity;
                    remainingQuantity = determinePacketsCount(quantity, idx);
                    if (remainingQuantity > 0) {
                        quantity = remainingQuantity + currentPacket;
                        int packetCount = packetsCount.get(currentPacket);
                        if (packetCount > 1) {
                            packetsCount.replace(currentPacket, packetCount - 1);
                        } else {
                            packetsCount.remove(currentPacket);
                        }

                        remainingQuantity = determinePacketsCount(quantity, idx);
                    }
                }
                return remainingQuantity;
            } else if (noOfPackets == 0) {
                if (idx + 1 < size) {
                    idx++;
                    remainingQuantity = determinePacketsCount(quantity, idx);
                    return remainingQuantity;
                } else {
                    return remainingQuantity;
                }
            } else if (remainingQuantity == 0) {
                if (packetsCount.containsKey(currentPacket))
                    packetsCount.replace(currentPacket, noOfPackets + packetsCount.get(currentPacket));
                else
                    packetsCount.put(currentPacket, noOfPackets);
                return remainingQuantity;
            }
            return remainingQuantity;
        }

    }
}

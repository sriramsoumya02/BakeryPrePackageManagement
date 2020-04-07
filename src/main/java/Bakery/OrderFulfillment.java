package Bakery;

import Model.Product;
import Util.Constants;
import Util.Validations;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderFulfillment {
    private InventoryService inventoryService;
    private List<String> userInput;


    public OrderFulfillment(List<String> userInput) {
        inventoryService = InventoryService.getInstance();
        this.userInput = userInput;
    }

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

    private double getTotalAmountByProduct(Map<Integer, Integer> productPacketCount, Product product) {
        double totalAmount = 0;
        for (Map.Entry<Integer, Integer> entry : productPacketCount.entrySet()) {

            totalAmount = totalAmount + entry.getValue() * product.getPriceOfPacket(entry.getKey());
        }
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;
        return totalAmount;
    }

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

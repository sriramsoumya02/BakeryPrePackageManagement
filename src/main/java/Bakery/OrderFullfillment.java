package Bakery;

import Model.Product;
import Util.Constants;
import Util.Validations;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderFullfillment {
    private InventoryService inventoryService;
    private List<String> userInpt;


    public OrderFullfillment(List<String> userInpt) {
        inventoryService = InventoryService.getInstance();
        this.userInpt = userInpt;
    }

    public String processOrderByProduct() {
        StringBuffer bill = new StringBuffer();
        for (String s : userInpt) {
            String[] productDetails = s.split(Constants.CSV_SPLIT);
            if (productDetails.length >= 2 && Validations.isParsableInteger(productDetails[0]) && (Integer.parseInt(productDetails[0]) > 0) && inventoryService.isValidProduct(productDetails[1])) {
                int quantity = Integer.parseInt(productDetails[0]);
                String productCode = productDetails[1];
                Product product = inventoryService.getProduct(productCode);
                List<Integer> packetslist = product.sortedPacketSizelist();
                if (packetslist.size() > 0) {
                    Map<Integer, Integer> eachPacketCount = getOrderresult(packetslist, quantity);
                    bill.append(printBill(eachPacketCount, product, quantity));
                } else {
                    bill.append(Constants.NEW_LINE).append(productCode).append(Constants.SPACE).append(Constants.COLON).append(Constants.SPACE).append(Constants.PRODUCTS_NOT_AVAILABLE);
                }
            } else {
                bill.append(Constants.NEW_LINE).append(s).append(Constants.SPACE).append(Constants.COLON).append(Constants.SPACE).append(Constants.VALID_PRODUCT_DETAILS);
            }
        }
        return bill.toString();
    }

    private double getTotalCountByProduct(Map<Integer, Integer> eachPacketCount, Product product) {
        double totalAmount = 0;
        for (Map.Entry<Integer, Integer> entry : eachPacketCount.entrySet()) {

            totalAmount = totalAmount + entry.getValue() * product.getPriceofPacket(entry.getKey());
        }
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;
        return totalAmount;
    }

    private String printBill(Map<Integer, Integer> eachPacketCount, Product product, int quantity) {
        StringBuffer bill = new StringBuffer();
        String productCode = product.getProductCode();
        if (eachPacketCount.containsKey(0))
            bill.append(Constants.NEW_LINE).append(productCode).append(Constants.SPACE).append(Constants.COLON).append(Constants.SPACE).append(Constants.INVALID_QUANTITY);
        else {
            double totalAmount = getTotalCountByProduct(eachPacketCount, product);
            bill.append(quantity).append(productCode).append(Constants.SPACE).append(Constants.CURRENCY).append(totalAmount);
            eachPacketCount.forEach((k, v) -> {
                bill.append(Constants.NEW_LINE).append(Constants.TAB).append(v).append(Constants.SPACE).append(Constants.CROSS).append(k).append(Constants.CURRENCY).append(product.getPriceofPacket(k));
            });
            bill.append(Constants.NEW_LINE);
        }
        return bill.toString();
    }

    private Map<Integer, Integer> getOrderresult(List<Integer> packets, int quantity) {
        Prepacking processor = new Prepacking(packets);
        int remainingQuantity = processor.determinePackageCount(quantity, 0);
        Map<Integer, Integer> orderResult = processor.getpacketsCount();
        if (remainingQuantity > 0) {
            orderResult.clear();
            orderResult.put(0, 0);
        }
        return orderResult;
    }

    private class Prepacking {
        private Map<Integer, Integer> packetsCount =
                new TreeMap<Integer, Integer>(Collections.reverseOrder());
        List<Integer> packets;

        public Prepacking(List<Integer> packets) {
            this.packets = packets;
        }

        public Map<Integer, Integer> getpacketsCount() {
            return packetsCount;
        }

        public int determinePackageCount(int quantity, int idx) {
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
                    remainingQuantity = determinePackageCount(quantity, idx);
                    if (remainingQuantity > 0) {
                        quantity = remainingQuantity + currentPacket;
                        int packetCount = packetsCount.get(currentPacket);
                        if (packetCount > 1) {
                            packetsCount.replace(currentPacket, packetCount - 1);
                        } else {
                            packetsCount.remove(currentPacket);
                        }

                        remainingQuantity = determinePackageCount(quantity, idx);
                        return remainingQuantity;
                    } else {
                        return remainingQuantity;
                    }
                } else {
                    return remainingQuantity;
                }
            } else if (noOfPackets == 0) {
                if (idx + 1 < size) {
                    idx++;
                    remainingQuantity = determinePackageCount(quantity, idx);
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

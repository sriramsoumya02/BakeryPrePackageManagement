package Bakery;

import Model.PacketPrice;
import Model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryServiceTest {

    @Test
    public void validateProductInformation() {
        InventoryService inventoryService = InventoryService.getInstance();
        Product expectedProduct = new Product("Croissant", "CF");
        expectedProduct.addPacket(new PacketPrice(3, 5.95));
        expectedProduct.addPacket(new PacketPrice(5, 9.95));
        expectedProduct.addPacket(new PacketPrice(9, 16.95));
        assertAll(() -> assertEquals(expectedProduct.getProductName(), inventoryService.getProduct("CF").getProductName()),
                () -> assertEquals(expectedProduct.sortedPacketSizelist(), inventoryService.getProduct("CF").sortedPacketSizelist()),
                () -> assertEquals(expectedProduct.getPriceofPacket(5), inventoryService.getProduct("CF").getPriceofPacket(5)));
    }
}

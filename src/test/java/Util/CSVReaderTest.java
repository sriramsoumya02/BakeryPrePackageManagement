package Util;

import Exceptions.InvalidFileName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVReaderTest {

    @Test
    void readValidFile() throws InvalidFileName {
        List<String> actual = CSVReader.readFileData(Constants.PRODUCT_FILE);
        List<String> expected = new ArrayList<String>();
        expected.add("VegemiteScroll,VS5");
        expected.add("BlueberryMuffin,MB11");
        expected.add("Croissant,CF");
        assertTrue(expected.equals(actual));
    }

    @Test
    void readInvalidFile() throws InvalidFileName {
        assertThrows(InvalidFileName.class, () -> CSVReader.readFileData("myname"));
    }
}

package Util;

import Exceptions.InvalidFileName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<String> readFileData(String filename) throws InvalidFileName {
        List<String> filedata = new ArrayList<String>();
        String line;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(CSVReader.class.getClassLoader().getResource(filename).getPath()));
            while ((line = br.readLine()) != null) {
                filedata.add(line);
            }
        } catch (Exception e) {
            throw new InvalidFileName(Constants.INVALID_FILE_NAME + " " + filename);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return filedata;
    }
}

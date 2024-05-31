package bg.tu_varna.sit.menageHistory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriteIntoFIle {

    /**
     * Writes the history of product changes to a specified file.<br>
     * This method is called every time a product is removed/added from the warehouse.<br>
     * It iterates through the map containing the changes and writes each <br>
     * entry to the file in a specific format.<br>
     * Also, I am using this file that is filled for later purpose,<br>
     * such as reading from it and output the history of removing in given date range!<br>
     * @param filename      The name of the file to write the changes to.
     * @param mapForChanges The map containing the changes, where the key is the date
     *                      and the value is another map with product names as keys and
     *                      quantities as values.
     * @throws IOException If an error occurs while writing to the file.
     */

    public void writeIntoFile(String filename, Map<String, Map<String, Double>> mapForChanges) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Map.Entry<String, Map<String, Double>> stringMapEntry : mapForChanges.entrySet()) {
                for (Map.Entry<String, Double> innerMap : stringMapEntry.getValue().entrySet()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(innerMap.getKey()).append(",").append(innerMap.getValue()).append(",").append(stringMapEntry.getKey()).append(System.lineSeparator());
                    writer.write(stringBuilder.toString());

                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save in file");
            throw new RuntimeException(e);
        }
    }
}

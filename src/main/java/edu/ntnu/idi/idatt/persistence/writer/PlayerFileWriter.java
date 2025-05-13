package edu.ntnu.idi.idatt.persistence.writer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * A utility class for writing player data to CSV files using Apache Commons CSV.
 *
 * @author Trang Duong
 * @version 0.2
 * @since 0.1
 */
public class PlayerFileWriter {
    /**
     * Writes data to a CSV file, optionally including headers.
     *
     * @param dataLines  List of rows (each row is a String array).
     * @param outputFile The CSV file to write to.
     * @param headers    Optional headers (null if not needed).
     * @throws IOException If writing fails.
     */
    public void writeToCSV(List<String[]> dataLines, File outputFile, String[] headers) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT;
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                CSVPrinter csvPrinter = new CSVPrinter(writer, format)
        ) {
            if (headers != null) {
                csvPrinter.printRecord((Object[]) headers);
            }
            for (String[] row : dataLines) {
                csvPrinter.printRecord((Object[]) row);
            }
            csvPrinter.flush();
        }
    }
}

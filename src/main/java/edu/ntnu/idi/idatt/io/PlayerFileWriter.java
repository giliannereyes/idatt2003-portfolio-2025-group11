package edu.ntnu.idi.idatt.io;

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
 * @version 0.1
 * @since 0.1
 */
public final class PlayerFileWriter {

    private PlayerFileWriter() {}

    /**
     * Writes data to a CSV file, optionally including headers.
     *
     * @param dataLines  List of rows (each row is a String array).
     * @param outputFile The CSV file to write to.
     * @param headers    Optional headers (null if not needed).
     * @throws IOException If writing fails.
     */
    public static void writeToCSV(List<String[]> dataLines, File outputFile, String[] headers) throws IOException {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        headers != null
                                ? CSVFormat.DEFAULT.withHeader(headers)
                                : CSVFormat.DEFAULT)
        ) {
            for (String[] row : dataLines) {
                csvPrinter.printRecord((Object[]) row);
            }
            csvPrinter.flush();
        }
    }
}

package edu.ntnu.idi.idatt.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for reading player data from CSV files using Apache Commons CSV.
 *
 * @author Trang Duong
 * @version 0.1
 * @since 0.1
 */
public class PlayerFileReader {
    /**
     * Reads a CSV file and returns the data as a list of String arrays (rows).
     * Assumes the file includes a header row (skips it).
     *
     * @param inputFile The CSV file to read from.
     * @return List of rows, each as a String array.
     * @throws IOException If reading fails.
     */
    public List<String[]> readFromCSV(File inputFile) throws IOException {
        List<String[]> records = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT)

        ) {
            for (CSVRecord record : parser) {
                String[] row = new String[record.size()];
                for (int i = 0; i < record.size(); i++) {
                    row[i] = record.get(i);
                }
                records.add(row);
            }
        }

        return records;
    }
}

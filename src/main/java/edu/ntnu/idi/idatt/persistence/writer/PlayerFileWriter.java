package edu.ntnu.idi.idatt.persistence.writer;

import edu.ntnu.idi.idatt.exceptions.PlayerDataSerializeException;
import edu.ntnu.idi.idatt.utils.Validation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

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
   * @param dataLines is a list of rows (each row is a String array).
   * @param outputFile is the CSV file to write to.
   * @param headers are optional headers (null if not needed).
   *
   * @throws IllegalArgumentException if the data lines or output file is {@code null}.
   * @throws PlayerDataSerializeException if an I/O error or CSV formatting error occurs.
   */
  public void writeToCsv(List<String[]> dataLines, File outputFile, String[] headers) {
    Validation.validateNonNull(dataLines, "Data lines");
    Validation.validateNonNull(outputFile, "Output file");
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
    } catch (IOException e) {
      throw new PlayerDataSerializeException(
            "Error writing player data due to I/O error: " + outputFile, e
      );
    } catch (SecurityException e) {
      throw new PlayerDataSerializeException(
            "Insufficient permissions to write player data to file: " + outputFile, e
      );
    } catch (Exception e) {
      throw new PlayerDataSerializeException(
            "Unexpected error writing player data to file: " + outputFile, e
      );
    }
  }
}

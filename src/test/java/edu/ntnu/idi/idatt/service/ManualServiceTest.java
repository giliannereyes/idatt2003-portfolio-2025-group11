package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.utils.Validation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ManualService}, verifying its behavior when loading manual text from resources.
 *
 * @version 0.1
 * @since 0.1
 * @author Trang Duong
 */
public class ManualServiceTest {
    private ManualService manualService = new ManualService();

    //------------ Positive test ------------

    /**
     * Verifies that the manual text is successfully loaded from a valid resource path.
     */
    @Test
    void testLoadManualTextSuccessfully() {
        String result = manualService.loadManualText("/manuals/sample_manual.txt");
        Validation.validateNonNull(result, "result should not be null");
        Validation.validateNonEmptyStr(result, "Manual text");
        assertTrue(result.contains("Welcome") || result.length() > 0, "Expected content in manual");
    }

    //------------ Negative test ------------

    /**
     * Verifies that loading a manual from a nonexistent path returns an appropriate error message.
     */
    @Test
    void testLoadManualTextWithNonexistentPathReturnsError() {
        String result = manualService.loadManualText("/manuals/nonexistent_file.txt");
        assertEquals("Could not load manual: Resource not found.", result);
    }

    /**
     * Verifies that loading a manual with a null path returns an appropriate error message.
     */
    @Test
    void testLoadManualTextWithNullPathReturnsError() {
        String result = manualService.loadManualText(null);
        assertEquals("Could not load manual: Resource not found.", result);
    }

    //------------ Edge case ------------

    /**
     * Verifies that loading a manual with an empty string path returns an appropriate error message.
     */
    @Test
    void testLoadManualTextWithEmptyPath() {
        String result = manualService.loadManualText("");
        assertEquals("Could not load manual: Resource not found.", result);
    }
}

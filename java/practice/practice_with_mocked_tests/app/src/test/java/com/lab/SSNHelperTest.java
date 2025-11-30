package com.lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for SSNHelper class.
 * Tests each validation method independently without mocking.
 * 
 * To test buggy versions, replace "new SSNHelper()" with the buggy class.
 * Available buggy helpers:
 * - BuggySSNHelperWrongLength: accepts length >= 11
 * - BuggySSNHelperAllowMonth0: allows month 0
 * - BuggySSNHelperAllowDayUpTo30: rejects day 31 (< 31 instead of <= 31)
 * - BuggySSNHelperIncorrectFormat: always returns true
 * - BuggySSNHelperIncorrectFormatFalse: always returns false
 * - BuggySSNHelperMessyLuhn: wrong Luhn calculation (digit -= 11 instead of -= 9)
 * - BuggySSNHelperAllowMonth13: allows month 13 (custom bug)
 */
public class SSNHelperTest {
    
    private SSNHelper getHelper() {
        return new SSNHelper();
        // return new BuggySSNHelperWrongLength();
        // return new BuggySSNHelperAllowMonth0();
        // return new BuggySSNHelperAllowDayUpTo30();
        // return new BuggySSNHelperIncorrectFormat();
        // return new BuggySSNHelperIncorrectFormatFalse();
        // return new BuggySSNHelperMessyLuhn();
        // return new BuggySSNHelperAllowMonth13();
    }
    
    // Test 1: Catches BuggySSNHelperWrongLength
    @Test
    public void isCorrectLengthShouldReturnFalseForTwelveCharacters() {
        SSNHelper helper = getHelper();
        assertFalse(helper.isCorrectLength("900101-00177"));
    }
    
    // Test 2: Catches BuggySSNHelperAllowMonth0
    @Test
    public void isValidMonthShouldReturnFalseForMonth00() {
        SSNHelper helper = getHelper();
        assertFalse(helper.isValidMonth("00"));
    }
    
    // Test 3: Catches BuggySSNHelperAllowDayUpTo30
    @Test
    public void isValidDayShouldReturnTrueForDay31() {
        SSNHelper helper = getHelper();
        assertTrue(helper.isValidDay("31"));
    }
    
    // Test 4: Catches BuggySSNHelperIncorrectFormat (always true)
    @Test
    public void isCorrectFormatShouldReturnFalseForInvalidFormat() {
        SSNHelper helper = getHelper();
        assertFalse(helper.isCorrectFormat("90010100017")); // Missing hyphen
    }
    
    // Test 5: Catches BuggySSNHelperIncorrectFormatFalse (always false)
    @Test
    public void isCorrectFormatShouldReturnTrueForValidFormat() {
        SSNHelper helper = getHelper();
        assertTrue(helper.isCorrectFormat("900101-0017"));
    }
    
    // Test 6: Catches BuggySSNHelperMessyLuhn
    @Test
    public void luhnIsCorrectShouldReturnTrueForValidSSN() {
        SSNHelper helper = getHelper();
        assertTrue(helper.luhnIsCorrect("900101-0017")); // Valid Luhn
    }
    
    // Coverage test 1: Valid length
    @Test
    public void isCorrectLengthShouldReturnTrueForElevenCharacters() {
        SSNHelper helper = getHelper();
        assertTrue(helper.isCorrectLength("900101-0017"));
    }
    
    // Coverage test 2: Month boundary - valid month
    @Test
    public void isValidMonthShouldReturnTrueForMonth12() {
        SSNHelper helper = getHelper();
        assertTrue(helper.isValidMonth("12"));
    }
    
    // Coverage test 3: Month boundary - invalid month
    @Test
    public void isValidMonthShouldReturnFalseForMonth13() {
        SSNHelper helper = getHelper();
        assertFalse(helper.isValidMonth("13"));
    }
    
    // Coverage test 4: Day boundary - valid day
    @Test
    public void isValidDayShouldReturnTrueForDay01() {
        SSNHelper helper = getHelper();
        assertTrue(helper.isValidDay("01"));
    }
    
    // Coverage test 5: Day boundary - invalid day
    @Test
    public void isValidDayShouldReturnFalseForDay32() {
        SSNHelper helper = getHelper();
        assertFalse(helper.isValidDay("32"));
    }
    
    // Coverage test 6: Invalid Luhn
    @Test
    public void luhnIsCorrectShouldReturnFalseForInvalidLuhn() {
        SSNHelper helper = getHelper();
        assertFalse(helper.luhnIsCorrect("900101-0018")); // Invalid Luhn checksum
    }
}

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
    
    private ISsnHelper getHelper() {
        // Toggle one implementation at a time (no production code changes):
        return new SSNHelperAdapter();
        // return new BuggySSNHelperWrongLengthAdapter();
        // return new BuggySSNHelperAllowMonth0Adapter();
        // return new BuggySSNHelperAllowDayUpTo30Adapter();
        // return new BuggySSNHelperIncorrectFormatAdapter();
        // return new BuggySSNHelperIncorrectFormatFalseAdapter();
        // return new BuggySSNHelperMessyLuhnAdapter();
        // return new BuggySSNHelperAllowMonth13Adapter();
    }
    
    // Test 1: Catches BuggySSNHelperWrongLength
    @Test
    public void isCorrectLengthShouldReturnFalseForTwelveCharacters() {
        ISsnHelper helper = getHelper();
        assertFalse(helper.isCorrectLength("900101-00177"));
    }
    
    // Test 2: Catches BuggySSNHelperAllowMonth0
    @Test
    public void isValidMonthShouldReturnFalseForMonth00() {
        ISsnHelper helper = getHelper();
        assertFalse(helper.isValidMonth("00"));
    }
    
    // Test 3: Catches BuggySSNHelperAllowDayUpTo30
    @Test
    public void isValidDayShouldReturnTrueForDay31() {
        ISsnHelper helper = getHelper();
        assertTrue(helper.isValidDay("31"));
    }
    
    // Test 4: Catches BuggySSNHelperIncorrectFormat (always true)
    @Test
    public void isCorrectFormatShouldReturnFalseForInvalidFormat() {
        ISsnHelper helper = getHelper();
        assertFalse(helper.isCorrectFormat("90010100017")); // Missing hyphen
    }
    
    // Test 5: Catches BuggySSNHelperIncorrectFormatFalse (always false)
    @Test
    public void isCorrectFormatShouldReturnTrueForValidFormat() {
        ISsnHelper helper = getHelper();
        assertTrue(helper.isCorrectFormat("900101-0017"));
    }
    
    // Test 6: Catches BuggySSNHelperMessyLuhn
    @Test
    public void luhnIsCorrectShouldReturnTrueForValidSSN() {
        ISsnHelper helper = getHelper();
        assertTrue(helper.luhnIsCorrect("900101-0017")); // Valid Luhn
    }
    
    // Coverage test 1: Valid length
    @Test
    public void isCorrectLengthShouldReturnTrueForElevenCharacters() {
        ISsnHelper helper = getHelper();
        assertTrue(helper.isCorrectLength("900101-0017"));
    }
    
    // Coverage test 2: Month boundary - valid month
    @Test
    public void isValidMonthShouldReturnTrueForMonth12() {
        ISsnHelper helper = getHelper();
        assertTrue(helper.isValidMonth("12"));
    }
    
    // Coverage test 3: Month boundary - invalid month
    @Test
    public void isValidMonthShouldReturnFalseForMonth13() {
        ISsnHelper helper = getHelper();
        assertFalse(helper.isValidMonth("13"));
    }
    
    // Coverage test 4: Day boundary - valid day
    @Test
    public void isValidDayShouldReturnTrueForDay01() {
        ISsnHelper helper = getHelper();
        assertTrue(helper.isValidDay("01"));
    }
    
    // Coverage test 5: Day boundary - invalid day
    @Test
    public void isValidDayShouldReturnFalseForDay32() {
        ISsnHelper helper = getHelper();
        assertFalse(helper.isValidDay("32"));
    }
    
    // Coverage test 6: Invalid Luhn
    @Test
    public void luhnIsCorrectShouldReturnFalseForInvalidLuhn() {
        ISsnHelper helper = getHelper();
        assertFalse(helper.luhnIsCorrect("900101-0018")); // Invalid Luhn checksum
    }
}

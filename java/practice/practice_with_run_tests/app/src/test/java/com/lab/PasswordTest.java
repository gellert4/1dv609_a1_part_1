package com.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Password implementations.
 * 
 * To test different buggy versions, simply uncomment the corresponding
 * getPassword() method and comment out the others.
 * 
 * Available implementations:
 * - Password: Correct implementation
 * - BugDoesNotTrim: Does not trim whitespace
 * - BugToShortPassword: Allows passwords shorter than 12 characters (threshold < 11)
 * - BugVeryShort: Allows very short passwords (threshold < 6)
 * - BugWrongExceptionMessage: Wrong exception message for short passwords
 * - BugMissingPasswordLengthCheck: Does not throw exception for short passwords
 * - BugMissingNumberCheck: Does not throw exception if password lacks a number
 * - BugIsPasswordSameAlwaysTrue: isPasswordSame always returns true
 * - BugWrongHashingAlgorithm: Wrong hashing algorithm
 * - BugWrongHashForUppercaseStart: Custom bug - wrong hash for uppercase-starting passwords
 */

public class PasswordTest {
    private IPassword getPassword(String s) throws Exception {
        return (IPassword) new Password(s);
        // return (IPassword) new BugDoesNotTrim(s);
        // return (IPassword) new BugToShortPassword(s);
        // return (IPassword) new BugVeryShort(s);
        // return (IPassword) new BugWrongExceptionMessage(s);
        // return (IPassword) new BugMissingPasswordLengthCheck(s);
        // return (IPassword) new BugMissingNumberCheck(s);
        // return (IPassword) new BugIsPasswordSameAlwaysTrue(s);
        // return (IPassword) new BugWrongHashingAlgorithm(s);
        // return (IPassword) new BugWrongHashForUppercaseStart(s);
    }

    // Helper to compute expected hash using correct algorithm
    private int computeExpectedHash(String input) {
        int hash = 7;
        for (int i = 0; i < input.length(); i++) {
            hash = hash * 31 + input.charAt(i);
        }
        return hash;
    }

    // Test 1: Catches BugDoesNotTrim - verifies whitespace is trimmed
    @Test
    public void constructorShouldTrimWhitespaceForPasswordWithSpaces() throws Exception {
        IPassword pwd1 = getPassword("  validPass123  ");
        IPassword pwd2 = getPassword("validPass123");
        assertTrue(pwd1.isPasswordSame(pwd2));
    }

    // Test 2: Catches BugToShortPassword - exactly 11 chars should fail
    @Test
    public void constructorShouldThrowExceptionForElevenCharPassword() {
        Exception exception = assertThrows(Exception.class, () -> {
            getPassword("password123");
        });
        assertEquals("To short password", exception.getMessage());
    }

    // Test 3: Catches BugVeryShort - 6-10 char password should fail
    @Test
    public void constructorShouldThrowExceptionForSevenCharPassword() {
        assertThrows(Exception.class, () -> {
            getPassword("pass123");
        });
    }

    // Test 4: Catches BugWrongExceptionMessage - verifies correct message
    @Test
    public void constructorShouldThrowCorrectMessageForShortPassword() {
        Exception exception = assertThrows(Exception.class, () -> {
            getPassword("short1");
        });
        assertEquals("To short password", exception.getMessage());
    }

    // Test 5: Catches BugMissingPasswordLengthCheck - short with number should fail
    @Test
    public void constructorShouldThrowExceptionForShortPasswordWithNumber() {
        assertThrows(Exception.class, () -> {
            getPassword("abc1");
        });
    }

    // Test 6: Catches BugMissingNumberCheck - long without number should fail
    @Test
    public void constructorShouldThrowExceptionForPasswordWithoutNumber() {
        Exception exception = assertThrows(Exception.class, () -> {
            getPassword("validpassword");
        });
        assertEquals("Does not contain a number", exception.getMessage());
    }

    // Test 7: Catches BugIsPasswordSameAlwaysTrue - different passwords != same
    @Test
    public void isPasswordSameShouldReturnFalseForDifferentPasswords() throws Exception {
        IPassword pwd1 = getPassword("validPassword123");
        IPassword pwd2 = getPassword("differentPass456");
        assertFalse(pwd1.isPasswordSame(pwd2));
    }

    // Test 8: Catches BugWrongHashingAlgorithm - verifies correct hash algorithm
    @Test
    public void getPasswordHashShouldReturnCorrectHashForKnownPassword() throws Exception {
        String pw = "testPassword1";
        IPassword pwd = getPassword(pw);
        assertEquals(computeExpectedHash(pw), pwd.getPasswordHash());
    }
}

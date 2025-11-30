package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test suite for SwedishSocialSecurityNumber class using Mockito mocks.
 * Tests each validation step by mocking SSNHelper dependency.
 * 
 * To test buggy versions, replace "new SwedishSocialSecurityNumber(ssn, helperMock)" with buggy class.
 * Available buggy SSN classes:
 * - BuggySwedishSocialSecurityNumberNoTrim: doesn't trim input
 * - BuggySwedishSocialSecurityNumberNoLenCheck: skips length check
 * - BuggySwedishSocialSecurityNumberNoLuhn: skips Luhn validation
 * - BuggySwedishSocialSecurityNumberWrongYear: uses substring(1,3) instead of (0,2)
 * - BuggySwedishSocialSecurityNumberNoFormatCheck: skips format validation (custom bug)
 */
public class SwedishSocialSecurityNumberTest {
    
    private SSNHelper helperMock;
    
    @BeforeEach
    public void setup() {
        helperMock = mock(SSNHelper.class);
    }
    
    private SwedishSocialSecurityNumber createSSN(String ssn) throws Exception {
        return new SwedishSocialSecurityNumber(ssn, helperMock);
        // return new BuggySwedishSocialSecurityNumberNoTrim(ssn, helperMock);
        // return new BuggySwedishSocialSecurityNumberNoLenCheck(ssn, helperMock);
        // return new BuggySwedishSocialSecurityNumberNoLuhn(ssn, helperMock);
        // return new BuggySwedishSocialSecurityNumberWrongYear(ssn, helperMock);
        // return new BuggySwedishSocialSecurityNumberNoFormatCheck(ssn, helperMock);
    }
    
    // Test 1: Catches BuggySwedishSocialSecurityNumberNoTrim
    @Test
    public void constructorShouldAcceptSSNWithLeadingWhitespace() throws Exception {
        // Mock all validations to pass
        when(helperMock.isCorrectLength(anyString())).thenReturn(true);
        when(helperMock.isCorrectFormat(anyString())).thenReturn(true);
        when(helperMock.isValidMonth(anyString())).thenReturn(true);
        when(helperMock.isValidDay(anyString())).thenReturn(true);
        when(helperMock.luhnIsCorrect(anyString())).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = createSSN("  900101-0017");
        
        // Verify that trimmed version was passed to helper
        verify(helperMock).isCorrectLength("900101-0017");
    }
    
    // Test 2: Catches BuggySwedishSocialSecurityNumberNoLenCheck
    @Test
    public void constructorShouldThrowExceptionForIncorrectLength() {
        when(helperMock.isCorrectLength("900101-00177")).thenReturn(false);
        
        Exception exception = assertThrows(Exception.class, () -> {
            createSSN("900101-00177");
        });
        
        verify(helperMock).isCorrectLength("900101-00177");
    }
    
    // Test 3: Catches BuggySwedishSocialSecurityNumberNoLuhn
    @Test
    public void constructorShouldThrowExceptionForInvalidLuhnChecksum() {
        when(helperMock.isCorrectLength("900101-0018")).thenReturn(true);
        when(helperMock.isCorrectFormat("900101-0018")).thenReturn(true);
        when(helperMock.isValidMonth("01")).thenReturn(true);
        when(helperMock.isValidDay("01")).thenReturn(true);
        when(helperMock.luhnIsCorrect("900101-0018")).thenReturn(false);
        
        Exception exception = assertThrows(Exception.class, () -> {
            createSSN("900101-0018");
        });
        
        verify(helperMock).luhnIsCorrect("900101-0018");
    }
    
    // Test 4: Catches BuggySwedishSocialSecurityNumberWrongYear
    @Test
    public void getYearShouldReturnCorrectYearForValidSSN() throws Exception {
        when(helperMock.isCorrectLength("900101-0017")).thenReturn(true);
        when(helperMock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(helperMock.isValidMonth("01")).thenReturn(true);
        when(helperMock.isValidDay("01")).thenReturn(true);
        when(helperMock.luhnIsCorrect("900101-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = createSSN("900101-0017");
        
        assertEquals("90", ssn.getYear()); // Should be substring(0,2) not (1,3)
    }
    
    // Coverage test 1: Invalid format
    @Test
    public void constructorShouldThrowExceptionForInvalidFormat() {
        when(helperMock.isCorrectLength("90010100017")).thenReturn(true);
        when(helperMock.isCorrectFormat("90010100017")).thenReturn(false);
        
        Exception exception = assertThrows(Exception.class, () -> {
            createSSN("90010100017");
        });
        
        verify(helperMock).isCorrectFormat("90010100017");
    }
    
    // Coverage test 2: Invalid month
    @Test
    public void constructorShouldThrowExceptionForInvalidMonth() {
        when(helperMock.isCorrectLength("901301-0017")).thenReturn(true);
        when(helperMock.isCorrectFormat("901301-0017")).thenReturn(true);
        when(helperMock.isValidMonth("13")).thenReturn(false);
        
        Exception exception = assertThrows(Exception.class, () -> {
            createSSN("901301-0017");
        });
        
        verify(helperMock).isValidMonth("13");
    }
    
    // Coverage test 3: Invalid day
    @Test
    public void constructorShouldThrowExceptionForInvalidDay() {
        when(helperMock.isCorrectLength("900132-0017")).thenReturn(true);
        when(helperMock.isCorrectFormat("900132-0017")).thenReturn(true);
        when(helperMock.isValidMonth("01")).thenReturn(true);
        when(helperMock.isValidDay("32")).thenReturn(false);
        
        Exception exception = assertThrows(Exception.class, () -> {
            createSSN("900132-0017");
        });
        
        verify(helperMock).isValidDay("32");
    }
    
    // Coverage test 4: Valid SSN, all validations pass
    @Test
    public void constructorShouldAcceptCompletelyValidSSN() throws Exception {
        when(helperMock.isCorrectLength("900101-0017")).thenReturn(true);
        when(helperMock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(helperMock.isValidMonth("01")).thenReturn(true);
        when(helperMock.isValidDay("01")).thenReturn(true);
        when(helperMock.luhnIsCorrect("900101-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = createSSN("900101-0017");
        assertNotNull(ssn);
    }
    
    // Coverage test 5: getMonth method
    @Test
    public void getMonthShouldReturnCorrectMonthForValidSSN() throws Exception {
        when(helperMock.isCorrectLength("900531-0017")).thenReturn(true);
        when(helperMock.isCorrectFormat("900531-0017")).thenReturn(true);
        when(helperMock.isValidMonth("05")).thenReturn(true);
        when(helperMock.isValidDay("31")).thenReturn(true);
        when(helperMock.luhnIsCorrect("900531-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = createSSN("900531-0017");
        assertEquals("05", ssn.getMonth());
    }
    
    // Coverage test 6: getDay method
    @Test
    public void getDayShouldReturnCorrectDayForValidSSN() throws Exception {
        when(helperMock.isCorrectLength("900531-0017")).thenReturn(true);
        when(helperMock.isCorrectFormat("900531-0017")).thenReturn(true);
        when(helperMock.isValidMonth("05")).thenReturn(true);
        when(helperMock.isValidDay("31")).thenReturn(true);
        when(helperMock.luhnIsCorrect("900531-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = createSSN("900531-0017");
        assertEquals("31", ssn.getDay());
    }
    
    // Coverage test 7: getSerialNumber method
    @Test
    public void getSerialNumberShouldReturnCorrectSerialForValidSSN() throws Exception {
        when(helperMock.isCorrectLength("900531-0017")).thenReturn(true);
        when(helperMock.isCorrectFormat("900531-0017")).thenReturn(true);
        when(helperMock.isValidMonth("05")).thenReturn(true);
        when(helperMock.isValidDay("31")).thenReturn(true);
        when(helperMock.luhnIsCorrect("900531-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = createSSN("900531-0017");
        assertEquals("0017", ssn.getSerialNumber());
    }
}

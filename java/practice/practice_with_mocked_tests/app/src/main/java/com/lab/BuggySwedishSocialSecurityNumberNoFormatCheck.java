package com.lab;

/**
 * Custom bug created to demonstrate test value.
 * Bug: Missing format validation - constructor doesn't check if format is correct.
 * This bug shows the value of the test:
 *   constructorShouldThrowExceptionForInvalidFormat()
 * 
 * The test catches when format validation is completely skipped.
 */
public class BuggySwedishSocialSecurityNumberNoFormatCheck extends SwedishSocialSecurityNumber {
    
    public BuggySwedishSocialSecurityNumberNoFormatCheck(String stringInput, SSNHelper helper) throws Exception {
        super("900101-0017", helper); // Call parent with valid SSN to avoid exception
        
        // Now do our buggy validation
        String trimmedSS = stringInput.trim();
        
        if (!helper.isCorrectLength(trimmedSS)) {
            throw new Exception("To short, must be 11 characters");
        }
        
        // BUG: Missing format check!
        // if (helper.isCorrectFormat(trimmedSS) == false) {
        //     throw new Exception("Incorrect format, must be: YYMMDD-XXXX");
        // }
        
        // Extract parts directly without format validation
        if (trimmedSS.length() >= 11) {
            String month = trimmedSS.substring(2, 4);
            String day = trimmedSS.substring(4, 6);
            
            if (helper.isValidMonth(month) == false) {
                throw new Exception("Invalid month in SSN");
            }
            
            if (helper.isValidDay(day) == false) {
                throw new Exception("Invalid day in SSN");
            }
            
            if (helper.luhnIsCorrect(trimmedSS) == false) {
                throw new Exception("Invalid SSN according to Luhn's algorithm");
            }
        }
    }
}

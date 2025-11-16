package com.lab;

public class BuggySwedishSocialSecurityNumberNoLuhn {
    private SSNHelper helper;
    private String ssn;
    
    public BuggySwedishSocialSecurityNumberNoLuhn(String stringInput, SSNHelper helper) throws Exception {
        this.helper = helper;
        
        String trimmedSS = stringInput.trim();
        
        if (!helper.isCorrectLength(trimmedSS)) {
            throw new Exception("To short, must be 11 characters");
        }
        
        if (helper.isCorrectFormat(trimmedSS) == false) {
            throw new Exception("Incorrect format, must be: YYMMDD-XXXX");
        }
        
        this.ssn = trimmedSS;
        
        if (helper.isValidMonth(this.getMonth()) == false) {
            throw new Exception("Invalid month in SSN");
        }
        
        if (helper.isValidDay(this.getDay()) == false) {
            throw new Exception("Invalid day in SSN");
        }
    }
    
    public String getYear() {
        return this.ssn.substring(0, 2);
    }
    
    public String getMonth() {
        return this.ssn.substring(2, 4);
    }
    
    public String getDay() {
        return this.ssn.substring(4, 6);
    }
    
    public String getSerialNumber() {
        return this.ssn.substring(7, 11);
    }
    
    public String getSSN() {
        return this.ssn;
    }
}

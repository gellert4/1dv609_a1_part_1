package com.lab;

public class BuggySwedishSocialSecurityNumberNoTrim {
    private SSNHelper helper;
    private String ssn;
    
    public BuggySwedishSocialSecurityNumberNoTrim(String stringInput, SSNHelper helper) throws Exception {
        this.helper = helper;
        
        if (!helper.isCorrectLength(stringInput)) {
            throw new Exception("To short, must be 11 characters");
        }
        
        if (helper.isCorrectFormat(stringInput) == false) {
            throw new Exception("Incorrect format, must be: YYMMDD-XXXX");
        }
        
        this.ssn = stringInput;
        
        if (helper.isValidMonth(this.getMonth()) == false) {
            throw new Exception("Invalid month in SSN");
        }
        
        if (helper.isValidDay(this.getDay()) == false) {
            throw new Exception("Invalid day in SSN");
        }
        
        if (helper.luhnIsCorrect(this.ssn) == false) {
            throw new Exception("Invalid SSN according to Luhn's algorithm");
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

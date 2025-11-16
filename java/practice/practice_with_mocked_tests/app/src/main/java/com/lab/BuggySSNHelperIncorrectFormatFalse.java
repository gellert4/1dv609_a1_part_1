package com.lab;

public class BuggySSNHelperIncorrectFormatFalse {
    
    public boolean isCorrectLength(String stringInput) {
        return stringInput.length() == 11;
    }
    
    public boolean isCorrectFormat(String stringInput) {
        return false;
    }
    
    public boolean isValidMonth(String monthString) {
        int month = Integer.parseInt(monthString);
        return month >= 1 && month <= 12;
    }
    
    public boolean isValidDay(String dayString) {
        int day = Integer.parseInt(dayString);
        return day >= 1 && day <= 31;
    }
    
    public boolean luhnIsCorrect(String stringInput) {
        String digitsOnly = stringInput.replace("-", "");
        int sum = 0;
        
        for (int i = 0; i < digitsOnly.length(); i++) {
            int digit = Character.getNumericValue(digitsOnly.charAt(i));
            
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        
        return sum % 10 == 0;
    }
}

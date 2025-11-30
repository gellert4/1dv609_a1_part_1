package com.lab;

/**
 * Custom bug created to demonstrate test value.
 * Bug: Allows month 13 (off-by-one error in boundary check).
 * This bug shows the value of the test:
 *   isValidMonthShouldReturnFalseForMonth13()
 * 
 * The test catches this boundary condition bug.
 */
public class BuggySSNHelperAllowMonth13 extends SSNHelper {
    
    @Override
    public boolean isValidMonth(String monthString) {
        int month = Integer.parseInt(monthString);
        return month >= 1 && month <= 13; // BUG: Should be <= 12
    }
}

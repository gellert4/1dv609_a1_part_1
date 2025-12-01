package com.lab;

public class BuggySSNHelperAllowDayUpTo30Adapter implements ISsnHelper {
    private final BuggySSNHelperAllowDayUpTo30 impl = new BuggySSNHelperAllowDayUpTo30();

    @Override
    public boolean isCorrectLength(String stringInput) { return impl.isCorrectLength(stringInput); }
    @Override
    public boolean isCorrectFormat(String stringInput) { return impl.isCorrectFormat(stringInput); }
    @Override
    public boolean isValidMonth(String monthString) { return impl.isValidMonth(monthString); }
    @Override
    public boolean isValidDay(String dayString) { return impl.isValidDay(dayString); }
    @Override
    public boolean luhnIsCorrect(String stringInput) { return impl.luhnIsCorrect(stringInput); }
}

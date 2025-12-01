package com.lab;

public class BuggySSNHelperIncorrectFormatFalseAdapter implements ISsnHelper {
    private final BuggySSNHelperIncorrectFormatFalse impl = new BuggySSNHelperIncorrectFormatFalse();

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

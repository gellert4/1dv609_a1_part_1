package com.lab;

public class SSNHelperAdapter implements ISsnHelper {
    private final SSNHelper impl = new SSNHelper();

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

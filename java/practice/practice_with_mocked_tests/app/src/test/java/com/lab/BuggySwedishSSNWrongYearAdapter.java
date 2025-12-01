package com.lab;

public class BuggySwedishSSNWrongYearAdapter implements ISwedishSSN {
    private final BuggySwedishSocialSecurityNumberWrongYear impl;

    public BuggySwedishSSNWrongYearAdapter(String ssn, SSNHelper helper) throws Exception {
        this.impl = new BuggySwedishSocialSecurityNumberWrongYear(ssn, helper);
    }

    @Override
    public String getYear() { return impl.getYear(); }
    @Override
    public String getMonth() { return impl.getMonth(); }
    @Override
    public String getDay() { return impl.getDay(); }
    @Override
    public String getSerialNumber() { return impl.getSerialNumber(); }
}

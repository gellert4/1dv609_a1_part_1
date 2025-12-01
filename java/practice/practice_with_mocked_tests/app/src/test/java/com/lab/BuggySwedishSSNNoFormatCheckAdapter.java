package com.lab;

public class BuggySwedishSSNNoFormatCheckAdapter implements ISwedishSSN {
    private final BuggySwedishSocialSecurityNumberNoFormatCheck impl;

    public BuggySwedishSSNNoFormatCheckAdapter(String ssn, SSNHelper helper) throws Exception {
        this.impl = new BuggySwedishSocialSecurityNumberNoFormatCheck(ssn, helper);
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

package com.lab;

public class BuggySwedishSSNNoTrimAdapter implements ISwedishSSN {
    private final BuggySwedishSocialSecurityNumberNoTrim impl;

    public BuggySwedishSSNNoTrimAdapter(String ssn, SSNHelper helper) throws Exception {
        this.impl = new BuggySwedishSocialSecurityNumberNoTrim(ssn, helper);
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

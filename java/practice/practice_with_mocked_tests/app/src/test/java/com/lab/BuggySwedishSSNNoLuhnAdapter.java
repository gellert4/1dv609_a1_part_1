package com.lab;

public class BuggySwedishSSNNoLuhnAdapter implements ISwedishSSN {
    private final BuggySwedishSocialSecurityNumberNoLuhn impl;

    public BuggySwedishSSNNoLuhnAdapter(String ssn, SSNHelper helper) throws Exception {
        this.impl = new BuggySwedishSocialSecurityNumberNoLuhn(ssn, helper);
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

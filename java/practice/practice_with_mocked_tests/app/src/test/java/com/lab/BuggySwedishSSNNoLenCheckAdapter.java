package com.lab;

public class BuggySwedishSSNNoLenCheckAdapter implements ISwedishSSN {
    private final BuggySwedishSocialSecurityNumberNoLenCheck impl;

    public BuggySwedishSSNNoLenCheckAdapter(String ssn, SSNHelper helper) throws Exception {
        this.impl = new BuggySwedishSocialSecurityNumberNoLenCheck(ssn, helper);
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

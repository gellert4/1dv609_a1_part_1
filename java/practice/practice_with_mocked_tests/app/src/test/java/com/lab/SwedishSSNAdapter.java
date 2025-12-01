package com.lab;

public class SwedishSSNAdapter implements ISwedishSSN {
    private final SwedishSocialSecurityNumber impl;

    public SwedishSSNAdapter(String ssn, SSNHelper helper) throws Exception {
        this.impl = new SwedishSocialSecurityNumber(ssn, helper);
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

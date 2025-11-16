package com.lab;
/**
 * Example usage of SwedishSocialSecurityNumber and SSNHelper
 */
public class Main {
    
    public static void main(String[] args) {
        SSNHelper helper = new SSNHelper();
        
        try {
            SwedishSocialSecurityNumber ssn = new SwedishSocialSecurityNumber("900101-0017", helper);
            System.out.println("Valid SSN created!");
            System.out.println("Year: " + ssn.getYear());
            System.out.println("Month: " + ssn.getMonth());
            System.out.println("Day: " + ssn.getDay());
            System.out.println("Serial: " + ssn.getSerialNumber());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
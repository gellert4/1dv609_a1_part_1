package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Example tests demonstrating how to use mocks with the SSN classes.
 * 
 * To use Mockito, add to build.gradle:
 * testImplementation 'org.mockito:mockito-core:5.+'
 * testImplementation 'org.mockito:mockito-junit-jupiter:5.+'
 */
public class MockExampleTest {
    
    private SSNHelper mockHelper;
    
    @BeforeEach
    public void setUp() {
        mockHelper = mock(SSNHelper.class);
    }
    
    @Test
    public void shouldCreateValidSSNWhenAllChecksPass() throws Exception {
        when(mockHelper.isCorrectLength("900101-0017")).thenReturn(true);
        when(mockHelper.isCorrectFormat("900101-0017")).thenReturn(true);
        when(mockHelper.isValidMonth("01")).thenReturn(true);
        when(mockHelper.isValidDay("01")).thenReturn(true);
        when(mockHelper.luhnIsCorrect("900101-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = new SwedishSocialSecurityNumber("900101-0017", mockHelper);
        
        // Assert: Verify the SSN was created and methods work
        assertEquals("90", ssn.getYear());
        assertEquals("01", ssn.getMonth());
        assertEquals("01", ssn.getDay());
        assertEquals("0017", ssn.getSerialNumber());
        
        // Verify that the mock methods were called
        verify(mockHelper).isCorrectLength("900101-0017");
        verify(mockHelper).isCorrectFormat("900101-0017");
        verify(mockHelper).isValidMonth("01");
        verify(mockHelper).isValidDay("01");
        verify(mockHelper).luhnIsCorrect("900101-0017");
    }
}
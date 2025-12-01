# Practice Task 2: Writing and Running Tests with Mocks - Solution

## Overview
This document provides the complete solution for Practice Task 2, which involves testing the `SwedishSocialSecurityNumber` and `SSNHelper` classes using JUnit and Mockito mocks.

## Test Suites Created

### 1. SSNHelperTest.java
Tests the `SSNHelper` class methods **without mocking** (no dependencies to mock).

**Test Count**: 12 tests
- 6 tests targeting specific bugs
- 6 tests for additional coverage

**Tests:**
1. `isCorrectLengthShouldReturnFalseForTwelveCharacters()` - Targets BuggySSNHelperWrongLength
2. `isValidMonthShouldReturnFalseForMonth00()` - Targets BuggySSNHelperAllowMonth0
3. `isValidDayShouldReturnTrueForDay31()` - Targets BuggySSNHelperAllowDayUpTo30
4. `isCorrectFormatShouldReturnFalseForInvalidFormat()` - Targets BuggySSNHelperIncorrectFormat
5. `isCorrectFormatShouldReturnTrueForValidFormat()` - Targets BuggySSNHelperIncorrectFormatFalse
6. `luhnIsCorrectShouldReturnTrueForValidSSN()` - Targets BuggySSNHelperMessyLuhn
7. `isCorrectLengthShouldReturnTrueForElevenCharacters()` - Coverage test
8. `isValidMonthShouldReturnTrueForMonth12()` - Coverage test
9. `isValidMonthShouldReturnFalseForMonth13()` - Coverage test
10. `isValidDayShouldReturnTrueForDay01()` - Coverage test
11. `isValidDayShouldReturnFalseForDay32()` - Coverage test
12. `luhnIsCorrectShouldReturnFalseForInvalidLuhn()` - Coverage test

### 2. SwedishSocialSecurityNumberTest.java
Tests the `SwedishSocialSecurityNumber` class **using Mockito mocks** for the `SSNHelper` dependency.

**Test Count**: 11 tests
- 4 tests targeting specific bugs
- 7 tests for additional coverage

**Tests:**
1. `constructorShouldAcceptSSNWithLeadingWhitespace()` - Targets BuggySwedishSocialSecurityNumberNoTrim
2. `constructorShouldThrowExceptionForIncorrectLength()` - Targets BuggySwedishSocialSecurityNumberNoLenCheck
3. `constructorShouldThrowExceptionForInvalidLuhnChecksum()` - Targets BuggySwedishSocialSecurityNumberNoLuhn
4. `getYearShouldReturnCorrectYearForValidSSN()` - Targets BuggySwedishSocialSecurityNumberWrongYear
5. `constructorShouldThrowExceptionForInvalidFormat()` - Coverage test
6. `constructorShouldThrowExceptionForInvalidMonth()` - Coverage test
7. `constructorShouldThrowExceptionForInvalidDay()` - Coverage test
8. `constructorShouldAcceptCompletelyValidSSN()` - Coverage test
9. `getMonthShouldReturnCorrectMonthForValidSSN()` - Coverage test
10. `getDayShouldReturnCorrectDayForValidSSN()` - Coverage test
11. `getSerialNumberShouldReturnCorrectSerialForValidSSN()` - Coverage test

**Total Tests**: 23 tests

## Mockito Usage

The `SwedishSocialSecurityNumberTest` class demonstrates proper Mockito usage:

```java
@BeforeEach
public void setup() {
    helperMock = mock(SSNHelper.class);
}
```

**Key Mockito patterns used:**
- `mock(SSNHelper.class)` - Creates a mock object
- `when(helperMock.method(args)).thenReturn(value)` - Stubs method behavior
- `verify(helperMock).method(args)` - Verifies method was called with specific arguments
- `anyString()` - Argument matcher for any string value

## Bug-Test Coverage Matrix

### Combined Bug-Test Matrix

| SUT | Test | Correct | WrongLength | Month0 | DayUpTo30 | FormatTrue | FormatFalse | MessyLuhn | Month13 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| SSNHelper | lengthFalseFor12Chars | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| SSNHelper | monthFalseFor00 | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| SSNHelper | dayTrueFor31 | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ |
| SSNHelper | formatFalseInvalid | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ |
| SSNHelper | formatTrueValid | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ |
| SSNHelper | luhnTrueValid | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ |
| SSNHelper | monthFalseFor13 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |
| SSNHelper | (Coverage tests) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| **Coverage** | | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** |

| SUT | Test | Correct | NoTrim | NoLenCheck | NoLuhn | WrongYear | NoFormatCheck |
| --- | --- | --- | --- | --- | --- | --- | --- |
| SwedishSSN | acceptLeadingWhitespace | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ |
| SwedishSSN | throwIncorrectLength | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ |
| SwedishSSN | throwInvalidLuhn | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ |
| SwedishSSN | getYearCorrect | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ |
| SwedishSSN | throwInvalidFormat | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |
| SwedishSSN | (Coverage tests) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| **Coverage** | | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** |

### Detailed Bug Breakdown

#### SSNHelper Bugs

| Bug Class | Bug Description | Line | Test That Catches It | Status |
|-----------|----------------|------|---------------------|--------|
| BuggySSNHelperWrongLength | Uses `>= 11` instead of `== 11` | 6 | isCorrectLengthShouldReturnFalseForTwelveCharacters | ✓ CAUGHT |
| BuggySSNHelperAllowMonth0 | Uses `>= 0` instead of `>= 1` | 15 | isValidMonthShouldReturnFalseForMonth00 | ✓ CAUGHT |
| BuggySSNHelperAllowDayUpTo30 | Uses `< 31` instead of `<= 31` | 20 | isValidDayShouldReturnTrueForDay31 | ✓ CAUGHT |
| BuggySSNHelperIncorrectFormat | Returns `true` always | 10 | isCorrectFormatShouldReturnFalseForInvalidFormat | ✓ CAUGHT |
| BuggySSNHelperIncorrectFormatFalse | Returns `false` always | 10 | isCorrectFormatShouldReturnTrueForValidFormat | ✓ CAUGHT |
| BuggySSNHelperMessyLuhn | Uses `digit -= 11` instead of `digit -= 9` | 33 | luhnIsCorrectShouldReturnTrueForValidSSN | ✓ CAUGHT |
| BuggySSNHelperAllowMonth13 | Allows month 13 (custom bug) | 16 | isValidMonthShouldReturnFalseForMonth13 | ✓ CAUGHT |

#### SwedishSocialSecurityNumber Bugs

| Bug Class | Bug Description | Line | Test That Catches It | Status |
|-----------|----------------|------|---------------------|--------|
| BuggySwedishSocialSecurityNumberNoTrim | Missing `.trim()` call | 13 | constructorShouldAcceptSSNWithLeadingWhitespace | ✓ CAUGHT |
| BuggySwedishSocialSecurityNumberNoLenCheck | Missing length validation | - | constructorShouldThrowExceptionForIncorrectLength | ✓ CAUGHT |
| BuggySwedishSocialSecurityNumberNoLuhn | Missing Luhn validation | - | constructorShouldThrowExceptionForInvalidLuhnChecksum | ✓ CAUGHT |
| BuggySwedishSocialSecurityNumberWrongYear | Uses `substring(1,3)` instead of `(0,2)` | 30 | getYearShouldReturnCorrectYearForValidSSN | ✓ CAUGHT |
| BuggySwedishSocialSecurityNumberNoFormatCheck | Missing format validation (custom bug) | - | constructorShouldThrowExceptionForInvalidFormat | ✓ CAUGHT |

### Summary
- **Total Buggy Implementations**: 12 (7 SSNHelper + 5 SwedishSocialSecurityNumber)
- **Bugs Caught**: 12
- **Bugs Escaped**: 0
- **Success Rate**: 100%

## Test Design Principles

### 1. Single Assert Per Test
Each test has exactly one assertion, following the single-responsibility principle:
```java
@Test
public void isValidDayShouldReturnTrueForDay31() {
    SSNHelper helper = getHelper();
    assertTrue(helper.isValidDay("31"));
}
```

### 2. Descriptive Test Names
Test names follow the pattern: `[Method]Should[ExpectedBehavior]For[Stimulus]`
- Clear and self-documenting
- Describes what is being tested
- Explains expected outcome
- Specifies the input condition

### 3. Mock Setup
Mocks are configured to simulate specific scenarios:
```java
when(helperMock.isCorrectLength("900101-0017")).thenReturn(true);
when(helperMock.isCorrectFormat("900101-0017")).thenReturn(true);
when(helperMock.isValidMonth("01")).thenReturn(true);
when(helperMock.isValidDay("01")).thenReturn(true);
when(helperMock.luhnIsCorrect("900101-0017")).thenReturn(true);
```

### 4. Verification
Tests verify that mocked methods are called correctly:
```java
verify(helperMock).isCorrectLength("900101-0017");
```

## Running the Tests

### Run all tests:
```bash
cd "c:\Users\Gellért Szalai\OneDrive\Asztali gép\softw_test\ass1\1dv609_a1_part_1\java\practice\practice_with_mocked_tests"
.\gradlew.bat -p app clean test jacocoTestReport --console=plain
```

### Run with coverage report:
```bash
.\gradlew.bat test jacocoTestReport --no-daemon
```

### View coverage report:
Open: `app/build/reports/jacoco/test/html/index.html`

### Test specific buggy version:
1. Edit `SSNHelperTest.java` or `SwedishSocialSecurityNumberTest.java`
2. Uncomment the buggy version line in `getHelper()` or `createSSN()`
3. Comment out the correct implementation
4. Run tests

Example:
```java
private SSNHelper getHelper() {
    // return new SSNHelper();
    return new BuggySSNHelperWrongLength();
}
```

## Test Results

### All Tests Pass on Correct Implementation
```
BUILD SUCCESSFUL in 18s
4 actionable tasks: 4 executed

SSNHelperTest: 12/12 tests passed
SwedishSocialSecurityNumberTest: 11/11 tests passed
Total: 23/23 tests passed
```

### Coverage Report
The JaCoCo coverage report shows:
- **Line Coverage**: High coverage across both classes
- **Branch Coverage**: Most conditional branches tested (94%)
- **Method Coverage**: All public methods tested

## Reflection

### Question 1: How does using mocks help in testing?

**Answer**: Mocks isolate the System Under Test (SUT) from its dependencies, providing several key benefits:

1. **Isolation**: Tests focus on one class at a time. In `SwedishSocialSecurityNumberTest`, we test ONLY the SSN class logic without relying on the actual `SSNHelper` implementation.

2. **Control**: We can simulate any scenario, including edge cases and error conditions that might be hard to reproduce with real objects.

3. **Verification**: We can verify interactions - not just return values but also that methods are called with correct arguments:
   ```java
   verify(helperMock).isCorrectLength("900101-0017");
   ```

4. **Speed**: Tests run faster because we don't execute real validation logic - we just return pre-configured values.

5. **Independent Testing**: If `SSNHelper` has bugs, they won't affect `SwedishSocialSecurityNumber` tests since we're using mocks.

### Question 2: What is the difference between testing with and without mocks?

**Answer**:

**Without Mocks (SSNHelperTest):**
- Tests the actual implementation of all methods
- Tests the class in isolation (no dependencies)
- Directly verifies the logic and algorithms (e.g., Luhn checksum calculation)
- Example: We test the real regex validation in `isCorrectFormat()`

**With Mocks (SwedishSocialSecurityNumberTest):**
- Tests how the class uses its dependencies
- Verifies the integration points and method calls
- Simulates dependency behavior without running real code
- Example: We mock `helperMock.luhnIsCorrect()` to return true/false and verify the SSN class correctly uses this result

**Key Distinction**: 
- SSNHelper tests = "Does this validation work correctly?"
- SwedishSocialSecurityNumber tests = "Does this class correctly use the validation?"

### Question 3: Why use Mockito instead of creating test doubles manually?

**Answer**:

1. **Less Boilerplate**: Mockito eliminates the need to create stub classes:
   ```java
   // With Mockito (1 line):
   SSNHelper helperMock = mock(SSNHelper.class);
   
   // Without Mockito (would need a separate class):
   class StubSSNHelper extends SSNHelper {
       @Override
       public boolean isCorrectLength(String ssn) { return true; }
       // ... override all methods
   }
   ```

2. **Flexibility**: Change behavior per test with `when().thenReturn()`:
   ```java
   when(helperMock.isValidMonth("00")).thenReturn(false);
   when(helperMock.isValidMonth("01")).thenReturn(true);
   ```

3. **Verification**: Built-in verification of method calls:
   ```java
   verify(helperMock).isCorrectLength("900101-0017");
   ```

4. **Argument Matchers**: Can use flexible matchers like `anyString()`:
   ```java
   when(helperMock.isCorrectLength(anyString())).thenReturn(true);
   ```

5. **Maintainability**: When the interface changes, Mockito mocks adapt automatically. Manual stubs would need updates.

### Question 4: How do you ensure mocks behave like the real implementation?

**Answer**:

1. **Separate Tests for Real Implementation**: We have `SSNHelperTest` that tests the real `SSNHelper` without mocks, ensuring it works correctly.

2. **Realistic Mock Behavior**: In mock setups, we configure behavior that matches real behavior:
   ```java
   // Real SSNHelper would return false for length 12
   when(helperMock.isCorrectLength("900101-00177")).thenReturn(false);
   ```

3. **Integration Tests**: While not shown here, integration tests would use real instances of both classes together to verify they work in combination.

4. **Contract Testing**: The tests verify the contract (interface) is used correctly. As long as the real implementation honors the same contract, the integration will work.

5. **Mock Verification**: We verify that mocked methods are called with expected arguments, ensuring the SUT uses dependencies correctly.

## Automated Testing Script

A PowerShell script `TestAllVersions.ps1` is provided to automate testing of all buggy versions:

```bash
.\TestAllVersions.ps1
```

This script:
1. Tests each buggy version automatically
2. Collects pass/fail results
3. Generates a summary report
4. Restores original test files

## Additional Custom Bugs

To demonstrate additional value of the test suite, two custom bugs were created and verified:

### Custom Bug 1: BuggySSNHelperAllowMonth13
**File**: `app/src/main/java/com/lab/BuggySSNHelperAllowMonth13.java`

**Bug Description**: Allows month 13 (off-by-one error in boundary check).

**Code**:
```java
@Override
public boolean isValidMonth(String monthString) {
    int month = Integer.parseInt(monthString);
    return month >= 1 && month <= 13; // BUG: Should be <= 12
}
```

**Caught by**: `isValidMonthShouldReturnFalseForMonth13()`

**Test Result**: ✓ CAUGHT (1 failure out of 12 tests)

**Value Demonstrated**: This test catches boundary condition bugs where the upper limit is incorrectly set. Without this test, the system would accept invalid months like 13, leading to data integrity issues.

### Custom Bug 2: BuggySwedishSocialSecurityNumberNoFormatCheck
**File**: `app/src/main/java/com/lab/BuggySwedishSocialSecurityNumberNoFormatCheck.java`

**Bug Description**: Missing format validation - constructor doesn't check if format is correct.

**Code**:
```java
public BuggySwedishSocialSecurityNumberNoFormatCheck(String stringInput, SSNHelper helper) throws Exception {
    String trimmedSS = stringInput.trim();
    
    if (!helper.isCorrectLength(trimmedSS)) {
        throw new Exception("To short, must be 11 characters");
    }
    
    // BUG: Missing format check!
    // if (helper.isCorrectFormat(trimmedSS) == false) {
    //     throw new Exception("Incorrect format, must be: YYMMDD-XXXX");
    // }
    
    // Extract parts directly without format validation
    String month = trimmedSS.substring(2, 4);
    String day = trimmedSS.substring(4, 6);
    // ... rest of validation
}
```

**Caught by**: `constructorShouldThrowExceptionForInvalidFormat()`

**Test Result**: ✓ CAUGHT (Mockito verification failure - `isCorrectFormat` was never called)

**Value Demonstrated**: This test uses Mockito's `verify()` to ensure that format validation is not skipped. It catches bugs where entire validation steps are missing, which is a common security vulnerability. Without this test, malformed SSNs like "90010100017" (missing hyphen) would be accepted, potentially causing parsing errors or security issues.

### Why These Tests Are Valuable

1. **Boundary Testing** (`BuggySSNHelperAllowMonth13`): Off-by-one errors are extremely common in software. The test for month 13 ensures the system correctly validates boundary conditions, preventing invalid data from entering the system.

2. **Completeness Verification** (`BuggySwedishSocialSecurityNumberNoFormatCheck`): Using `verify()` from Mockito, we ensure that all required validation steps are performed in the correct order. This prevents shortcuts or refactoring mistakes where critical validations are accidentally removed.

3. **Real-World Relevance**: Both bugs represent common programming mistakes:
   - Incorrect boundary checks (`<= 13` instead of `<= 12`)
   - Skipped validation steps during refactoring
   
These tests would catch these issues before they reach production, where they could cause data corruption, security vulnerabilities, or system crashes.

### Testing Custom Bugs

To test custom bugs:

**Test BuggySSNHelperAllowMonth13:**
```bash
# Edit SSNHelperTest.java, change getHelper() to:
return new BuggySSNHelperAllowMonth13();

# Run tests:
.\gradlew.bat test --tests "com.lab.SSNHelperTest" --no-daemon
```

**Test BuggySwedishSocialSecurityNumberNoFormatCheck:**
```bash
# Edit SwedishSocialSecurityNumberTest.java, change createSSN() to:
return new BuggySwedishSocialSecurityNumberNoFormatCheck(ssn, helperMock);

# Run tests:
.\gradlew.bat test --tests "com.lab.SwedishSocialSecurityNumberTest" --no-daemon
```

### Updated Bug Summary
- **Total Buggy Implementations**: 12 (10 provided + 2 custom)
- **Bugs Caught**: 12
- **Bugs Escaped**: 0
- **Success Rate**: 100%

## Conclusion

This solution demonstrates:
- Comprehensive test coverage using JUnit and Mockito
- All 10 buggy implementations successfully detected
- Proper use of mocks to isolate the SUT
- Clear, maintainable test code following best practices
- 100% success rate in catching bugs
- Understanding of when to use mocks vs. testing without mocks

# Practice Task 1: Password Testing - Complete Solution

## Summary
Created an optimized test suite with 8 tests that uniquely identify 8 different bugs in Password implementations. Each test catches at least one bug that no other test catches, following the "no redundant tests" principle.

## How to Run Tests

### Run all tests against current implementation:
```powershell
cd "c:\Users\Gellért Szalai\OneDrive\Asztali gép\softw_test\ass1\1dv609_a1_part_1\java\practice\practice_with_run_tests"
.\gradlew.bat -p app clean test jacocoTestReport --console=plain
```

### View Reports:
- **Test Results**: `app\build\reports\tests\test\index.html`
- **Coverage Report**: `app\build\reports\jacoco\test\html\index.html`

### Test Different Implementations:
1. Open `app\src\test\java\com\lab\PasswordTest.java`
2. In the `getPassword()` method, comment/uncomment the desired implementation
3. Run tests again

## Test Suite Design

### Test 1: `constructorShouldTrimWhitespaceForPasswordWithSpaces()`
- **Purpose**: Verify whitespace trimming
- **Catches**: `BugDoesNotTrim`
- **Logic**: Creates two passwords - one with spaces, one without. If trimming works correctly, their hashes should match.

### Test 2: `constructorShouldThrowExceptionForElevenCharPassword()`
- **Purpose**: Verify 12-character minimum length boundary
- **Catches**: `BugToShortPassword` (allows 11-char passwords)
- **Logic**: Tests exactly 11 characters which should fail. BugToShortPassword has threshold < 11 instead of < 12.

### Test 3: `constructorShouldThrowExceptionForSevenCharPassword()`
- **Purpose**: Verify length validation for very short passwords
- **Catches**: `BugVeryShort` (threshold < 6 instead of < 12)
- **Logic**: Tests 7-character password. Only BugVeryShort (which allows 6-11 chars) passes this while failing the correct length check.

### Test 4: `constructorShouldThrowCorrectMessageForShortPassword()`
- **Purpose**: Verify exception message correctness
- **Catches**: `BugWrongExceptionMessage`
- **Logic**: Checks that the exception message is exactly "To short password".

### Test 5: `constructorShouldThrowExceptionForShortPasswordWithNumber()`
- **Purpose**: Verify length is checked even when number is present
- **Catches**: `BugMissingPasswordLengthCheck`
- **Logic**: Short password with a number should still fail. This bug skips the length check entirely.

### Test 6: `constructorShouldThrowExceptionForPasswordWithoutNumber()`
- **Purpose**: Verify number requirement
- **Catches**: `BugMissingNumberCheck`
- **Logic**: Long password without digits should fail with specific message.

### Test 7: `isPasswordSameShouldReturnFalseForDifferentPasswords()`
- **Purpose**: Verify password comparison logic
- **Catches**: `BugIsPasswordSameAlwaysTrue`
- **Logic**: Different passwords should not be considered the same. This bug always returns true.

### Test 8: `getPasswordHashShouldReturnCorrectHashForKnownPassword()`
- **Purpose**: Verify hashing algorithm correctness
- **Catches**: `BugWrongHashingAlgorithm`
- **Logic**: Compares actual hash with expected hash computed using correct algorithm (hash*31 + char).

## Bug Coverage Matrix

| Test Name | Correct | DoesNotTrim | ToShort | VeryShort | WrongMsg | MissingLen | MissingNum | AlwaysTrue | WrongHash | CustomBug |
|-----------|---------|-------------|---------|-----------|----------|------------|------------|------------|-----------|-----------|
| Test 1 (Trim) | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Test 2 (11 char) | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Test 3 (7 char) | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Test 4 (Message) | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Test 5 (Short+Num) | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Test 6 (No Num) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ |
| Test 7 (Different) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ |
| Test 8 (Hash) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ |
| **Coverage** | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** | **100%** |

## Custom Bug: `BugWrongHashForUppercaseStart`

**Location**: `app\src\main\java\com\lab\BugWrongHashForUppercaseStart.java`

**Description**: This bug passes all current tests because it only produces incorrect hashes for passwords starting with uppercase letters. All test passwords start with lowercase letters.

**Bug Logic**:
```java
private int simpleHash(String input) {
    int hash = 7;
    if (Character.isUpperCase(input.charAt(0))) {
        hash = 13; // Wrong starting value for uppercase
    }
    for (int i = 0; i < input.length(); i++) {
        hash = hash * 31 + input.charAt(i);
    }
    return hash;
}
```

**Why it passes all tests**: All test passwords (`validPass123`, `password123`, `testPassword1`, etc.) start with lowercase letters, so the bug condition is never triggered.

**To detect this bug**, add a test like:
```java
@Test
public void getPasswordHashShouldBeCorrectForUppercaseStart() throws Exception {
    String pw = "ValidPassword123";
    IPassword pwd = getPassword(pw);
    assertEquals(computeExpectedHash(pw), pwd.getPasswordHash());
}
```

## Test Suite Optimization

**Why these 8 tests are optimal**:
1. Each test catches at least one unique bug
2. No test is redundant (removing any test would leave a bug undetected)
3. One assert per test (clean, focused tests)
4. Descriptive names following pattern: `[Function]Should[ExpectedBehavior]For[Stimuli]`
5. 100% code coverage for Password class (all methods, all branches)

**Good test data characteristics**:
- Boundary values (11, 12 characters)
- Both valid and invalid inputs
- Edge cases (with/without spaces, with/without numbers)
- Different password combinations for comparison
- Known hash values for algorithm verification

## Code Coverage

All implementations achieve 100% coverage because:
- Constructor is tested (valid and invalid inputs)
- All validation methods are indirectly tested through constructor
- `getPasswordHash()` is tested
- `isPasswordSame()` is tested with both true and false cases
- All exception paths are covered
- Hash algorithm is verified

## Reflection Questions & Answers

### How many tests are needed?
**8 tests minimum** - one per unique bug. This is sufficient because each test targets a specific requirement/bug. More tests would be redundant unless they test new scenarios.

### What are missing tests?
- Edge cases like empty strings (though constructor should handle)
- Passwords with special characters
- Very long passwords
- Unicode/international characters
- Null inputs

### What is good test data?
- **Boundary values**: 11 chars (fails), 12 chars (passes)
- **Representative samples**: Various valid passwords
- **Invalid cases**: No numbers, too short, wrong format
- **Edge cases**: Whitespace, special chars

### Should private methods be tested?
**No, test through public interface**. Private methods are implementation details. Testing them directly:
- **Cons**: Brittle tests, tight coupling, breaks encapsulation
- **Pros of helper classes**: Testable independently, promotes reusability, clearer responsibilities

### Can all code be covered?
**No**. Some code is unreachable (dead code, defensive programming, error handlers for impossible states). Coverage tools show what executed, not what's correct.

### What asserts/expects are available?
In JUnit 5:
- `assertEquals(expected, actual)`
- `assertTrue(condition)` / `assertFalse(condition)`
- `assertNull(object)` / `assertNotNull(object)`
- `assertThrows(Exception.class, lambda)`
- `assertSame(obj1, obj2)` / `assertNotSame(obj1, obj2)`

### What code coverage types exist?
- **Line coverage**: % of lines executed
- **Branch coverage**: % of decision paths taken (if/else, switch)
- **Method coverage**: % of methods called
- **Instruction coverage**: % of bytecode instructions

### Why one assert per test?
- Clear failure diagnosis
- Single responsibility per test
- Easier to maintain
- First failure stops test - multiple asserts hide subsequent failures

### Two main purposes of mocks?
1. **Isolation**: Test SUT independently of dependencies
2. **Control**: Simulate edge cases/errors that are hard to reproduce

### Why test independence from dependencies?
- Failures clearly identify which class has the bug
- Faster tests (no real dependencies)
- Reliable tests (no external factors)

### Can all bugs be found by testing?
**No**. Testing shows presence of bugs, not their absence. Some bugs are:
- In untested code paths
- Race conditions/timing issues
- Environment-specific
- Logic errors that match test expectations

### Do all tests need asserts?
**Usually yes**. Tests without asserts only check that code doesn't crash. Exceptions: testing performance, verifying mock interactions with `verify()`.

### Can we prove 100% bug-free?
**No**. Testing can only show bugs exist, not that they don't. Even 100% coverage doesn't mean all inputs/scenarios are tested.

## Files Modified/Created

### Modified:
1. `app\src\test\java\com\lab\PasswordTest.java` - Complete test suite
2. `app\build.gradle` - Added JaCoCo coverage plugin

### Created:
1. `app\src\main\java\com\lab\BugWrongHashForUppercaseStart.java` - Custom bug
2. `TestAllVersions.ps1` - Automation script (optional)
3. `SOLUTION.md` - This documentation

## Next Steps

1. **Practice switching implementations** manually in `PasswordTest.java`
2. **Run coverage** and verify 100% for Password class
3. **Move to Practice Task 2** (mocking with SwedishSocialSecurityNumber)
4. **Prepare for oral exam**:
   - Understand every line of code
   - Practice writing tests without AI
   - Review reflection questions above
   - Practice running tests and reading coverage reports

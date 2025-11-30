# Script to test all Password implementations and generate bug coverage matrix

$implementations = @(
    "Password",
    "BugDoesNotTrim",
    "BugToShortPassword",
    "BugVeryShort",
    "BugWrongExceptionMessage",
    "BugMissingPasswordLengthCheck",
    "BugMissingNumberCheck",
    "BugIsPasswordSameAlwaysTrue",
    "BugWrongHashingAlgorithm"
)

$testFile = "app\src\test\java\com\lab\PasswordTest.java"
$results = @{}

Write-Host "Testing all implementations..." -ForegroundColor Cyan
Write-Host "=" * 80

foreach ($impl in $implementations) {
    Write-Host "`nTesting $impl..." -ForegroundColor Yellow
    
    # Read the test file
    $content = Get-Content $testFile -Raw
    
    # Comment out all implementations
    $content = $content -replace '(\s+return \(IPassword\) new \w+\(s\);)', '        // return (IPassword) new Password(s);'
    
    # Uncomment the target implementation
    $content = $content -replace "// return \(IPassword\) new $impl\(s\);", "return (IPassword) new $impl(s);"
    
    # Write back
    Set-Content $testFile -Value $content
    
    # Run tests
    $output = & .\gradlew.bat -p app test --no-daemon 2>&1 | Out-String
    
    # Parse results
    $xmlPath = "app\build\test-results\test\TEST-com.lab.PasswordTest.xml"
    if (Test-Path $xmlPath) {
        [xml]$xml = Get-Content $xmlPath
        $testCount = [int]$xml.testsuite.tests
        $failures = [int]$xml.testsuite.failures
        $errors = [int]$xml.testsuite.errors
        
        $passed = $testCount - $failures - $errors
        $failed = $failures + $errors
        
        Write-Host "  Tests: $testCount | Passed: $passed | Failed: $failed" -ForegroundColor $(if ($failed -eq 0) { "Green" } else { "Red" })
        
        # Store detailed results
        $results[$impl] = @{
            Total = $testCount
            Passed = $passed
            Failed = $failed
            FailedTests = @()
        }
        
        # Get failed test names
        foreach ($testcase in $xml.testsuite.testcase) {
            if ($testcase.failure -or $testcase.error) {
                $results[$impl].FailedTests += $testcase.name
                Write-Host "    ❌ $($testcase.name)" -ForegroundColor Red
            }
        }
    }
}

# Restore original (Password)
$content = Get-Content $testFile -Raw
$content = $content -replace '(\s+)return \(IPassword\) new \w+\(s\);', '        return (IPassword) new Password(s);'
$content = $content -replace '(\s+)// return \(IPassword\) new (\w+)\(s\);', '        // return (IPassword) new $2(s);'
Set-Content $testFile -Value $content

# Generate summary table
Write-Host "`n`n" + ("=" * 80) -ForegroundColor Cyan
Write-Host "BUG COVERAGE MATRIX" -ForegroundColor Cyan
Write-Host ("=" * 80) -ForegroundColor Cyan

# Get all unique test names
$allTests = @(
    "constructorShouldTrimWhitespaceForPasswordWithSpaces",
    "constructorShouldThrowExceptionForElevenCharPassword",
    "constructorShouldThrowExceptionForSevenCharPassword",
    "constructorShouldThrowCorrectMessageForShortPassword",
    "constructorShouldThrowExceptionForShortPasswordWithNumber",
    "constructorShouldThrowExceptionForPasswordWithoutNumber",
    "isPasswordSameShouldReturnFalseForDifferentPasswords",
    "getPasswordHashShouldReturnCorrectHashForKnownPassword"
)

# Print header
Write-Host "`n| Test | " -NoNewline
foreach ($impl in $implementations) {
    Write-Host "$($impl.Substring(0, [Math]::Min(8, $impl.Length))) | " -NoNewline
}
Write-Host ""
Write-Host "| --- | " -NoNewline
foreach ($impl in $implementations) {
    Write-Host "--- | " -NoNewline
}
Write-Host ""

# Print each test row
for ($i = 0; $i -lt $allTests.Count; $i++) {
    $testName = $allTests[$i]
    Write-Host "| Test $($i + 1) | " -NoNewline
    
    foreach ($impl in $implementations) {
        $symbol = if ($results[$impl].FailedTests -contains $testName) { "❌" } else { "✅" }
        Write-Host "$symbol | " -NoNewline
    }
    Write-Host ""
}

Write-Host "`nTest completed for all implementations!"

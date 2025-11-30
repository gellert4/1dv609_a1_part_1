# Script to test all buggy versions and generate coverage matrix
# Run with: .\TestAllVersions.ps1

$ErrorActionPreference = "Stop"
$projectRoot = $PSScriptRoot

Write-Host "=== Testing All SSN Buggy Versions ===" -ForegroundColor Cyan
Write-Host ""

# Define all buggy versions
$buggyHelpers = @(
    "BuggySSNHelperWrongLength",
    "BuggySSNHelperAllowMonth0",
    "BuggySSNHelperAllowDayUpTo30",
    "BuggySSNHelperIncorrectFormat",
    "BuggySSNHelperIncorrectFormatFalse",
    "BuggySSNHelperMessyLuhn"
)

$buggySSNs = @(
    "BuggySwedishSocialSecurityNumberNoTrim",
    "BuggySwedishSocialSecurityNumberNoLenCheck",
    "BuggySwedishSocialSecurityNumberNoLuhn",
    "BuggySwedishSocialSecurityNumberWrongYear"
)

# Test result storage
$results = @{}

# Helper function to run tests
function Test-Implementation {
    param (
        [string]$ImplementationName,
        [string]$TestFile
    )
    
    Write-Host "Testing: $ImplementationName" -ForegroundColor Yellow
    
    # Run tests
    $output = & "$projectRoot\gradlew.bat" test --no-daemon 2>&1 | Out-String
    
    # Parse results from XML
    $xmlPath = "$projectRoot\app\build\test-results\test\TEST-$TestFile.xml"
    
    if (Test-Path $xmlPath) {
        [xml]$testResults = Get-Content $xmlPath
        $failures = [int]$testResults.testsuite.failures
        $errors = [int]$testResults.testsuite.errors
        $total = [int]$testResults.testsuite.tests
        
        if ($failures -gt 0 -or $errors -gt 0) {
            Write-Host "  ✗ CAUGHT ($failures failures, $errors errors out of $total tests)" -ForegroundColor Red
            return @{
                Status = "CAUGHT"
                Failures = $failures
                Errors = $errors
                Total = $total
            }
        } else {
            Write-Host "  ✓ ESCAPED (all $total tests passed)" -ForegroundColor Green
            return @{
                Status = "ESCAPED"
                Failures = 0
                Errors = 0
                Total = $total
            }
        }
    } else {
        Write-Host "  ? BUILD FAILED" -ForegroundColor Magenta
        return @{
            Status = "BUILD_FAILED"
            Failures = 0
            Errors = 0
            Total = 0
        }
    }
}

# Backup correct files
$helperPath = "$projectRoot\app\src\test\java\com\lab\SSNHelperTest.java"
$ssnPath = "$projectRoot\app\src\test\java\com\lab\SwedishSocialSecurityNumberTest.java"

$helperBackup = Get-Content $helperPath -Raw
$ssnBackup = Get-Content $ssnPath -Raw

Write-Host "=== Testing SSNHelper Bugs ===" -ForegroundColor Cyan
Write-Host ""

foreach ($buggy in $buggyHelpers) {
    # Modify test file to use buggy version
    $modified = $helperBackup -replace 'return new SSNHelper\(\);', "return new $buggy();"
    Set-Content -Path $helperPath -Value $modified -NoNewline
    
    $result = Test-Implementation -ImplementationName $buggy -TestFile "com.lab.SSNHelperTest"
    $results[$buggy] = $result
    
    Write-Host ""
}

# Restore original
Set-Content -Path $helperPath -Value $helperBackup -NoNewline

Write-Host "=== Testing SwedishSocialSecurityNumber Bugs ===" -ForegroundColor Cyan
Write-Host ""

foreach ($buggy in $buggySSNs) {
    # Modify test file to use buggy version
    $modified = $ssnBackup -replace 'return new SwedishSocialSecurityNumber\(ssn, helperMock\);', "return new $buggy(ssn, helperMock);"
    Set-Content -Path $ssnPath -Value $modified -NoNewline
    
    $result = Test-Implementation -ImplementationName $buggy -TestFile "com.lab.SwedishSocialSecurityNumberTest"
    $results[$buggy] = $result
    
    Write-Host ""
}

# Restore original
Set-Content -Path $ssnPath -Value $ssnBackup -NoNewline

# Generate summary report
Write-Host "=== SUMMARY ===" -ForegroundColor Cyan
Write-Host ""

$caught = ($results.Values | Where-Object { $_.Status -eq "CAUGHT" }).Count
$escaped = ($results.Values | Where-Object { $_.Status -eq "ESCAPED" }).Count
$buildFailed = ($results.Values | Where-Object { $_.Status -eq "BUILD_FAILED" }).Count

Write-Host "Total buggy versions tested: $($buggyHelpers.Count + $buggySSNs.Count)"
Write-Host "Bugs caught: $caught" -ForegroundColor Red
Write-Host "Bugs escaped: $escaped" -ForegroundColor Green
Write-Host "Build failures: $buildFailed" -ForegroundColor Magenta
Write-Host ""

# Detailed results
Write-Host "Detailed Results:" -ForegroundColor Cyan
foreach ($buggy in ($buggyHelpers + $buggySSNs)) {
    $result = $results[$buggy]
    $statusColor = switch ($result.Status) {
        "CAUGHT" { "Red" }
        "ESCAPED" { "Green" }
        "BUILD_FAILED" { "Magenta" }
    }
    Write-Host "  $buggy : $($result.Status)" -ForegroundColor $statusColor
}

Write-Host ""
Write-Host "=== Testing Complete ===" -ForegroundColor Cyan

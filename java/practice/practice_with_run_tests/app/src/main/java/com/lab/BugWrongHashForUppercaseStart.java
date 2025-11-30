package com.lab;

// Custom bug: Returns wrong hash for passwords starting with uppercase letters
// All current test passwords start with lowercase, so this bug is not detected
public class BugWrongHashForUppercaseStart implements IPassword {
    protected int passwordHash;

    public BugWrongHashForUppercaseStart(String pw) throws Exception {
        String trimmedPW = pw.trim();
        if (isToShort(trimmedPW)) {
            throw new Exception("To short password");
        }
        if (containsNumber(trimmedPW) == false) {
            throw new Exception("Does not contain a number");
        }
        this.passwordHash = simpleHash(trimmedPW);
    }

    private int simpleHash(String input) {
        int hash = 7;
        // Bug: Use wrong starting value if first char is uppercase
        if (Character.isUpperCase(input.charAt(0))) {
            hash = 13; // Wrong starting value
        }
        for (int i = 0; i < input.length(); i++) {
            hash = hash * 31 + input.charAt(i);
        }
        return hash;
    }

    private boolean isToShort(String pw) {
        return pw.length() < 12;
    }

    private boolean containsNumber(String text) {
        return text.matches(".*\\d.*");
    }

    @Override
    public int getPasswordHash() {
        return this.passwordHash;
    }

    @Override
    public boolean isPasswordSame(IPassword other) {
        return this.passwordHash == other.getPasswordHash();
    }
}

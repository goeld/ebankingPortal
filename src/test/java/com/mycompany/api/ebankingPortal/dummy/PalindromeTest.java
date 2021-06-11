package com.mycompany.api.ebankingPortal.dummy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class PalindromeTest {

    @Test
    public void whenEmptyString_thenAccept() {
        Palindrome palindromeTester = new Palindrome();
        assertEquals(palindromeTester.isPalindrome("abba"), true);
    }

    @Test
    public void failingTestCase(){
        assertFalse(true);
    }
}

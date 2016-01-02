package com.athena.common.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
 * This Java source file was auto generated by running 'gradle init --type java-library'
 * by 'brook.xi' at '11/16/15 8:37 PM' with Gradle 2.7
 *
 * @author brook.xi, @date 11/16/15 8:37 PM
 */
public class AthenaUserDetailsTest {
    @Test public void testAthenaUserDetails() {
        AthenaUserDetails userDetails = new AthenaUserDetails(1L, 123L, "athena", null);
        assertEquals("AthenaUserDetails.getUsername() should return its id as username", "1", userDetails.getUsername());
    }
}

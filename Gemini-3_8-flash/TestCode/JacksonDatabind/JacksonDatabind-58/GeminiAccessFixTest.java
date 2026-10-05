package com.fasterxml.jackson.databind.misc;

import java.security.Permission;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiAccessFixTest extends BaseMapTest
{
    static class SecurityManagerImpl extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            String name = perm.getName();
            if (name != null && name.contains("suppressAccessChecks")) {
                throw new SecurityException("Can not force permission: " + perm);
            }
        }
    }

    public void testCauseOfThrowableIgnoral() throws Exception
    {
        final SecurityManager sm = System.getSecurityManager();
        try {
            System.setSecurityManager(new SecurityManagerImpl());
            ObjectMapper mapper = new ObjectMapper();
            Exception e = mapper.readValue("{\"message\":\"test\"}", Exception.class);
            assertNotNull(e);
            assertEquals("test", e.getMessage());
        } finally {
            System.setSecurityManager(sm);
        }
    }
}
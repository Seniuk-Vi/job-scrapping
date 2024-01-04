package org.brain.jobscrapping.utils;

import org.springframework.stereotype.Component;

@Component
public class Base64Helper {
    public String encode64(String text) {
        return java.util.Base64.getEncoder().encodeToString(text.getBytes());
    }

    public String decode64(String text) {
        return new String(java.util.Base64.getDecoder().decode(text));
    }
}

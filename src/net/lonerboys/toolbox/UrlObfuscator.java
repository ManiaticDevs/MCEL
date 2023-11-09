package net.lonerboys.toolbox;

import java.util.Base64;

public class UrlObfuscator {
    public static String obfuscateUrl(String originalUrl) {
        // Base64 encode the URL
        return Base64.getEncoder().encodeToString(originalUrl.getBytes());
    }

    public static String deobfuscateUrl(String obfuscatedUrl) {
        // Base64 decode the URL
        byte[] decodedBytes = Base64.getDecoder().decode(obfuscatedUrl);
        return new String(decodedBytes);
    }
}

package me.mourjo.y22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Random;

/*
https://leetcode.com/problems/encode-and-decode-tinyurl/

TinyURL is a URL shortening service where you enter a URL such as
https://leetcode.com/problems/design-tinyurl and it returns a short URL such as
http://tinyurl.com/4e9iAk.

Design the encode and decode methods for the TinyURL service. There is no restriction
on how your encode/decode algorithm should work. You just need to ensure that a URL can
be encoded to a tiny URL and the tiny URL can be decoded to the original URL.
 */
public class EncodeDecodeTinyURL {

  public static void main(String[] args) {
    Codec codec = new Codec();
    String x = codec.encode("https://mourjo.me");
    codec.encode("abd");
    assertEquals("https://mourjo.me", codec.decode(codec.encode("https://mourjo.me")));
    assertEquals("https://mourjo.me", codec.decode(x));
  }

  static class Codec {

    static String charset = "abcdefghijklmnopqrstuvwxyz0987654321ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static HashMap<String, String> decodeMap = new HashMap<>();
    static HashMap<String, String> encodeMap = new HashMap<>();
    static Random r = new Random();

    String rand(int n) {
      StringBuilder sb = new StringBuilder();
      while (n-- > 0) {
        sb.append(charset.charAt(r.nextInt(charset.length())));
      }
      return sb.toString();
    }

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
      if (encodeMap.containsKey(longUrl)) {
        return "http://tinyurl.com/" + encodeMap.get(longUrl);
      }

      String key = null;
      while (key == null || decodeMap.containsKey(key)) {
        key = rand(6);
      }
      encodeMap.put(longUrl, key);
      decodeMap.put(key, longUrl);
      return "http://tinyurl.com/" + key;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
      String suffix = shortUrl.replace("http://tinyurl.com/", "");
      return decodeMap.get(suffix);
    }
  }
}

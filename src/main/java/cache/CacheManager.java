package cache;

public interface CacheManager {
    String get(String key);
    void put(String key, String value);
    boolean contains(String key);
}

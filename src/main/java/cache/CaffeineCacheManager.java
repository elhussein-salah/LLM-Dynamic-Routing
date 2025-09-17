package cache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineCacheManager implements CacheManager {

    private static volatile CaffeineCacheManager instance;
    private final Cache<String, String> cache;

    private CaffeineCacheManager(int maxSize, int expireAfterSeconds) {
        cache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireAfterSeconds, TimeUnit.SECONDS)
                .build();
    }

    public static CaffeineCacheManager getInstance(int maxSize, int expireAfterSeconds) {
        if (instance == null) {
            synchronized (CaffeineCacheManager.class) {
                if (instance == null) {
                    instance = new CaffeineCacheManager(maxSize, expireAfterSeconds);
                }
            }
        }
        return instance;
    }

    @Override
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void put(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public boolean contains(String key) {
        return cache.getIfPresent(key) != null;
    }
}

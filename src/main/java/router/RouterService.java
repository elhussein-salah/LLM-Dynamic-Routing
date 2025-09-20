package router;

import cache.CacheManager;
import domain.ResultDTO;
import domain.UserQuery;

public class RouterService {

    private final ModelRouter router;
    private final CacheManager cache;

    public RouterService(ModelRouter router, CacheManager cache) {
        this.router = router;
        this.cache = cache;
    }

    public ResultDTO handle(UserQuery query) {
        long start = System.currentTimeMillis();
        String key = query.getText().trim().toLowerCase();

        if (cache.contains(key)) {
            long elapsed = System.currentTimeMillis() - start;
            String cachedResponse = cache.get(key);
            return new ResultDTO(cachedResponse, "CACHE", elapsed, true);
        }

        RouteResult result;
        try {
            result = router.route(query);
        } catch (Exception e) {
            result = new RouteResult("Error: " + e.getMessage(), "ERROR");
        }

        cache.put(key, result.getResponse());
        long elapsed = System.currentTimeMillis() - start;
        return new ResultDTO(result.getResponse(), result.getUsedModel(), elapsed, false);
    }
}
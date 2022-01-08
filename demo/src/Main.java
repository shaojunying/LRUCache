import com.shao.Cache;
import com.shao.impl.LRUCache;

public class Main {
    public static void main(String[] args) {
        Cache<String> cache = new LRUCache<>();
        cache.put("name", "admin");
        cache.get("name");
        cache.remove("name");
    }
}

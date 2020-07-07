package others.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author liyang
 * @Date 2020/7/7 4:55 下午
 * @Description 实现一个符合LRU策略的队列
 **/
public class MyLRUWithLinkedHashMap extends LinkedHashMap<String, String>{
    private int maxSize;

    public MyLRUWithLinkedHashMap(int size){
        super(size, 0.75f, true);
        this.maxSize = size;
    }

    public void add(String key, String value){
        put(key,value);
    }

    public String getVal(String key){
        return get(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> entry){
        return size() > maxSize;
    }
}

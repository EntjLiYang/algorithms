package others.lru;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Author liyang
 * @Date 2020/7/7 5:35 下午
 * @Description 实现LinkedHashMap，按照插入顺序维护的Map
 **/
public class MyLinkedHashMap {
    private HashMap<String, String> map;
    private LinkedList<String> list;

    public MyLinkedHashMap(){
        this.map = new HashMap<>();
        this.list = new LinkedList<>();
    }

    // 插入队尾
    public void put(String key, String value){
        list.addLast(key);
        map.put(key,value);
    }

    // 从队头拿
    public String get(){
        return map.get(list.pollFirst());
    }
}

package others.lru;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Author liyang
 * @Date 2020/7/7 5:44 下午
 * @Description 不用LinkedHashMap来实现LRU
 **/
public class MyLRUWithoutLinkedHashMap {
    private HashMap<String, String> map;
    private LinkedList<String> list;
    private int maxSize;

    public MyLRUWithoutLinkedHashMap(int size){
        this.maxSize = size;
        this.map = new HashMap<>();
        this.list = new LinkedList<>();
    }

    public String get(String key){
        String value = map.get(key);
        if (value != null){
            adjustListOrder(key);
        }
        return value;
    }

    public void put(String key, String value){
        if (list.size() + 1 > maxSize){
            map.remove(list.pollLast());
        }
        list.addFirst(key);
        map.put(key,value);
    }

    private void adjustListOrder(String key){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(key)){
                list.remove(i);
                list.addFirst(key);

                break;
            }
        }
    }
}

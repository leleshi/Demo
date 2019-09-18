package lele.shi.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author lele.shi
 * @Date 2019-09-18 17:51
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ListToMapTest {

    private List<Map<String, String>> getList(){
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("grade", "1");
        map1.put("name", "大大");
        map1.put("age", "12");
        Map<String, String> map2 = new HashMap<>();
        map2.put("grade", "1");
        map2.put("name", "花花");
        map2.put("age", "13");
        Map<String, String> map3 = new HashMap<>();
        map3.put("grade", "1");
        map3.put("name", "菲菲");
        map3.put("age", "11");
        Map<String, String> map4 = new HashMap<>();
        map4.put("grade", "3");
        map4.put("name", "小美");
        map4.put("age", "11");

        list.add(map1);
        list.add(map2);
        list.add(map4);
        list.add(map3);
        return list;
    }


    /**
     * List里面的对象元素，以某个属性来分组，例如，以grade分组，将grade相同的放在一起：
     */
    @Test
    public void groupingBy(){
        List<Map<String, String>> list = getList();
        Map<String, List<Map<String, String>>> map = list.stream().collect(Collectors.groupingBy(c -> c.get("grade")));
        System.out.println("分组："+map);
    }

    /**
     * list转map
     * 可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
     */
    @Test
    public void toMap(){
        List<Map<String, String>> list = getList();
        Map<String, Map<String, String>> map = list.stream().collect(Collectors.toMap(c -> c.get("grade"), c -> c, (k1, k2) -> k1));
        System.out.println("list转map："+map);
    }

    /**
     * 从集合中过滤出来符合条件的元素
     */
    @Test
    public void filter(){
        List<Map<String, String>> list = getList();
        List<Map<String, String>> list1 = list.stream().filter(c -> c.get("age").equals("12")).collect(Collectors.toList());
        System.out.println("过滤："+list1);
    }

    /**
     * 将集合中的数据按照某个属性求和
     */
    @Test
    public void add(){
        List<Map<String, String>> list = getList();
        BigDecimal grade = list.stream().map(c -> new BigDecimal(c.get("age"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("求和："+grade);
    }

    /**
     * 获取list中age最大的数据
     */
    @Test
    public void maxBy(){
        List<Map<String, String>> list = getList();
        Map<String, String> map = list.stream().collect(Collectors.maxBy(Comparator.comparing(c -> c.get("age")))).get();
        System.out.println("最大值："+map);
    }

    /**
     * 获取list中age最小的数据
     */
    @Test
    public void minBy(){
        List<Map<String, String>> list = getList();
        Map<String, String> map = list.stream().collect(Collectors.minBy(Comparator.comparing(c -> c.get("age")))).get();
        System.out.println("最小值："+map);
    }

}

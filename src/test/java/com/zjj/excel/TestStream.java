package com.zjj.excel;

import com.zjj.excel.domain.Teacher;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhijiaju
 * @version 1.0
 * @date 2020/6/12 16:12
 */
public class TestStream {

    private List<Teacher> teachers= Arrays.asList(
            new Teacher(1,"明",new Date(),"男", Teacher.Status.BUS),
            new Teacher(2,"哈哈",new Date(),"女",Teacher.Status.FREE),
            new Teacher(3,"郑",new Date(),"男",Teacher.Status.FREE),
            new Teacher(4,"陈",new Date(),"男",Teacher.Status.VOCATION),
            new Teacher(5,"支",new Date(),"男",Teacher.Status.BUS),
            new Teacher(6,"我我",new Date(),"男",Teacher.Status.FREE)
    );

    @Test
    public void test1(){
        //students.stream().skip(1).forEach(System.out::println);
        int pageSize=2;
        int pageNumber=3;
        List<Teacher> areaVo = teachers.stream()
                .skip(pageSize * (pageNumber - 1))  //2
                .limit(pageSize).collect(Collectors.toList()); //2
        System.out.println(areaVo);
    }


    @Test
    public void test2(){
        List<Teacher> collect = teachers.stream().sorted(Comparator.comparing(Teacher::getName)).collect(Collectors.toList());
        System.out.println(collect);
    }



    @Test
    public void test3(){
        ArrayList<String> list = new ArrayList<>();
        list.add("张三");
        list.add("张三丰");
        list.add("风清扬");
        list.add("东方不败");
        /**
         * 自然排序
         */
        list.stream().sorted().forEach(System.out::println);
        System.out.println("-----------");
        /**
         * 比较器排序
         */
        list.stream().sorted((s1,s2)->{
            int num1 = s1.length() - s2.length();
            int num2 = num1 == 0 ? s2.compareTo(s1) : num1;
            return num2;
        }).forEach(System.out::println);

    }

    @Test
    public void test4(){
        List<Teacher> collect = teachers.stream()
                .sorted(Comparator.comparing(Teacher::getName, (x, y) -> {
                    // ToFirstChar 将汉字首字母转为拼音
                    x = ToFirstChar(x).toUpperCase();
                    y = ToFirstChar(y).toUpperCase();
                    Collator clt = Collator.getInstance();
                    return clt.compare(y, x);
                })).collect(Collectors.toList());
        System.out.println(collect);


    }
    @Test
    public void test5(){
        List<Teacher> collect = teachers.stream().filter((s) -> s.getId() > 3).sorted(
                Comparator.comparing(Teacher::getName, (x, y) -> {
                    // ToFirstChar 将汉字首字母转为拼音
                    x = ToFirstChar(x).toUpperCase();
                    y = ToFirstChar(y).toUpperCase();
                    Collator clt = Collator.getInstance();
                    return clt.compare(x, y);
                })
        ).collect(Collectors.toList());
        System.out.println(collect);


    }
    /*
    * allMatch
    * anyMATCH
    * NONEMATCH
    * FINDFIRST
    * COUNT
    * MAX
    * MIN
    *
    * */

    @Test
    public void test6(){
        boolean b = teachers.stream().anyMatch((e) -> e.getStatus().equals(Teacher.Status.FREE));
        System.out.println(b);
        List<Boolean> collect = teachers.stream().map((t) -> t.getStatus().equals(Teacher.Status.FREE)).collect(Collectors.toList());
        List<Teacher> collect1 = teachers.stream().filter((t) -> t.getStatus().equals(Teacher.Status.FREE)).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(collect1);
    }

    @Test
    public void test7(){
//        Optional<Teacher.Status> first = teachers.stream().map((t) -> t.getStatus()).findFirst();
//        System.out.println(first.get());
//        long count = teachers.stream().count();
//        System.out.println(count);
//        Optional<Teacher> max = teachers.stream().max((e1, e2) -> (Double.compare(e1.getId(), e2.getId())));
//        System.out.println(max);
//        Optional<Teacher> min = teachers.stream().min((e, f) -> (Double.compare(e.getId(), f.getId())));
//        System.out.println(min.get());
        Optional<Integer> min = teachers.stream().map(Teacher::getId).min(Double::compare);
        System.out.println(min.get());
    }

    /**
     * 获取字符串拼音的第一个字母
     * @param chinese
     * @return
     */
    public static String ToFirstChar(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();  //转为单个字符
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

    /**
     * 汉字转为拼音
     * @param chinese
     * @return
     */
    public static String ToPinyin(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

}

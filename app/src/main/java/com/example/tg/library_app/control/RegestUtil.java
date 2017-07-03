package com.example.tg.library_app.control;



import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.tg.library_app.model.BookInfo;

/**
 * Created by tg on 2017/4/24 0024.
 *
 * 正则表达类，用来从网页中获取信息
 */

public class RegestUtil {
    //提取借书信息的正则表达式
    private static String REVERTTABLE = "<tr>[\\s]*<td[\\s][\\S]+[\\s]class[\\S]+>[\\s]*[\\d][\\d]*[\\s]*</td>[\\s]*<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}<td[\\s\\S]{1,120}</td>[\\s]*</tr>";
    //提取还书信息的正则表达式
    private static String BORROWTABLE = "<tr>[\\s]*<td[\\s][\\S]+[\\s]class[\\S]+>[\\s]*[\\d][\\s]*</td>" +
            "[\\s]*<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}" +
            "<td[\\s\\S]{1,80}<td[\\s\\S]{1,80}<td[\\s\\S]{1,120}</td>[\\s]*</tr>";
    //根据value值决定获取借书还是还书信息
    public static List<String> regexTable(String result,int value) {
        List<String> lists = new ArrayList<>();
        String reg_tabset;
        if(value==1){
            reg_tabset=BORROWTABLE; //借书信息
        }else{
            reg_tabset=REVERTTABLE; //还书信息
        }
        Pattern p = Pattern.compile(reg_tabset);    //生成正则表达式适配器
        Matcher m = p.matcher(result);      //匹配正则
        while (m.find()) {
            lists.add(m.group());
        }
        return lists;
    }

    //获取每一项借还图书的信息，将他们封装到BookInfo中
    public static List<BookInfo> regexBook(List<String> lists,int value){
        List<BookInfo> listBooks = new ArrayList<>();
        BookInfo book = null;
        String regex_empty = "&nbsp;";
        String regex_id = "tdborder3[\\S]{1,10}[\\s]*([\\d][\\d]*)";
        String regex_name ="tdborder4[\\s]*>([^<]+)</td>";


        List<String> listTemp = new ArrayList<>();
        String strTemp = null;
        String str = null;
        Pattern p = null;
        Matcher m = null;
        for (int i=0;i<lists.size();i++){
            book = new BookInfo();
            str = lists.get(i);
            p = Pattern.compile(regex_id);
            m = p.matcher(str);
            while(m.find()){
                book.setId(m.group(1));
            }
            p = Pattern.compile(regex_name);
            m = p.matcher(str);
            while(m.find()){

                strTemp = m.group(1);

                strTemp = strTemp.replaceAll(regex_empty,"");
                listTemp.add(strTemp);
            }
            book.setName(listTemp.get(0));
            book.setBarCode(listTemp.get(1));
            book.setBorrow(listTemp.get(2));
            book.setRevert(listTemp.get(3));
            Integer times = Integer.valueOf(listTemp.get(4));
            book.setTimes(times);
            book.setAddress(listTemp.get(5));
            if(value==1){   //根据value值判断是否获取超期信息，只有在借书列表中才有，value=1
                String regex_state = "tdborder4[\\s]*align=center[\\s\\S]+</tr>";
                String url = "http://218.87.136.101/museweb/dzxj/dzxj";
                String regex_url = "<a[\\s]*[\\s\\S]*/dzxj([\\s\\S]*)'[\\s]*target";
                p = Pattern.compile(regex_state);
                m = p.matcher(str);
                while(m.find()){
                    String state = m.group();
                    if(state.contains("超期")){
                        book.setState(false);
                    }else if(state.contains("续借")){
                        Pattern p1 = Pattern.compile(regex_url);
                        Matcher m1 = p1.matcher(state);
                        while(m1.find()){
                            strTemp = url+m1.group(1);
                            book.setXjurl(strTemp);
                        }
                        book.setState(true);
                    }
                }
            }else{
                book.setState(null);
                book.setXjurl(null);
            }
            listBooks.add(book);
            listTemp.clear();
        }
        return listBooks;
    }
}


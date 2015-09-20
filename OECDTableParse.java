/*
 * Парсинг с сайта OECD
 * Парсит значение с таблицы для Russia
 * Возвращает значение для указанного периода времени.
 */

package app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author kemelov.gabit[at]gmail.com
 */
public class OECDTableParse {
    Document d;
    String value; // значение которое будет возвращаться
    String url = "http://stats.oecd.org/index.aspx?DatasetCode=MEI_CLI"; // сайт откуда берутся данные
    SimpleDateFormat formatter = new SimpleDateFormat( "MMM-yyyy" );
    SimpleDateFormat fmt = new SimpleDateFormat("d"); 
    String monthYear = formatter.format(new java.util.Date()); // дата в формате Sep-2015
    String currentDay = fmt.format(new java.util.Date()); // текущее число для проверки
    ArrayList<List<String>> listOfList = new ArrayList<>(); // list of rows
    
    // после 8-го числа каждого месяца берет данные 2 месячной давности
    // например, после 8-октября достанет данные за август текщего года
    public String getRussianValueLatest() throws IOException {
        d = Jsoup.connect(url).get();
        Element table = d.select("table[class=DataTable]").first(); // таблица с данными
        int count = table.select("tbody tr").size(); // count rows
        //System.out.println("monthYear: "+monthYear+" currentDay: "+currentDay);
        for(int i=0; i<count; i++) {
            String row = table.select("tbody tr").get(i).text(); // содержит все данные по месяцам
            List<String> list = new ArrayList<>(Arrays.asList(row.split(" "))); // поиск данных отсюда
            listOfList.add(list);
        }
        int rusIndex = listOfList.get(count-1).size()-2; // Russia's index in row
        //System.out.println("lisOfList size: "+listOfList.size()); // size of list that contains a list/row
        //System.out.println("last row: "+listOfList.get(count-1)); // самая последняя строчка
        //System.out.println("last row Russia: "+listOfList.get(count-1).get(rusIndex));
        // TODO: date filter before 8th and after
        
        value = listOfList.get(count-1).get(rusIndex)+" "+listOfList.get(count-1).get(0); // last value+time period
        return value;
    }
}

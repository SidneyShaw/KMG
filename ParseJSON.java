/*
 * Gets JSON from URL
 * Parses 2D JSon object
 * Finds value by date period
 */

package htmlparser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 *
 * @author Gabit Kemelov"
 * 
 */
public class ParseJSON {
    public static void main(String[] args) throws MalformedURLException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMM" );
        String end = formatter.format(new java.util.Date()); // параметр для URL
        String sURL = "http://www.eia.gov/beta/steo/dataService.cfc?v=6&f=M&start=201501&end="+end+
                "&maptype=0&ctype=linechart&method=getData";
         // Connect to the URL using java's native library
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        
        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        //Convert the input stream to a json element
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
        //JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
        
        //JsonParser jsonParser = new JsonParser();
        JsonObject rootobj = root
            .getAsJsonObject().get("VIEWSDATA")
            .getAsJsonObject().getAsJsonArray("ROWS").get(19)
            .getAsJsonObject().getAsJsonArray("DATA").get(0).getAsJsonObject();
            //.getAsJsonObject().getAsJsonObject("201507");
        String val = rootobj.get("201506").getAsString();
        System.out.println(val);
    }
    
}

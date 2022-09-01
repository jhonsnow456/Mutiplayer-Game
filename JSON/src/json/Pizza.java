package json;

import java.io.StringWriter;
import org.json.*;

public class Pizza 
{
    String pizza_name;
    String pizza_base;
    boolean spicy, veg;
    float price;
    String pizza_toppings [];
    
    Pizza(String name, String base, boolean spicy, boolean veg, float price, String toppings[])
    {
        pizza_name = name;
        pizza_base = base;
        this.spicy = spicy;
        this.veg = veg;
        this.price = price;
        pizza_toppings = toppings;
    }    
    
    //build a JSON string from the Java object
    public String to_json_string()
    {
        
        try
        {
            JSONArray jarr = new JSONArray();
            
            for (String s: pizza_toppings)
            {
                jarr.put(s);
            }


            JSONObject jobj = new JSONObject();
            jobj.put("pizza_name", pizza_name); //string value
            jobj.put("pizza_base", pizza_base); //string value
            jobj.put("pizza_toppings", jarr); //nesting of JSON array in an JSON object
            jobj.put("pizza_spicy", spicy); //boolean
            jobj.put("pizza_veg", veg); //boolean
            jobj.put("pizza_price", price); //number

            
            StringWriter sw = new StringWriter();
            jobj.write(sw);
            String result = sw.toString();
            
            sw.close();
            return result;
        }
        catch(Exception ex)
        {
            System.out.println("Err: " + ex);
            return "{}";
        }
        
    }
}

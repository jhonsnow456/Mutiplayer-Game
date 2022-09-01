package json;

import org.json.*;
public class PizzaBilling 
{
    String json;
    public PizzaBilling(String js) 
    {//{"pizza_spicy":true,"pizza_price":350,"pizza_base":"Thin Crust","pizza_veg":true,"pizza_toppings":"[\"Grilled Mushrooms\",\"Cottage Cheese\",\"Black Olives\",\"Jalepenos\",\"Onions\",\"Cherry Tomatoes\"]","pizza_name":"Veggie Delux"}
        json = js;
    }
    
    public void generateBill()
    {
        JSONObject jobj = new JSONObject(json);
        System.out.println(jobj.get("pizza_name"));
        System.out.println(jobj.get("pizza_base"));
        
        System.out.print("Toppings: " );
        JSONArray arr = jobj.getJSONArray("pizza_toppings");
        int i;
        int l = arr.length();
        for(i =0 ; i < l; i++)
        {
            System.out.print(arr.getString(i));
            if (i+1 != l)
                System.out.print(", ");
        }
        System.out.println();
        
        System.out.println("Veg: " + jobj.get("pizza_veg"));
        System.out.println("Spicy: " + jobj.get("pizza_spicy"));
        System.out.println("Price: " + (char)8377 + " "  + jobj.get("pizza_price"));
                
        
    }
    
    
    
}

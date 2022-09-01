package json;
/*
JSON (JavaScript Object Notation) is a text based, language independent data-interchange format.

JSON is built on two data structures:

1) A JSON Object (collection of name/value pairs)
-------------------------------------------------------
An object is an unordered set of name/value pairs.
It (object) begins with a { (left brace) and ends with a } (right brace). Inside it encloses name:value pairs separated by commas.

Example:
{"Emp_num":1 , "Emp_name": "Varun", "Emp_desg": "Manager"}

In various languages, this is realized as an object, struct, dictionary, hash map, or an associative array.


2) A JSON Array (list of values)
-------------------------------------
An array is an ordered collection of values. It (array) begins with a [ (left sq bracket) and ends with a ] (right sq bracket). Inside it encloses values separated by commas.

Example:
["Sunday", "Thursday", "Saturday"]

In most of the languages, this is realized as an array, a list or a vector, or a sequence.

A value can be a String in double quotes, a number, a boolean, an object or an array.

These (JSON Object and Array) structures can be nested.

Example:

{
  "PizzaName" : "Veggie Delux",
  "PizzaBase" : "Thin Crust",
  "Toppings" : ["Grilled Mushrooms", "Cottage Cheese", "Black Olives", "Jalepenos", "Onions", "Cherry Tomatoes"]
  "Spicy" : true
  "Veg": true
  "Price": 350
}

*/
public class Main {

    public static void main(String[] args) 
    {
        //Generator Module
        String temp[] = {"Grilled Mushrooms", "Cottage Cheese", "Black Olives", "Jalepenos", "Onions", "Cherry Tomatoes"};
        Pizza p = new Pizza("Veggie Delux", "Thin Crust", true, true, 350, temp);
        
        String js = p.to_json_string();
        System.out.println("******"+js);
        //Consumer Module
        PizzaBilling billing = new PizzaBilling(js);
        billing.generateBill();
    }
    
}

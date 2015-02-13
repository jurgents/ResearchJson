/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Jurgen
 */
public class ResearchJson {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JSONException {
       
       String mapping  = "{ \"typesToTable\": {\"natp\":\"natp_imp\", "
                       + "\"ander\":\"ander_imp\"}}";
    
       
       
       JSONObject objMapping = new JSONObject(mapping);
       JSONObject typesToTable = (JSONObject) objMapping.get("typesToTable");
     
       
       Map<String, String> typesToTableMapping = SimpleJsonMapToJavaMap(typesToTable);
       
       System.out.println(mapping); 
       System.out.println(typesToTableMapping); 
       
       

    }

    private static Map<String, String> SimpleJsonMapToJavaMap(JSONObject typesToTable) throws JSONException {
        Map<String, String> typesToTableMapping = new HashMap<String,String>();
        Iterator<String> types = typesToTable.keys();
        while (types.hasNext()){
            String type = types.next();
            typesToTableMapping.put(type, typesToTable.getString(type));
        }
        return typesToTableMapping;
    }
    
}

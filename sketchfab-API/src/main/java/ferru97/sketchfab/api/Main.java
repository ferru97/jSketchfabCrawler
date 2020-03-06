/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Vito
 */
public class Main {
    
    public static void main(String[] args) throws IOException, InterruptedException{
        System.out.println("SketchFab API");
        
        /*RequestHTTP request = new RequestHTTP();
        String res = request.getRequest("https://sketchfab.com/v3/models", new HashMap<String,String>());
        
        JSONObject obj = new JSONObject(res);
        JSONArray items = new JSONArray(obj.get("results").toString());
        JSONObject item_temp;
        for(int i=0; i<items.length(); i++){
            System.out.println("----------------------------------");
            item_temp = (JSONObject) items.get(i);
            System.out.println(item_temp.get("viewerUrl"));
            System.out.println(item_temp.get("likeCount"));
            System.out.println(item_temp.get("viewCount"));
        }*/
        
        SketchfabAPI api = new SketchfabAPI();
        
        ArrayList categories = api.getCategories(api.CAT_URL);
        //categories.forEach(x->System.out.println(x));
        
        ArrayList<Model> models = api.getModels(null, api.MODELS_URL);
        models.forEach(x->System.out.println(x.toString()));
        
        
    }
    
}

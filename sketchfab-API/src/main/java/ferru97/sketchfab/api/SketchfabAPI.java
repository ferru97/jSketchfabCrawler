/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Vito
 */
public class SketchfabAPI {
    //API INFO https://docs.sketchfab.com/data-api/v3/index.html
    
    private final String CAT_URL = "https://sketchfab.com/v3/categories";
    private final String MODELS_URL = "https://sketchfab.com/v3/models";
    private RequestHTTP request = new RequestHTTP();
    
    private String next_page_url;
    
    public SketchfabAPI(){}
    
    public ArrayList<String> getCategories(String api_url){
        ArrayList<String> res = new ArrayList<>();
        
        try {
            String response = request.getRequest(api_url, null);
            if(response!=null){
                JSONObject obj = new JSONObject(response);
                JSONArray categories = new JSONArray(obj.get("results").toString());
                JSONObject cat_temp;
                for(int i=0; i<categories.length(); i++){
                    cat_temp = (JSONObject) categories.get(i);
                    res.add(cat_temp.get("name").toString());
                }
                
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SketchfabAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return res;
    }
    
    
    public ArrayList<Model> getModels(Map<String,String> params, String api_url){
        ArrayList<Model> models = new ArrayList<>();
        
        try {
            String res = request.getRequest(api_url, params);
        
            JSONObject obj = new JSONObject(res);
            next_page_url = obj.get("next").toString();
            JSONArray items = new JSONArray(obj.get("results").toString());
            JSONObject item_temp;
            for(int i=0; i<items.length(); i++){
                item_temp = (JSONObject) items.get(i);
                System.out.println(item_temp.get("viewerUrl"));
                System.out.println(item_temp.get("likeCount"));
                System.out.println(item_temp.get("viewCount"));
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SketchfabAPI.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        return models;
    }
    
    
    public ArrayList<Model> getModels_Next(Map<String,String> params){
       return getModels(params, next_page_url);
    }

    public String getCAT_URL() {
        return CAT_URL;
    }

    public String getMODELS_URL() {
        return MODELS_URL;
    }
    
}

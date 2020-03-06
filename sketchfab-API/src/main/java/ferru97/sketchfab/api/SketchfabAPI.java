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
    
    public static final String CAT_URL = "https://sketchfab.com/v3/categories";
    public static final String MODELS_URL = "https://sketchfab.com/v3/models";
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
            JSONArray temp_array;
            Model temp_moel;
            for(int i=0; i<items.length(); i++){
                item_temp = (JSONObject) items.get(i);
                temp_moel = new Model();
                temp_moel.setName(item_temp.get("name").toString());
                temp_moel.setUrl(item_temp.get("viewerUrl").toString());
                temp_moel.setUid(item_temp.get("uid").toString());
                temp_moel.setDate(item_temp.get("publishedAt").toString());
                temp_moel.setLike_count(Integer.valueOf(item_temp.get("likeCount").toString()));
                temp_moel.setView_count(Integer.valueOf(item_temp.get("viewCount").toString()));
                temp_moel.setComment_count(Integer.valueOf(item_temp.get("commentCount").toString()));
                temp_moel.setVertex_count(Integer.valueOf(item_temp.get("vertexCount").toString()));
                temp_moel.setFace_count(Integer.valueOf(item_temp.get("faceCount").toString()));
                temp_moel.setSound_count(Integer.valueOf(item_temp.get("soundCount").toString()));
                
                temp_array = new JSONArray(item_temp.get("tags").toString());
                for(int k=0; k<temp_array.length(); k++)
                    temp_moel.addTag(((JSONObject)temp_array.get(k)).get("slug").toString());
                
                temp_array = new JSONArray(item_temp.get("categories").toString());
                for(int k=0; k<temp_array.length(); k++)
                    temp_moel.addTag(((JSONObject)temp_array.get(k)).get("name").toString());
                
                models.add(temp_moel);
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SketchfabAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
        
        return models;
    }
    
    
    public ArrayList<Model> getModels_Next(Map<String,String> params){
       return getModels(params, next_page_url);
    }

    public String getMODELS_URL() {
        return MODELS_URL;
    }
    
}

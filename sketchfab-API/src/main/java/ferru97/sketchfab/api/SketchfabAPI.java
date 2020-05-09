/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;

import ferru97.sketchfab.utils.RequestHTTP;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public static final String GET_CATEGOTIES_API_URL = "https://sketchfab.com/v3/categories";
    public static final String GET_MODELS_API_URL = "https://sketchfab.com/v3/models";
    public static final String GET_COMMENTS_API_URL = "https://sketchfab.com/v3/comments";
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
                    res.add(cat_temp.get("slug").toString()+"/"+cat_temp.get("name").toString());
                }
                
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SketchfabAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return res;
    }
    
    
    public ArrayList<Model> getModels(Map<String,String> params, String api_url){
        ArrayList<Model> models = new ArrayList<>();
        next_page_url = api_url;
        
        try {
            String res = request.getRequest(api_url, params);
            if (res == null)
                return null;
                
            JSONObject obj = new JSONObject(res);
            next_page_url = obj.get("next").toString();
            JSONArray items = new JSONArray(obj.get("results").toString());
            JSONObject item_temp;
            JSONArray temp_array;
            Model temp_model;
            for(int i=0; i<items.length(); i++){
                item_temp = (JSONObject) items.get(i);
                temp_model = new Model();
                temp_model.setName(item_temp.get("name").toString());
                temp_model.setUrl(item_temp.get("viewerUrl").toString());
                temp_model.setUid(item_temp.get("uid").toString());
                temp_model.setDate(item_temp.get("publishedAt").toString());
                temp_model.setLike_count(Integer.valueOf(item_temp.get("likeCount").toString()));
                temp_model.setView_count(Integer.valueOf(item_temp.get("viewCount").toString()));
                temp_model.setComment_count(Integer.valueOf(item_temp.get("commentCount").toString()));
                temp_model.setVertex_count(Integer.valueOf(item_temp.get("vertexCount").toString()));
                temp_model.setFace_count(Integer.valueOf(item_temp.get("faceCount").toString()));
                temp_model.setSound_count(Integer.valueOf(item_temp.get("soundCount").toString()));
                
                temp_array = new JSONArray(item_temp.get("tags").toString());
                for(int k=0; k<temp_array.length(); k++)
                    temp_model.addTag(((JSONObject)temp_array.get(k)).get("slug").toString());
                
                temp_array = new JSONArray(item_temp.get("categories").toString());
                for(int k=0; k<temp_array.length(); k++)
                    temp_model.addCategory(((JSONObject)temp_array.get(k)).get("name").toString());
                
                temp_model.setComments(getModelComments(temp_model.getUid()));
                
                models.add(temp_model);
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SketchfabAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
        
        return models;
    }
    
    
    private ArrayList<String> getModelComments(String model_id){
        ArrayList<String> comments = new ArrayList<>();
        HashMap<String,String> params = new HashMap();
        params.put("model", model_id);
        
        try {
            String res = request.getRequest(GET_COMMENTS_API_URL, params);
            if(res==null)
                return comments;
            JSONObject obj = new JSONObject(res);
            JSONArray items = new JSONArray(obj.get("results").toString());
            JSONObject item_temp;
            for(int i=0; i<items.length(); i++){
                item_temp = (JSONObject) items.get(i);
                comments.add(item_temp.get("body").toString());
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SketchfabAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
        
        return comments;
    }
    
    
    public ArrayList<Model> getModels_Next(Map<String,String> params){
       return getModels(null, next_page_url);
    }

    public String getMODELS_URL() {
        return GET_MODELS_API_URL;
    }
    
    public String getNextPageUrl(){
        return this.next_page_url;
    }
    
    public void reconnect(){
        request.reconnect();
    }
    
}

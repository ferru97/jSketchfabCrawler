/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;

import ferru97.sketchfab.utils.SQLDatabase;
import ferru97.sketchfab.utils.Writer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
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
        
        File f = Writer.createTable("test_output", Model.getTableColumsHeader());
        
        //ArrayList categories = api.getCategories(api.CAT_URL);
        //categories.forEach(x->System.out.println(x));
        
       /* HashMap<String,String> params = new HashMap<>();
        params.put("sort_by", "-viewCount");
        ArrayList<Model> models = api.getModels(params, api.MODELS_URL);
        Writer.appendRowsTable(f, models.stream());
        for(int i=0; i<5; i++){
            System.out.println("Execute i= "+i );
            models = api.getModels_Next(null);
            if(models.size()>0){
               Writer.appendRowsTable(f, models.stream());
               long wait = (long) (0 + Math.random() * (1 - 0));
               TimeUnit.MILLISECONDS.sleep(wait);  
            }else{
                System.out.println("Fail? = "+i+" next="+api.getNextUrl());
            }
        }*/
        
      SQLDatabase database = new SQLDatabase("localhost","sketchfab_data" , "root", "", 3308);
      database.connect();
      
      //ArrayList<String> categories = api.getCategories(api.CAT_URL);
      //categories.forEach(x->database.insertCategory(x));
      
      
        HashMap<String,String> params = new HashMap<>();
        params.put("sort_by", "-likeCount");
        ArrayList<Model> models = api.getModels(params, api.MODELS_URL);
        Writer.appendRowsTable(f, models.stream());
        for(int i=0; i<3; i++){
            System.out.println("Execute i= "+i );
            models = api.getModels_Next(null);
            if(models.size()>0){
               models.stream().forEach(m->database.insertModel(m));
               long wait = (long) (0 + Math.random() * (1 - 0));
               TimeUnit.MILLISECONDS.sleep(wait);  
            }else{
                System.out.println("Fail? = "+i+" next="+api.getNextUrl());
            }
        }
      
      database.closeConnection();
        
        
        
    }
    
}

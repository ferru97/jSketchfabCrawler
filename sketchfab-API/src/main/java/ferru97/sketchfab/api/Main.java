/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;

import ferru97.sketchfab.utils.FritzTR064;
import ferru97.sketchfab.utils.SQLDatabase;
import ferru97.sketchfab.utils.Writer;
import java.awt.Toolkit;
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
      System.out.println("jSketchfabCrawler start");
       
      SketchfabAPI api = new SketchfabAPI();
        
      SQLDatabase database = new SQLDatabase("localhost","test_sk" , "root", "", 3308);
      database.connect();
      
        HashMap<String,String> params = new HashMap<>();
        params.put("sort_by", "viewCount");
        ArrayList<Model> models = api.getModels(params, SketchfabAPI.GET_MODELS_API_URL);
        models.stream().forEach(m->database.insertModel(m));
        for(int i=0; i>-1; i++){
            System.out.println("Execute i= "+i );
            models = api.getModels_Next(null);
            if(models!=null && models.size()>0){
               models.stream().forEach(m->database.insertModel(m));
 
            }else{
                Toolkit.getDefaultToolkit().beep();
                TimeUnit.MILLISECONDS.sleep(100);
                Toolkit.getDefaultToolkit().beep();
                System.out.println("Fail/Calm? = "+i+" next="+api.getNextPageUrl());
                TimeUnit.MINUTES.sleep(2); 
                api.reconnect();
            }
        }
      
      database.closeConnection();

    }
    
}

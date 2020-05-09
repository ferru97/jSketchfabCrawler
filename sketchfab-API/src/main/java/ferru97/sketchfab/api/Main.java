/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;

import ferru97.sketchfab.utils.SQLDatabase;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Vito
 */
public class Main {
    
    public static void main(String[] args) throws IOException, InterruptedException{
        String db_host,db_name,db_user,db_psw;
        int db_port;
        if(args.length!=1 || args[0].split("/").length!=5){
            System.out.println("Invalid arguments: provide the database credential "
                    + "in the following format: 'db_address/db_name/db_user/db_password/db_port'");
            return;
        }else{
            String[] params = args[0].split("/");
            db_host = params[0];
            db_name = params[1];
            db_user = params[2];
            db_psw = params[3];
            db_port = Integer.parseInt(params[4]);         
        }  

        System.out.println("jSketchfabCrawler start");

        SketchfabAPI api = new SketchfabAPI();
        ArrayList<String> categories = api.getCategories(SketchfabAPI.GET_CATEGOTIES_API_URL);
        
        SQLDatabase database = new SQLDatabase(db_host, db_name, db_user, db_psw, db_port);
        database.connect();
        categories.forEach(cat->database.insertCategory(cat));
        

        HashMap<String,String> post_data = new HashMap<>();
        post_data.put("sort_by", "viewCount");
        ArrayList<Model> models = api.getModels(post_data, SketchfabAPI.GET_MODELS_API_URL);
        Date d;
        models.stream().forEach(m->database.insertModel(m));
        for(int i=0; i>-1; i++){
            d = new Date();
            System.out.println("Execute request "+i+" - time:"+d.toString() );
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

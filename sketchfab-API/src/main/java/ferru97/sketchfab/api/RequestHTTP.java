/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Iterator;
import java.util.Map;
/**
 *
 * @author Vito
 */
public class RequestHTTP {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    
    public RequestHTTP(){}
    
    public String getRequest(String url, Map<String,String> params) throws IOException, InterruptedException{
        if(params!=null && params.size() > 0)
            url = url + "/?" + parseParams(params);
        System.out.println(url);
         
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            System.out.println("Request Error: status code="+response.statusCode()+" URL="+url);
            return null;
        }

        return response.body();

    }
    
    
    private static String parseParams(Map<String,String> params){
        StringBuilder get_params = new StringBuilder();
        
        Iterator it = params.entrySet().iterator();
        Map.Entry temp;
        while(it.hasNext()){
            temp = (Map.Entry) it.next();
            get_params.append(temp.getKey()).append("=").append(temp.getValue()).append("?");
        }
        
        get_params.deleteCharAt(get_params.length() - 1);
        return get_params.toString();
        
    }
    
    
}

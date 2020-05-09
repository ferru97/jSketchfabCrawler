/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.utils;

import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Vito
 */
public class FritzTR064 {
    private String usr;
    private String psw;
    private String ip;

    public FritzTR064(String ip, String usr, String psw) {
        this.usr = usr;
        this.psw = psw;
        this.ip = ip;
        
    }
    
    /*public void changePubicIP(){
        FritzConnection fc = new FritzConnection(ip,usr,psw);
        try {
            fc.init();
            Service service = fc.getService("WANPPPConnection:1");
            Action action = service.getAction("NewExternalIPAddress");
            Response response;
            try {
                response = action.execute();
                return response.toString();
            } catch (IOException ex) {
                System.out.println(ex.toString());
                return null;
            } catch (ClassCastException ex) {
                System.out.println(ex.toString());
                return null;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        } catch (JAXBException ex) {
            System.out.println(ex.toString());
        }
        
    }*/
    
    
    public void test(){
        FritzConnection fc = new FritzConnection("192.168.178.1","fritz-user","Cikkio10");
        try {
            fc.init();
            fc.printInfo();
        } catch (IOException ex) {
            Logger.getLogger(FritzTR064.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(FritzTR064.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

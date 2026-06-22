
package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Burger;
import model.List;
import view.SearchBestCustomer;

public class BurgerController {
    
    //------------Order.txt reload List--------------//
    public static List burgerList = new List();
    
    public static void burgerLoadList() throws FileNotFoundException, IOException{
    
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
       
       String line;
       while((line = br.readLine()) != null){
           if(!line.trim().isEmpty()){
              String[] data = line.split(",");
              
              String orderId = data[0];
              String customerId = data[1];
              String name = data[2];
              int burgerQty = Integer.parseInt(data[3]);
              int status = Integer.parseInt(data[4]);
              
              Burger burger = new Burger(orderId,customerId,name,burgerQty,status);
              List intList = new List();
              intList.addLast(burger);
              
           }
       }
         br.close();
    }
    
    //-------------------------Genarate Order ID-----------------------------//
    public static String genarateOrderId() throws IOException{
        String lastLine = null;
        
       try {
           BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
           
           String line;
           
           while((line = br.readLine()) != null){
               if(!line.trim().isEmpty()){
                lastLine = line;
               }
           }
           br.close();
           
       } catch (FileNotFoundException ex) {
           return "O000";
       }
       
       if(lastLine == null || lastLine.trim().isEmpty()){
           return "O000";
       }
       String[] data = lastLine.split(",");
       
       if(data.length == 0){
           return "O000";
       }else{
       
       String lastOrderId = data[0];
       int num = Integer.parseInt(lastOrderId.substring(1));
       
       return String.format("O%03d", num+1);
       }
       
    }
    
    //--------------------Genarate Customer ID----------------------//
    public static String genarateCustomerId(){
        String line;
        int count = 0;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("Order.txt"));
        } catch (FileNotFoundException ex) {
            return "C000";
        }
        try {
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    count++;
                }
            }
             br.close();
        } catch (IOException ex) {
            if(count == 0){
                return "C000";
            }
        }
       
        String[] tempCustId = new String[count];
        
      String line1;
      BufferedReader br1;
        try {
            br1 = new BufferedReader(new FileReader("Order.txt"));
        } catch (FileNotFoundException ex) {
            return "C000";
        }
      int index = 0;
        try {
            while((line1 = br1.readLine()) != null){
                if(!line1.trim().isEmpty()){
                    String[] data = line1.split(",");
                    if(data.length > 1){
                        tempCustId[index] = data[1];
                        index++;
                    }
                }
            }
            
        } catch (IOException ex) {
            return "C000";
        } 
      
      int count1 = 0;
      for(int i = 0; i < tempCustId.length; i++){
          for(int j = i + 1; j < tempCustId.length; j++){
              if(tempCustId[i].equalsIgnoreCase(tempCustId[j])){
                  count1++;
                  break;
              }
          }
      }
      
      String[] dupRemoveCustomerIdArray = new String[tempCustId.length - count1];
      
      int ind = 0;
      for(int i = 0; i < tempCustId.length; i++){
          boolean dupHave = false;
          String currtCustId = tempCustId[i];
          
          for(int x = 0; x < dupRemoveCustomerIdArray.length; x++ ){
              if(dupRemoveCustomerIdArray[x] != null && currtCustId.equalsIgnoreCase(dupRemoveCustomerIdArray[x]) ){
                  dupHave = true;
                  break;
              }
          }
          if(!dupHave){
              dupRemoveCustomerIdArray[ind] = currtCustId;
              ind++;
          }
          
      }
      for(int i = 0; i < dupRemoveCustomerIdArray.length; i++){
          for(int j = i + 1; j < dupRemoveCustomerIdArray.length; j++){
              int iD1 = Integer.parseInt(dupRemoveCustomerIdArray[i].substring(1));
              int iD2 = Integer.parseInt(dupRemoveCustomerIdArray[j].substring(1));
              if(iD1 > iD2){
                  String temp = dupRemoveCustomerIdArray[i];
                  dupRemoveCustomerIdArray[i] = dupRemoveCustomerIdArray[j];
                  dupRemoveCustomerIdArray[j] = temp;
              }
          }
      }
      if(dupRemoveCustomerIdArray.length == 0){
          return "C000";
      }
      
      int num = Integer.parseInt(dupRemoveCustomerIdArray[dupRemoveCustomerIdArray.length - 1].substring(1));
      return String.format("C%03d",num + 1);
    }
    
    //------------------------Place Order----------------------//
    public static boolean placeOrder(String orderId,String customerId, String customerName, int qty, int status){
        FileWriter fw = null;
        
        try {
            fw = new FileWriter("Order.txt",true);
            
            fw.write(orderId+","+customerId+","+customerName+","+qty+","+status+"\n");
            fw.close();
            return true;
            
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    
    //-------------------------Customer ID Search-------------------------//
    public static Burger searchCustomerId(String customerID) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        String line;
        Burger burger = null;
        while((line = br.readLine()) != null){
            String[] data = line.split(",");
            if(data.length > 2){
                if(customerID.equalsIgnoreCase(data[1])){
                    String orderId = data[0];
                    String customerId = data[1];
                    String custName = data[2];
                    int qty = Integer.parseInt(data[3]);
                    int status = Integer.parseInt(data[4]);
                    burger = new Burger(orderId,customerId,custName,qty,status);
                    break;
                }
            }
        }
        br.close();
        
        return burger;
    }
    
    //--------------------------Search Best Customer--------------------//
    
    public static Object[][] searBestCoustome(){
    
     int count = 0;
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                if(data.length > 4){
                    if(data[4].equals("1")){
                        count++;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Error...");
        } catch (IOException ex) {
            System.out.println("File Error...");
        }
        
        String[] customerIdArray = new String[count];
        String[] nameArray = new String[count];
        double[] totalArray = new double[count];
        
        line = "";
        int index = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String[] data = line.split(",");
                    if(data.length > 4){
                        if(data[4].equalsIgnoreCase("1")){
                            customerIdArray[index] = data[1];
                            nameArray[index] = data[2];
                            int qty = Integer.parseInt(data[3]);
                            double total = (double)qty * Burger.UNIT_PRICE;
                            totalArray[index] = total;
                            index++;
                            
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SearchBestCustomer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchBestCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int count2 = 0;
        for(int i = 0; i < customerIdArray.length; i++){
            for(int j = i + 1; j < customerIdArray.length; j++){
                if(customerIdArray[i].equalsIgnoreCase(customerIdArray[j])){
                    count2++;
                    break;
                }
            }
        }
        String[] dupRemoveCustomerIdArray = new String[customerIdArray.length - count2];
        String[] dupRemoveNameArray = new String[customerIdArray.length - count2];
        double[] dupRemoveTotalArray = new double[customerIdArray.length - count2];
        
        int in = 0;
        boolean[] visited = new boolean[customerIdArray.length];
        
        for(int i = 0; i < customerIdArray.length; i++ ){
            boolean isDup = false;
            if(visited[i]) continue;
            
            for(int j = i + 1; j < customerIdArray.length; j++){
                if(customerIdArray[i].equalsIgnoreCase(customerIdArray[j])){
                    totalArray[i] += totalArray[j];
                    visited[j] = true;
                }
            }
            String currentCustId = customerIdArray[i];
            String currentName = nameArray[i];
            double currentTotal = totalArray[i];
      
            for(int x = 0; x < dupRemoveCustomerIdArray.length; x++ ){
                if((dupRemoveCustomerIdArray[x] != null) && currentCustId.equalsIgnoreCase(dupRemoveCustomerIdArray[x])){
                    isDup = true;
                }
            }
            
            if(!isDup){
                dupRemoveCustomerIdArray[in] = currentCustId;
                dupRemoveNameArray[in] = currentName;
                dupRemoveTotalArray[in] = currentTotal;
                in++;
            }
        }
        
        //-------sort Array--------------------------------------
        
        for(int i = 0; i < dupRemoveCustomerIdArray.length; i++){
            for(int j = i + 1; j < dupRemoveCustomerIdArray.length; j++){
                if(dupRemoveTotalArray[i] < dupRemoveTotalArray[j]){
                    String tempId = dupRemoveCustomerIdArray[i];
                    dupRemoveCustomerIdArray[i] = dupRemoveCustomerIdArray[j];
                    dupRemoveCustomerIdArray[j] = tempId;
                    
                    String tempName = dupRemoveNameArray[i];
                    dupRemoveNameArray[i] = dupRemoveNameArray[j];
                    dupRemoveNameArray[j] = tempName;
                    
                    double tempTotal = dupRemoveTotalArray[i];
                    dupRemoveTotalArray[i] = dupRemoveTotalArray[j];
                    dupRemoveTotalArray[j] = tempTotal;
                }
            }
        }
        
        Object[][] data = new Object[dupRemoveCustomerIdArray.length][3];
        
        for(int i = 0; i < dupRemoveCustomerIdArray.length; i++){
            data[i][0] = dupRemoveCustomerIdArray[i];
            data[i][1] = dupRemoveNameArray[i];
            data[i][2] = dupRemoveTotalArray[i];
        }
        return data;
    }
    
}

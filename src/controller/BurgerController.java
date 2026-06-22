
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
    public static List burgerList = new List();
    //------------Order.txt reload List--------------//
     
    
    public static void burgerLoadList() throws FileNotFoundException, IOException{
        burgerList = new List();
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
              burgerList.addLast(burger);
              
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
    
    //------------------Search Customer----------------------------//
    
    public static Object[][] searchCustomer(String customerId) throws FileNotFoundException, IOException{
    
        String line;
        int count = 0;
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        while((line = br.readLine()) != null){
            String[] data = line.split(",");
            if(customerId.equalsIgnoreCase(data[1])){
                count++;
            }
        }
        br.close();
        
        Object[][] customer = new Object[count][3];
        
        String line1;
        int index = 0;
        BufferedReader br1 = new BufferedReader(new FileReader("Order.txt"));
        while((line1 = br1.readLine())!=null){
            String[] data = line1.split(",");
            if(customerId.equalsIgnoreCase(data[1])){
                int qty = Integer.parseInt(data[3]);
                String total = String.format("%.2f", (double)qty * Burger.UNIT_PRICE);
                customer[index][0] = data[0];
                customer[index][1] = data[3];
                customer[index][2] = total;
                index++;
            }
        }
        br1.close();
        
        return customer;
    }
    //----------------Name Set----------------------//
    public static String nameSet(String customerId) throws FileNotFoundException, IOException{
        String line;
        String name = "";
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        while((line = br.readLine()) != null){
            String[] data = line.split(",");
            if(customerId.equalsIgnoreCase(data[1])){
                name = data[2];
                break;
            }
        }
        br.close();
        return name;
    }
    
    //----------------------------Search Order---------------------//
    
    public static Burger searchOrder(String orderId) throws FileNotFoundException, IOException{
        String line;
        Burger burger = null;
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        while((line = br.readLine())!= null){
            String[] data = line.split(",");
            if(orderId.equalsIgnoreCase(data[0])){
                String orderID = data[0];
                String custID = data[1];
                String name = data[2];
                int qty = Integer.parseInt(data[3]);
                int status = Integer.parseInt(data[4]);
                
                burger = new Burger(orderID,custID,name,qty,status);
            }
        }
        br.close();
        return burger;
    }
    
    //--------------------------Preparing Order Report --------------------//
    
    public static Object[][] preparingOrders() throws FileNotFoundException, IOException{
        String line;
        int count = 0;
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        while((line = br.readLine())!= null){
            String[] data = line.split(",");
            if(data[4].equalsIgnoreCase("0")){
                count++;
            }
        }
        br.close();
        
        Object[][]order = new Object[count][5];
        int index = 0;
        String line1;
        BufferedReader br1 = new BufferedReader(new FileReader("Order.txt"));
        while((line1 = br1.readLine())!= null){
            String[] data = line1.split(",");
            if(data[4].equalsIgnoreCase("0")){
                order[index][0] = data[0];
                order[index][1] = data[1];
                order[index][2] = data[2];
                order[index][3] = data[3];
                int qty = Integer.parseInt(data[3]);
                
                String total = String.format("%.2f", (double)(qty * Burger.UNIT_PRICE));
                order[index][4] = total;
                index++;
            }
        }
        br1.close();
        return order;
    }
    
    //----------------------------Delivered Order Report----------------------//
    
    public static Object[][] deliverdOrders() throws FileNotFoundException, IOException{
        String line;
        int count = 0;
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        while((line = br.readLine())!= null){
            String[] data = line.split(",");
            if(data[4].equalsIgnoreCase("1")){
                count++;
            }
        }
        br.close();
        
        Object[][]order = new Object[count][5];
        int index = 0;
        String line1;
        BufferedReader br1 = new BufferedReader(new FileReader("Order.txt"));
        while((line1 = br1.readLine())!= null){
            String[] data = line1.split(",");
            if(data[4].equalsIgnoreCase("1")){
                order[index][0] = data[0];
                order[index][1] = data[1];
                order[index][2] = data[2];
                order[index][3] = data[3];
                int qty = Integer.parseInt(data[3]);
                
                String total = String.format("%.2f", (double)(qty * Burger.UNIT_PRICE));
                order[index][4] = total;
                index++;
            }
        }
        br1.close();
        return order;
    }
    
    //----------------------------Delivered Order Report----------------------//
    
    public static Object[][] canceledOrders() throws FileNotFoundException, IOException{
        String line;
        int count = 0;
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        while((line = br.readLine())!= null){
            String[] data = line.split(",");
            if(data[4].equalsIgnoreCase("2")){
                count++;
            }
        }
        br.close();
        
        Object[][]order = new Object[count][5];
        int index = 0;
        String line1;
        BufferedReader br1 = new BufferedReader(new FileReader("Order.txt"));
        while((line1 = br1.readLine())!= null){
            String[] data = line1.split(",");
            if(data[4].equalsIgnoreCase("2")){
                order[index][0] = data[0];
                order[index][1] = data[1];
                order[index][2] = data[2];
                order[index][3] = data[3];
                int qty = Integer.parseInt(data[3]);
                
                String total = String.format("%.2f", (double)(qty * Burger.UNIT_PRICE));
                order[index][4] = total;
                index++;
            }
        }
        br1.close();
        return order;
    }
    
    //------------------------Entered Order ID search And send Details------------------//
    public static Burger orderIdSearchAndOrderDetails(String orderId) throws FileNotFoundException, IOException{
        String line;
        Burger burger = null;
        
        BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
        while((line = br.readLine())!= null){
            String[] data = line.split(",");
            if(orderId.equalsIgnoreCase(data[0].trim())){
                String ordId = data[0];
                String custId = data[1];
                String name = data[2];
                int bQty = Integer.parseInt(data[3]);
                int status = Integer.parseInt(data[4]);
                
                burger = new Burger(ordId,custId,name,bQty,status);
                break;
            }
        }
        br.close();
        return burger;
    }
    
    //------------------------find update order index---------------------------//
    public static int findIndex(String orderId){
        String line;
        int index = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Order.txt"));
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    String[] data = line.split(",");
                    
                    if(orderId.equalsIgnoreCase(data[0])){
                       return index;
                    }
                }
                index++;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File Error...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    //---------------------Update Order------------------------//
    
    public static boolean updateOrder(int index,Burger burger) throws IOException{
        burgerLoadList();
        boolean isUpdate = burgerList.update(index, burger);
        
        if(isUpdate){
            reWrite();
        }
        return isUpdate;
    }
    
    //---------------Re-Write file--------------------------------//
    
    public static void reWrite(){
        try (FileWriter fw = new FileWriter("Order.txt")) {
            for(int i = 0; i < burgerList.size(); i++){
                Burger b = burgerList.get(i);
                
                fw.write(b.getOrderId()+","+ b.getCustomerId()+","+ b.getCustomarName()+","+ b.getBurgerQty()+","+ b.getStatus()+"\n");
            }
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
           
    
    }
    
}

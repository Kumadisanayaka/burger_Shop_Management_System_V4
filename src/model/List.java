package model;
      
public class List {
   private Node first;
   
   //--------------Add Burger-------------//
   public boolean add(int index, Burger burger){
       Node node = new Node(burger);
       if(index >= 0 && index <= size()){
           if(index==0){
               node.next = first;
               first = node;
           }else{
               int count = 0;
               Node temp = first;
               if(count < index - 1){
                   temp = temp.next;
                   count++;
               }
               node.next = temp.next;
               temp.next = node;
           }
           return true;
           }
           return false;
    }
   //-------------Add First Burger-----------//
   public boolean addFirst(Burger burger){
       return add(0,burger);
   }
   //------------Add Last Burger------------//
   public boolean addLast(Burger burger){
       return add(size(),burger);
   }  
   //-----------find size------------//
   public int size(){
       int count = 0;
       Node temp = first;
       while(temp != null){
           temp = temp.next;
           count++;
       }
       return count;
   }
   //-------------Update Order---------//
   public boolean update(int index, Burger burger){
       Node temp = first;
       if(index >= 0 && index < size()){
            int count = 0;
            while(count < index){
                temp = temp.next;
                count++;
           }
            temp.burger = burger;
 
           return true;
       }
       return false;
   }
   //-----------Remove Burger---------//
   public boolean remove(int index){
       if(index >= 0 && index < size()){
           if(index == 0){
               first = first.next;
           }else{
               int count = 0;
               Node temp = first;
               while(count < index - 1){
                   count++;
                   temp = temp.next;
               }
               temp.next = temp.next.next;
           }
           return true;
       }
       return false;
   }
   //-------------Remove First Burger-------------//
   public boolean removeFirst(){
       return remove(0);
   }
   //------------Remove Last Burger-------------//
   public boolean removeLast(){
       return remove(size()-1);
   }
   //-----------Get burger------------------//
   public Burger get(int index){
       if(index >= 0 && index < size()){
           int count = 0;
           Node temp = first;
           while(count < index){
               count++;
               temp = temp.next;
           }
           return temp.burger;
       }
       return null;
   }
   //---------indexOf-----------------------//
   public int indexOf(Burger burger){
       int index = 0;
       Node temp = first;
       while(temp != null){
           if(burger.equals(temp.burger)){
               return index;
           }
           index++;
           temp = temp.next;
       }
       return -1;
   }
   
   //-------------contains-------------------//
   public boolean contains(Burger burger){
       return indexOf(burger) != -1;
   }
   //-----------Remove Burger---------------//
   public boolean remove(Burger burger){
       int index = indexOf(burger);
       return remove(index);
   }
   
   
//----------Node class------------
    class Node{
     private Burger burger;
     private Node next;

        public Node(Burger burger) {
          this.burger = burger;
        }
    }
}



package burgerShop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class List {
   private Burger[] burgerArray;
   private int nextIndex;
   private int initSize;
   private double loadFact;

    public List() {
        nextIndex = 0;
        loadFact = 0;
        initSize = 0;
        burgerArray = new Burger[initSize];
    }
    public List(int initSize, double loadFact){
        burgerArray = new Burger[initSize];
        this.loadFact = loadFact;
        nextIndex = 0;
        this.initSize = initSize;
    }
    public void add(Burger burger){
        if(nextIndex >= burgerArray.length){
            extendArray();
        }
        burgerArray[nextIndex++] = burger;
    }
    
    public void addLast(Burger burger){
        add(burger);
    }
    public void addFirst(Burger burger){
        add(0,burger);
    }
    public void add(int index, Burger burger){
        if(index >= 0 && index <= nextIndex){
            for(int i = nextIndex - 1; i >= index; i--){
                burgerArray[i + 1] = burgerArray[i];
            }
            burgerArray[index] = burger;
            nextIndex++;
        }
    }
    public void removeFirst(){
        remove(0);
    }
    public void removeLast(){
        remove(nextIndex - 1);
    }
    public void remove(int index){
        if(index >= 0 && index < nextIndex){
            for(int i = index; i < nextIndex - 1; i++){
                burgerArray[i] = burgerArray[i + 1]; 
            }
            nextIndex--;
        }
    }
    
    public Burger get(int index){
        return index >= 0 && index < nextIndex ? burgerArray[index]:null;
    }
    public void printList(){
        System.out.println("[");
        for(int i = 0; i < nextIndex; i++){
            System.out.println(burgerArray[i]+",");
        }
        System.out.println(isEmpty()?"empty]":"\b]");
    }
    public boolean isEmpty(){
        return nextIndex <= 0;
    }
    public int size(){
        return nextIndex;
    }
    
    private void extendArray(){
        Burger[] tempBurgerArray = new Burger[(int)(burgerArray.length * loadFact + 1)];
        for(int i = 0; i < burgerArray.length; i++){
            tempBurgerArray[i] = burgerArray[i];
        }
        burgerArray = tempBurgerArray;
    }
    
    //-----------------Genarate Order Id-----------------------
    
    
}

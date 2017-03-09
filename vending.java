/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toc;
import java.util.*;
/**
 *
 * @author Aditya
 */

class states{
    String state;
    public void change(String s){
        state=s;
    }
}
public class vending{
    List<String> seq_of_states=new ArrayList(); 
    String[] items=new String[4];        
    int[] diff_cost=new int[4];              
    String item;
    int pos;
    int remain=0; 
    Scanner in=new Scanner(System.in);
    states var=new states();      
    String state_at;
    
    vending(String state){
        var.state=state;               
        seq_of_states.add(var.state);      
        init();
    }
    
    void init(){                           
        items[0]="L";
        items[1]="T";
        items[2]="C";
        items[3]="M";
        diff_cost[0]=20;
        diff_cost[1]=10;
        diff_cost[2]=30;
        diff_cost[3]=20;
    }
    
    void initiating(String input){
        remain=0;
        seq_of_states.add("Initiation");
        
        if(input.equals("S")){                       
            if(var.state.equals("Terminated")){
                System.out.println("Initiating.....\nPlease wait for a few seconds");
                state_at="Start";
                start();
            }                        
        }
        else if(input.equals("q")){                   
            if(var.state.equals("Terminated")){
                state_at="Terminated";
                var.change(state_at);
                terminated();
            }
            else{
                state_at="Terminating";
                var.change(state_at);
                terminating();
                
            }
        }
        else if(input.equals("B")){                   
            if(var.state.equals("Dispose")){
                state_at="Start";
                seq_of_states.add("Start");
            }   
        }
        var.change(state_at);
    }
    
    void start(){
        seq_of_states.add("Start");                       
        int i;
        menu();
        item=in.next();
        for(i=0;i<items.length;i++){
            if(item.equals(items[i])){                
                remain=diff_cost[i];
                pos=i;
                break;
            }
        }
        if(remain>0)
            state_at="Selected";
        var.change(state_at);
        selected();
    }
    
    void menu(){                                                              
        System.out.println("Select an item from the following");
        System.out.println("Latte            INR 20      L");
        System.out.println("Tea              INR 10      T");
        System.out.println("Cappuchino       INR 30      C");
        System.out.println("Masala Chai      INR 20      M");      
    }
    
    void selected(){
                                                                                           
        if(var.state.equals("Selected")){                     
            seq_of_states.add("Selected");                    
            System.out.println("You selected "+item);
            state_at="Incomplete";
            var.change(state_at);
            incomplete();
        }
        else{
            state_at="Invalid";                                     
            var.change(state_at);
            invalid();
        }
        
    }
    void invalid(){
        seq_of_states.add("Invalid");                                      
        System.out.println("You did not select anything valid");
        state_at="Start";                                           
        var.change(state_at);
        start();
    }
    void incomplete(){      
        int temp;   
        if(var.state.equals("Incomplete"))                         
        {
            seq_of_states.add("Incomplete");
            while(remain>0){
                System.out.println("You have to pay "+remain+" \nEnter your next denomination");
                temp=in.nextInt();
                if((temp==1)||(temp==5)||(temp==10)){
                    if(isAllowed(temp)){
                        state_at="Incomplete";
                        seq_of_states.add("Incomplete");
                        remain=remain-temp;
                    }                        
                }
                else{
                    state_at="Incorrect";                       
                    incorrect();
                }
                var.change(state_at);
            }
            if(remain==0){
                state_at="Complete";                         
            }
            var.change(state_at);
            complete();
        }
        
    }
    boolean isAllowed(int currency){                                
        if(currency<=remain)
            return true;                                    
    return false;
    }
    void incorrect(){
        seq_of_states.add("Incorrect");                                
        System.out.println("Invalid denomination\nPlease enter coins of 1,5 and 10 only");
    }
    void complete(){
        seq_of_states.add("Complete");                                 
        state_at="Dispose";
        var.change(state_at);
        dispose();                                                  
    }
    void dispose(){
        seq_of_states.add("Dispose");                                   
        System.out.print("Here is your fresh and hot cup of ");
        switch(pos){
            case 0:
                System.out.println("Latte");
                break;
            case 1:
                System.out.println("Tea");
                break;
            case 2:
                System.out.println("Cappuchino");
                break;
            case 3:
                System.out.println("Masala Chai");
                break;
        }
        System.out.print("Press B to go back to the menu\nPress q to terminate the machine\n");            
        String input=in.next();
        if(input.equals("B")){
            state_at="Start";
            var.change(state_at);                           
            start();
        }
        else if(input.equals("q")){
            state_at="Terminating";
            var.change(state_at);                          
            terminating();
        }
    }
    void terminating(){
        
        System.out.println("Terminating...");        
        seq_of_states.add("Terminating");                              
        state_at="Terminated";
        var.change(state_at);
        terminated();
        
    }
    void terminated(){
        seq_of_states.add("Terminated");
        System.out.println("The sequence of states is "+seq_of_states);
        var.state="Terminated";                                            
        System.out.println("In termiated mode\nPress S to start the machine");
    }
   
    public static void main(String args[]){
        vending v=new vending("Terminated");
        String input;
        Scanner in=new Scanner(System.in);
        System.out.println("Press S to start the machine");
        System.out.println("Press q to terminate the machine");
        while(true){
            System.out.println("Press p to shut down the machine");
            input=in.next();
            if(input.equals("p"))                                   
                System.exit(0);
            else
                v.initiating(input);
        }
    }
}
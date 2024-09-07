/*Names: James, Kowan, Yibo, Howard
Main contributors: Kowan
 * Date Finished: 2020-04-24
 * Title: Creation Page
 * Description: Page with functions where mobs are created and randomized
 * */
package com.company;
import java.util.*;
import java.io.*;

public class createMob {
    //refere to member1. and member2.
    //refer to enemy1. and enemy2.
    public String name= randomName();
    //stats go [hp, attack, magic, def, magic resist, speed]
    int[] mobStats=new int[6];

    /*---------------------------------------FUNCTION WITHOUT PARAMETER-------------------------------*/
    public static String randomName(){
        double companion=Math.random();
        //random name for companion
        if(companion<=0.25){
            return "Kowan";
        }

        else if(companion<0.5){
            return "Howard";
        }

        else if(companion<0.75){
            return "YiBo";
        }

        else{
            return"James";
        }
    }

    //mob creation and randomization
    /*---------------------------------------FUNCTION WITH PARAMETER-------------------------------*/
    public static int[] statsCalc(int[] coreStats, String type){
        if(type.equals("player")){
            //makes sure that the player stats are kinda balanced
            coreStats= new int[]{15, 5, 5, 2, 2, 2};
            for(int statPoints=20;statPoints>0;statPoints--){
                int pointInto=(int) Math.round(Math.random()*5);

                coreStats[pointInto]=coreStats[pointInto]+1;
            }
            return coreStats;
        }

        else{
            int extraPoints=15;

            while(extraPoints>0){
                extraPoints--;
                double chanceOf=Math.random();

                //the following code buffs the enemies based on core stats. the higher the core stat the higher the chance that is has to be increased.
                //this way, defense mobs stay defense and atk mobs stay atk. MATH MAKES SENSE!!!
                if(chanceOf<((double)(coreStats[0])/coreStats[6])){
                    coreStats[0]+=1;
                }

                else if(chanceOf<((double)(coreStats[0]+coreStats[1])/coreStats[6])){
                    coreStats[1]+=1;
                }

                else if(chanceOf<((double)(coreStats[0]+coreStats[1]+coreStats[2])/coreStats[6])){
                    coreStats[2]+=1;
                }

                else if(chanceOf<((double)(coreStats[0]+coreStats[1]+coreStats[2]+coreStats[3])/coreStats[6])){
                    coreStats[3]+=1;
                }

                else if(chanceOf<((double)(coreStats[0]+coreStats[1]+coreStats[2]+coreStats[3]+coreStats[4])/coreStats[6])){
                    coreStats[4]+=1;
                }

                else{
                    coreStats[5]+=1;
                }
            }
            return coreStats;
        }
    }

    //character improvement
    /*----------------------------------------FUNCTION WITH PARAMETER-------------------------------------*/
    public static int[] statAdd(int upgrades,int[] stats,String member){
        int[] temp=stats;

        do{
            Scanner addTo=new Scanner(System.in);
            System.out.println("You have "+upgrades+" upgrade points remaining for "+member+". What stats would you like to improve by 1 point?");
            System.out.println("1) Hp 2) Attack 3) Magic 4) Defense 5) Magic Resist 6) Speed");
            int intoWhat=addTo.nextInt();
            intoWhat-=1;
            temp[intoWhat]=temp[intoWhat]+1;
            upgrades--;
        }while(upgrades>0);

        System.out.println("Your new stats are for "+member+" are HP: "+temp[0]+", Attack: "+temp[1]+", Magic: "+temp[2]+", Defense: "+temp[3]+", Magic Resist "+temp[4]+", Speed: "+temp[5]+" ");
        return temp;
    }

    /*---------------------------------FUNCTION WITHOUT PARAMETER-----------------------------------------*/
    public static int[] randomMob(int mob,int totalLines)throws IOException{
        /*------------------------------------------------------------------
        ---------------------USE OF File Reading------------------------------
        ---------------------------------------------------------------------*/
        BufferedReader br=new BufferedReader(new FileReader("mobStats.csv"));

        String line="";
        int[] temp=new int[7];


        //array for every single mob to get stats
        /*------------------------------------------------------------------
        ---------------------USE OF 2D ARRAY------------------------------
        ---------------------------------------------------------------------*/
        //2d array to sort enemies core stats
        String[][] everyMob=new String[totalLines][8];

        //makes array full of the mobs
        /*------------------------------------------------------------------
        ---------------------USE OF LOOPS------------------------------
        ---------------------------------------------------------------------*/
        for(int pos=0;pos<totalLines;pos+=1){
            line=br.readLine();

            if(line!=null){
                String[] insert = line.split(",");
                everyMob[pos] = insert;
            }
        }

        br.close();
        //incase mob=0, or is greater than file length this makes is so the code does not crash
        if(mob==0){
            mob=1;
        }

        if(mob>=totalLines){
            //just to be safe
            mob=3;
        }
        //create mob stats without name
        for(int index=1;index<8;index++){
            temp[index-1]=Integer.parseInt(everyMob[mob][index]);
        }

        return statsCalc(temp,"mob");
    }

    public static String mobName(int mob,int totalLines)throws IOException{
        //incase mob=0, or is greater than file length this makes is so the code does not crash
        if(mob==0){
            mob=1;
        }

        if(mob>=totalLines){
            //just to be safe
            mob=3;
        }

        //create array of every mob again, this time to return the name
        BufferedReader br=new BufferedReader(new FileReader("mobStats.csv"));
        String line="";

        String[][] everyMob=new String[totalLines][8];

        //makes array full of the mobs
        /*------------------------------------------------------------------
        ---------------------USE OF LOOPS------------------------------
        ---------------------------------------------------------------------*/
        for(int pos=0;pos<totalLines;pos+=1){
            line=br.readLine();

            if(line!=null){
                String[] insert = line.split(",");
                everyMob[pos] = insert;
            }
        }
        br.close();
        String name=everyMob[mob][0];
        return name;
    }

    /*---------------------------------------FUNCTION WITHOUT PARAMETER-------------------------------*/
    public static int mobNum()throws IOException{
        BufferedReader howMany=new BufferedReader(new FileReader("mobStats.csv"));
        String line="";
        int totalLines=0;

        do{
            line = howMany.readLine();
            totalLines+=1;
        }while(line!=null);

        totalLines-=1;
        howMany.close();

        return totalLines;
    }
}



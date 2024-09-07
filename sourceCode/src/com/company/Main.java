/*Names: James, Kowan, Yibo, Howard
Main contributors: James
 * Date Finished: 2020-04-24
 * Title: Main Run Page
 * Description: Page where all the main functions go. Looping the 100 floors and calling and creating mobs
 * */

package com.company;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //declare all mob variables.
        int floorNum=1;
        boolean dead=false;
        int difficulty=1;
        createMob member1=new createMob();
        createMob member2=new createMob();
        member1.mobStats=member1.statsCalc(null,"player");
        member2.mobStats=member2.statsCalc(null, "player");

        //create enemies object
        createMob enemy1=new createMob();
        createMob enemy2=new createMob();
        //reading and writing to the player scores list
        BufferedWriter scores=new BufferedWriter(new FileWriter("previousHeros.txt",true));
        BufferedReader pastPeeps=new BufferedReader(new FileReader("previousHeros.txt"));
        //variables used at the end
        String person, coolness;

        //declare inputs
        Scanner playerInput=new Scanner(System.in);

        //beginning of the tutorial
        System.out.println("Welcome to the Tower of Krnic! Your goal is to reach the top of a 100 floor tower! At the top, you will win a grand prize! I think...");
        System.out.println("What is your name adventurer?");
        /*----------------------------------------*/
        member1.name = playerInput.nextLine();
        System.out.println("Hello "+member1.name+"!");

        System.out.println("Now adventurer, you will enter the first floor in the Tower of Krnic!\nAlso! One important thing to know is that healing can go above your max HP. Intended. Totally... So use it and abuse it!");

        /*--------------------USE OF LOOPS--------------------*/
        while(floorNum<=100 && !dead){
            boolean first5=true;
            /*---------------------------------use this line with enemy1 or enemy2 to generate a new random mob----------------------------
             * ---------------------------------enemy1.name gives its name// enemy1.mobStats gives an array of the mob stats-------------------*/
            int enemyNum=createMob.mobNum();
            enemy1.mobStats=enemy1.randomMob((int)Math.round(createMob.mobNum()*Math.random()), createMob.mobNum());
            enemy1.name=enemy1.mobName((int)Math.round(createMob.mobNum()*Math.random()), createMob.mobNum());

            enemyNum=createMob.mobNum();
            enemy2.mobStats=enemy2.randomMob((int)Math.round(createMob.mobNum()*Math.random()), createMob.mobNum());
            enemy2.name=enemy2.mobName((int)Math.round(createMob.mobNum()*Math.random()),createMob.mobNum());
            System.out.println("You are now entering floor "+floorNum);

            /*------------------------------------------------------------------
        ---------------------USE OF 2D ARRAY------------------------------
        ---------------------------------------------------------------------*/
            //2d array organizes enemies as one and your party as the other
            String[][] participants={
                    {member1.name,member2.name},
                    {enemy1.name,enemy2.name}
            };
            dead=combat.floor(member1.mobStats, member2.mobStats,enemy1.mobStats,enemy2.mobStats, participants, member2.name);

            //give upgrade points to player to spend
            if(floorNum%5==0){
                /*--------------------LOTS OF SELECTION--------------------*/
                if(first5){
                    //check btoon givebonus on the first 5 floors
                    System.out.println("Congrats on not dying this early on!\nEvery 5 floors on this tower is like a checkpoint...only you don't respawn. \n When you die in this tower, you die forever... so don't die.\n But anyways, every 5 floors you gain upgrade points which allow you to increase your stats. \n Since this is your first 5 floors, I'll give you 10 points to upgrade yourself with. Good luck and don't die!");
                    member1.statAdd(10,member1.mobStats,member1.name);
                    member2.statAdd(10,member2.mobStats,member2.name);
                    first5=false;
                }
                else{
                    System.out.println("Welcome back!\r How do you plan to not die?");
                    member1.statAdd(5,member1.mobStats,member1.name);
                    member2.statAdd(5,member2.mobStats,member2.name);
                    System.out.println("Ok then!\n Off you go to not die!");
                }
            }
            floorNum++;
        }

        if(dead){
            System.out.println("You have died.\nThat sucks. Maybe next life you won't die.\n On the bright side though, I'll write you down in the history of this dungeon in this easily readable file called 'previousHeros.txt'.\n I'll read the past people to you now:");
            double coolnessRate=Math.random();
            //easter egg for random coolness
            if(coolnessRate<0.25){
                coolness="Smells funny. OK";
            }

            else if(coolnessRate<0.5){
                coolness="Subarashi desu ka?";
            }

            else if(coolnessRate<0.75){
                coolness="I've never met this person";
            }

            else{
                coolness="Coolest";
            }

            //print out everyone
            /*------------------------------------------------------------------
        ---------------------USE OF File Reading------------------------------
        ---------------------------------------------------------------------*/
            person=pastPeeps.readLine();
            while(person!=null){
                System.out.println(person);
                person=pastPeeps.readLine();
            }
            /*------------------------------------------------------------------
        ---------------------USE OF Writing to File------------------------------
        ---------------------------------------------------------------------*/
            System.out.println("And lastly... your party:\n"+member1.name+", "+member2.name+", "+Integer.toString(floorNum)+", "+coolness);
            scores.write(member1.name+","+member2.name+","+Integer.toString(floorNum-1)+","+coolness+"\r\n");

        }
        /*------------------------------------------------------------------
        ---------------------USE OF File Writing------------------------------
        ---------------------------------------------------------------------*/
        else{
            coolness="Super Awesome Gamer";
            System.out.println("You really made it to the 100th floor? Huh...\nWell I didn't really think you'd get this far so...\nI'll add your name to the dead players list with the status of '-SURVIVED-'.\nGo home now please.");
            System.out.println("Your party was added to a super cool document:\n"+member1.name+", "+member2.name+", "+Integer.toString(floorNum)+", "+coolness+", -SURVIVED-");
            scores.write(member1.name+","+member2.name+","+Integer.toString(floorNum-1)+","+coolness+"\r\n");
        }
        scores.close();
    }
}

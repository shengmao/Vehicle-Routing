/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Solution;
import Entity.entitiesinmemory.*;
import java.util.ArrayList;
import Entity.entitiesinmemory.Stage2;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Stack;
/**
 *
 * @author lis
 */
public class MySolution2 {
    public Aroute2[] RouteList;
    public BenchmarkInstance2 bmInstance;
    public int nRoute;
    String planning_results;
    ArrayList superlist;
     
    public MySolution2(String method, BenchmarkInstance2 bmInstance) {
        System.out.println("the solution class has been executed");
        //Chec input first
        if (method.equals("Greedy")) {
            this.bmInstance = bmInstance;

            //Greedy heuristic for initialize solution
            //Initial Unvisited Location2 Array, include location 0 so that with 100 customer input->list lenght is 101
            int[] unVisitedLoc = initUnvisitedLocation(bmInstance.loclist.length);
            int n_unVisitedLoc = bmInstance.loclist.length - 1;

            //Construct Distance Matrix
            double[][] distMatr = bmInstance.distancematrix;



            int n_route = 0;
            ArrayList listRoute = new ArrayList();

            outerloop:
            while (n_unVisitedLoc != 0) {
                System.out.printf("the MYSOLUTION constructor method has been exectued %d %n", n_unVisitedLoc);
                Location2 startLoc = bmInstance.loclist[0];
                int startLocIndex = 0;
                double currentDemand = 0;
                double totalTravelTime = 0;
                double totalDistance = 0;
                int lastpausecheck = 0;
                
                int scanIndex = 1;//Run from 1
                
                ArrayList<Integer> visited_bestindex = new ArrayList<Integer>();              
                ArrayList arrListLoc = new ArrayList();
                ArrayList arrListDel = new ArrayList();
                ArrayList listStages = new ArrayList();
  
                //First is always location 0 and delivery 0
                arrListLoc.add(bmInstance.loclist[0]);
                arrListDel.add(bmInstance.dellist[0]);

                int finalCustomer = -1;

                while (currentDemand < bmInstance.vehiclecapacity && scanIndex < unVisitedLoc.length) {
       
                    System.out.printf("the while loop has been executed %d %n", n_unVisitedLoc);
                    ArrayList bestChoice = findGreedy2(startLoc, startLocIndex, distMatr,
                            totalTravelTime, currentDemand, unVisitedLoc, bmInstance, totalDistance, visited_bestindex);                                                     
                    
                    if (!bestChoice.isEmpty()) {

                        System.out.printf("best choise is not empty and the best index is %d", (Integer)bestChoice.get(1));
                        Location2 departPoint = startLoc;
                        Location2 bestLoc = (Location2) bestChoice.get(0);
                        Location2 destinationPoint = bestLoc;

                        Integer tmp = (Integer) bestChoice.get(1);
                        int bestLocIndex = tmp.intValue();
                        
                        //arraylist varibale "visited_bestindex" is used to test if the best index meets the other following contrstraints
                        //if so, when executing findGreey2, this index should not be execute, otherwise a dead circle occurs in while loop.
                         visited_bestindex.add(bestLocIndex);
                        
                        //Fahrzeitrestriktion                        
                        double t = totalTravelTime;
                        double vfzij =calculateTravelTime(distMatr[startLocIndex][bestLocIndex],  100);
                        double ffzg = 0;                                
                        double fzg = 0;
                        double pause = 0;
                        //caculate ffzg
                        for (Object stage : listStages){
                            Stage2 aStage = (Stage2)stage;
                            ffzg = ffzg +aStage.Atraveltime;
                        }
                        
                        //caculate fzg
                        for(int i = listStages.size()-1; i>=0; i--){
                            Stage2 aStage = (Stage2)listStages.get(i);
                            fzg = fzg + aStage.Atraveltime;                            
                            if (aStage.pause != 0){
                                break;
                            }                                
                        }
                        double endtrzg = 0;
                        double endwrzg = 0;
                        double wfzg = ffzg;
                        double zwfzg = ffzg;
                        boolean meetFzr = true;
                        boolean secmeetFzr = true;
                        
                        Fahrzeitrestrikition2 aFzr = new Fahrzeitrestrikition2(t, fzg, ffzg, endtrzg, endwrzg, wfzg, zwfzg, vfzij);                                               
                        
                        //System.out.println();
                        //System.out.printf("time constraints value are: %f %f %f %f %n", t, fzg, ffzg, vfzij );
                        for(int i = 0; i <aFzr.getVariablearr().length; i++){
                            System.out.println("111111the member is:" + aFzr.getVariablearr()[i]);
                        } 
                        System.out.println("1111111the smallest time constraint is: " + aFzr.getsmallest(aFzr.getVariablearr()));
                        
                        //compare the smallest restriction value
                         if(aFzr.getsmallest(aFzr.getVariablearr()) == 0){
                            pause = 0;
                        }else if(aFzr.getsmallest(aFzr.getVariablearr()) ==1){
                            pause = 45;
                        }else{
                             meetFzr = false; 
                        }      
                         
                        //check if truck can return to depot with time restriction
                        double hypo_endtime = calculateTotalTravelTime(totalTravelTime +pause,
                                distMatr[startLocIndex][bestLocIndex], 100, bmInstance.dellist[bestLocIndex])
                                + bmInstance.dellist[bestLocIndex].servicetime;
                        double hypo_fzg =0;
                        if(pause == 0){
                             hypo_fzg = fzg+vfzij;
                        }else if(pause == 45){                       
                             hypo_fzg = vfzij;
                        }
                        
                        Fahrzeitrestrikition2 secFzr = new Fahrzeitrestrikition2(hypo_endtime, hypo_fzg, ffzg+vfzij, endtrzg, endwrzg, ffzg+vfzij, ffzg+vfzij, calculateTravelTime(distMatr[bestLocIndex][0],  100));
                        
                        //System.out.printf("%n pause is: %f", pause);          
                        //System.out.println();
                        //System.out.printf("time constraints value are: %f %f %f %f %n", hypo_endtime, hypo_fzg, ffzg+vfzij, calculateTravelTime(distMatr[bestLocIndex][0],  100) );
                        for(int i = 0; i <secFzr.getVariablearr().length; i++){
                            //System.out.println("222222the member is:" + secFzr.getVariablearr()[i]);
                        } 
                        //System.out.println("222222the smallest time constraint is: " + secFzr.getsmallest(secFzr.getVariablearr()));
                        
                        //compare the smallest restriction value
                         if(secFzr.getsmallest(secFzr.getVariablearr()) == 0){
                             lastpausecheck = 0;
                        }else if(secFzr.getsmallest(secFzr.getVariablearr()) ==1){
                            secmeetFzr = true;
                            lastpausecheck = 45;
                        }else{
                             secmeetFzr = false;   
//                             //System.out.println();
//                             //System.out.println("the smallest time variant is:" + secFzr.getsmallest(secFzr.getVariablearr()));
                             
                        }
                        //end of checking  
                         
                        //end Fahrzeitrestriktion
                        
                        //check constraints again
                        double seccheckTravelTime = calculateTotalTravelTime( totalTravelTime + pause,  distMatr[startLocIndex][bestLocIndex],  100,  bmInstance.dellist[bestLocIndex]); 
                        double secreturnTime = seccheckTravelTime + bmInstance.dellist[bestLocIndex].servicetime + calculateTravelTime(distMatr[bestLocIndex][0], 100) + lastpausecheck;
                        if (isSatisfyTimeWindow(seccheckTravelTime, bmInstance.dellist[bestLocIndex])// satisfy time window of destination location
                        && isSatisfyTimeWindow(secreturnTime, bmInstance.dellist[0]) && meetFzr && secmeetFzr) // satisfy time window of depot                        
                       
                        {                                                     
                        double startingTime = totalTravelTime;//at that time totlaTravelTime is the time form the previous location                        
                        double secstartingTime = startingTime + pause;
                        double travelTime = calculateTravelTime(distMatr[startLocIndex][bestLocIndex], 100);                       
                        double arrivingTime = secstartingTime + travelTime;
                        double issuingTime;
                        if (arrivingTime <= bmInstance.dellist[bestLocIndex].timewindowfrom) {
                            issuingTime = bmInstance.dellist[bestLocIndex].timewindowfrom;
                        } else {
                            issuingTime = arrivingTime;
                        }


                        totalDistance = (Double) bestChoice.get(2);

                        //total Travel time + Service time
                        totalTravelTime = calculateTotalTravelTime(totalTravelTime +pause,
                                distMatr[startLocIndex][bestLocIndex], 100, bmInstance.dellist[bestLocIndex])
                                + bmInstance.dellist[bestLocIndex].servicetime;

                        double endTime = totalTravelTime;//at that time totalTravelTime including all
                        double distance = distMatr[startLocIndex][bestLocIndex];


                        //Set current considered customer is the best Location2
                        startLoc = bestLoc;
                        startLocIndex = bestLocIndex;

                        //Save final customer index in route
                        finalCustomer = bestLocIndex;

                        //Set this customer is visited
                        unVisitedLoc[bestLocIndex] = 1;

                        //Update demand
                        currentDemand = currentDemand + bmInstance.dellist[bestLocIndex].demand;

                        //Update number of unvisited customer
                        n_unVisitedLoc--;

                        //Update list location and delivery
                        arrListLoc.add(bestLoc);
                        arrListDel.add(bmInstance.dellist[bestLocIndex]);
                        Stage2 st = new Stage2(departPoint, destinationPoint, startingTime, arrivingTime, issuingTime, endTime, distance, travelTime, pause);
                        listStages.add(st);                                             
                        }
                        else{
                            System.out.printf("the time constraints are not satisfied, meetFzr: %b,  secmeetFzr: %b %n", meetFzr, secmeetFzr);
                            if(startLocIndex ==0){
                                System.out.println();
                                System.out.printf("Delivery %d can not be planned, it doesn't meet time constraints requirements.", (Integer)bestChoice.get(1));
                                System.out.println();
                            }
                        }
                    } else if (startLocIndex == 0 && bestChoice.isEmpty()){
                         break outerloop;
                    } else{//End, without finding a good choice from current start location, should start a new route or terminate program
                        scanIndex = unVisitedLoc.length;                        
                    }
                        
                }//End while -> construct one route

                if (finalCustomer > 0) {
                    //Finaly, update traveltime and distance from final customer to depot
                    //Final stage
                    Stage2 semiFinalStage = (Stage2) listStages.get(listStages.size() - 1);
                    double startingTime = semiFinalStage.endTime;//at that time totlaTravelTime is the time form the previous location                                        
                    double travelTime = calculateTravelTime(distMatr[finalCustomer][0], 100);                     
                    double arrivingTime = startingTime + lastpausecheck + travelTime;

                    Stage2 st = new Stage2(bmInstance.loclist[finalCustomer], bmInstance.loclist[0], startingTime, arrivingTime, 0, 0, distMatr[finalCustomer][0], travelTime, lastpausecheck);
                    listStages.add(st);
                    totalTravelTime = totalTravelTime + calculateTravelTime(distMatr[finalCustomer][0], 100) + bmInstance.dellist[finalCustomer].servicetime;
                    totalDistance = totalDistance + distMatr[finalCustomer][0];
                    //append location 0
                    arrListLoc.add(bmInstance.loclist[0]);
                    arrListDel.add(bmInstance.dellist[0]);
                    Aroute2 r = new Aroute2(arrListLoc, arrListDel, currentDemand, totalDistance, totalTravelTime, listStages);
                    listRoute.add(r);
                    n_route++;
                }
            }

            if (n_unVisitedLoc != 0) {
                System.out.println("there are " + n_unVisitedLoc + " delivery not scheduled, please check your generated plan.");
            }
            
            //builder string for ouput in view
            StringBuilder builder  = new StringBuilder();
            //superlist for timelist output 
            superlist = new ArrayList();
            
            RouteList = new Aroute2[listRoute.size()];
            for (int k = 0; k < listRoute.size(); k++) {
                RouteList[k] = (Aroute2) listRoute.get(k);
                ArrayList midlist = new ArrayList();
                               
                System.out.println();
                String temp_builder_str0 = String.format("%n");  
                //builder.append(temp_builder_str0);

                System.out.print("route" + k + ":");
                String temp_builder_str1 = String.format("route" + k + ": "); 
                builder.append(temp_builder_str1);

                //System.out.println( "the final result is" + RouteList[k].listOfDelivery);

                for(int i=0; i<RouteList[k].listOfDelivery.size(); i++){
                     if(i == (RouteList[k].listOfDelivery.size()-1)){
                         Delivery2 aDelivery = (Delivery2)RouteList[k].listOfDelivery.get(i);
                         System.out.print(aDelivery.deliveryindex);
                         String temp_builder_str7 = String.format("%d",aDelivery.deliveryindex); 
                          builder.append(temp_builder_str7);
                     }else{
                          Delivery2 aDelivery = (Delivery2)RouteList[k].listOfDelivery.get(i);
                          System.out.print(aDelivery.deliveryindex + "->");
                          String temp_builder_str8 = String.format(aDelivery.deliveryindex + "->"); 
                           builder.append(temp_builder_str8);
                     }                     
                }
                System.out.println();
                String temp_builder_str2 = String.format("%n");                 
                builder.append(temp_builder_str2);
                double tmp_totaltraveltime =0;
      
                //change begin on   19.06.2013
                //to put the time variables in a list for later output in the view
                                                                    
                //change end on 19.06.2013    
                for(Object stage : RouteList[k].listOfStage){
                    
                    Stage2 aStage =(Stage2)stage;
                    //round double to 2 digits after decimal point
                    double startingTime = aStage.startingTime/60d;
                    startingTime = Math.round( startingTime * 100.0 ) / 100.0;                    
                    
                    double pause=aStage.pause/60d;
                    pause = Math.round( pause * 100.0 ) / 100.0;
                    
                    double arrivingTime=aStage.arrivingTime/60d;
                    arrivingTime=Math.round( arrivingTime * 100.0 ) / 100.0;
                    
                    double issuingTime=aStage.issuingTime/60d;
                    issuingTime = Math.round( issuingTime * 100.0 ) / 100.0;
                    
                    double endTime=aStage.endTime/60d;
                    endTime = Math.round( endTime * 100.0 ) / 100.0;
                                        
                    double travelTime = (aStage.arrivingTime -aStage.pause -aStage.startingTime)/60d;
                    travelTime = Math.round( travelTime * 100.0 ) / 100.0;
                    
                    
                    Double[] concretlist = new Double[6];                                                    
                    concretlist[0] = startingTime;
                    concretlist[1] = pause;
                    concretlist[2] = travelTime;
                    concretlist[3] = arrivingTime;
                    concretlist[4] = issuingTime;
                    concretlist[5] = endTime;
                    
                                
                    System.out.println("testing concretlist--------------------------->");
                    
                    for(int i=0; i<concretlist.length; i++)
                    {
                       System.out.println(concretlist[i]);                        
                    }                                         
                    midlist.add(concretlist);
                                                                                  
                    System.out.println("Startingtime:"+startingTime + " " +"Pause:"+pause + " "+"Traveltime"+travelTime+" "+ "arrivingtime"+arrivingTime +" " +"issuingtime"+ issuingTime +" " +"endtime"+endTime +" ");
                    String temp_builder_str3 = ("Startingtime:"+startingTime + " " +"Pause:"+pause + " "+"Traveltime"+travelTime+" "+ "arrivingtime"+arrivingTime +" " +"issuingtime"+ issuingTime +" " +"endtime"+endTime +" ");                                        
                    String temp_builder_str9 = String.format("%n");  
                    //builder.append(temp_builder_str3);
                    //builder.append(temp_builder_str9);                    
                    tmp_totaltraveltime +=(aStage.arrivingTime-aStage.pause-aStage.startingTime);                    
                }
             
                //round double to 2 digits after decimal point
                tmp_totaltraveltime = tmp_totaltraveltime /60d;
                tmp_totaltraveltime = Math.round(tmp_totaltraveltime * 100.0) / 100.0;
                System.out.println("Total travel time: "+tmp_totaltraveltime);
                System.out.println("Total delivery demand of route is: " + RouteList[k].totalDemand);
                System.out.println();
                String temp_builder_str4 = String.format("Total travel time: "+tmp_totaltraveltime +"%n");
                String temp_builder_str5 = String.format("Total delivery demand of route is: " + RouteList[k].totalDemand +"!");
                //String temp_builder_str6 = String.format("%n"); 
                builder.append(temp_builder_str4);
                builder.append(temp_builder_str5);
                //builder.append(temp_builder_str6);

                //System.out.print(RouteList[k].listOfLocation);
                //System.out.print(RouteList[k].totalDemand);
                
                superlist.add(midlist);

            }
            this.nRoute = listRoute.size();
            //pass the planning_results to virable
            System.out.println(builder.toString());
            planning_results = builder.toString();
            System.out.println("testing superlist--------------------------->");
            System.out.println(superlist);

        }// end construc
}
        
        
        
    public double calculateTravelTime(double distance, double speed) {
        double travelTime = distance / speed;
        travelTime = travelTime *60;
        return travelTime;
    }
    
    public double calculateTotalTravelTime(double currentTravelTime, double distance, double speed, Delivery2 del) {
//        System.out.println("---------------------------------------------->");
//      System.out.printf("currentTraveltime: %f, distance: %f, speed: %f, Deliverytimewindow: %f", currentTravelTime, distance, speed, del.timewindowfrom);        
//        System.out.printf("Deliverytimewindow: %f",del.timewindowfrom);       
//        System.out.printf("currentTraveltime: %f",currentTravelTime);
//        System.out.printf("distance: %f",distance);
//        System.out.printf("speed: %f",speed);
        

        if (currentTravelTime + calculateTravelTime(distance, speed) <= del.timewindowfrom) {
            return del.timewindowfrom; //Vehicle early has to wait until the time of opening
        }
        return currentTravelTime + calculateTravelTime(distance, speed);
    }
    
    public int[] initUnvisitedLocation(int Lenght) {
        int[] arr = new int[Lenght];
        for (int k = 0; k < Lenght; k++) {
            arr[k] = 0;
        }
        return arr;
    }
    
    public boolean checkifexist(int i, ArrayList alist){
        for(Object object: alist){
                Integer Aobject = (Integer)object;
                int temp = Aobject.intValue();
                if(i == temp){
                    return false;
                }
            }
        return true;
    }
    
    public ArrayList findGreedy2(Location2 start, int startLocIndex, double[][] distMatr,
            double totalTravelTime, double currentDemand,
            int[] unVisitedLoc, BenchmarkInstance2 bmInstance, double totalDistance, ArrayList visited_bestindex) {
        ArrayList bestChoice = new ArrayList();
        Location2 best = new Location2();
        double maxsav = -1;
        int k_bestIndex = -1;
        double currentDistance = -1;
        //Becareful, bcs the first locatin should not be location 0
        //As design the unVisitedLoc.length include location 0
        //So that the scanning should run from 1
        System.out.printf("the greedy method --------------------------->1 %n");
        int x = 1;
        for (int i = 1; i < unVisitedLoc.length; i++) {
            
            
            if (unVisitedLoc[i] == 0 && checkifexist(i, visited_bestindex) )//find unvisted Location2 so that
            {               
                 
                System.out.printf("the greedy1part method - --------------------------->---->%d %n",  x ++);
                System.out.printf("the greedy1part method - --------------------------->---->%d---->from %d to %d %n", x-1,startLocIndex,i);
                //if Demand & Intime & return intime , so we check with demand                
                //return time from current point to depart point; point 0
                //include service time as well
                double checkTravelTime = calculateTotalTravelTime( totalTravelTime,  distMatr[startLocIndex][i],  100,  bmInstance.dellist[i]); 
                double returnTime = checkTravelTime + bmInstance.dellist[i].servicetime + calculateTravelTime(distMatr[i][0], 100);
                double checkDemand = currentDemand + bmInstance.dellist[i].demand;
                if (checkDemand <= bmInstance.vehiclecapacity
                        && isSatisfyTimeWindow(checkTravelTime, bmInstance.dellist[i])// satisfy time window of destination location
                        && isSatisfyTimeWindow(returnTime, bmInstance.dellist[0])) // satisfy time window of depot
                {       
                    // find biggest saving value                                         
                    if(startLocIndex == 0)
                    {
                        //if the route is an empty route, then find the location pair with biggest saving value first.
                        double bigsav = initializeFirstlocpair(i, startLocIndex, distMatr, totalTravelTime, currentDemand, unVisitedLoc, bmInstance, visited_bestindex);  
                        

                        if(bigsav >= maxsav){
                                maxsav = bigsav;
                                k_bestIndex =i;                       
                                currentDistance = distMatr[startLocIndex][k_bestIndex];
                                best = bmInstance.loclist[k_bestIndex];
                                System.out.printf("the greedy2part method has been exectued %d, the maxvalue is: %f %n", i, bigsav);                                
                            } 
                    }else
                    {                       
                        double dist_startTodepot = distMatr[startLocIndex][0];
                        double dist_iTodepot = distMatr[i][0];
                        double dist_depotToi = distMatr[0][i];
                        double dist_startToi = distMatr[startLocIndex][i];
                        
                        double sav_value = (dist_startTodepot + dist_depotToi) - dist_startToi;
                        System.out.printf("the greedy3part method has been exectued from %d to %d, the maxvalue is: %f %n", startLocIndex, i, sav_value);
                            
                        if (sav_value >= maxsav)//If max distance, best will be the max
                        {
                            maxsav = sav_value;
                            currentDistance = distMatr[startLocIndex][i];
                            best = bmInstance.loclist[i];
                            k_bestIndex = i;
                            
                        }
                    }
                }
            }
        }//End loop with the best and k_bestIndex is updated
        if (k_bestIndex != -1) {
            bestChoice.add(best);
            bestChoice.add(k_bestIndex);
            totalDistance = totalDistance + currentDistance;
            bestChoice.add(totalDistance);
        }
        return bestChoice;
    }
    
        public boolean isSatisfyTimeWindow(double travelTime, Delivery2 del) {
             if (travelTime <= del.timewindowto ) {
                 return true;
             }
             return false;
       }
        
       public double initializeFirstlocpair(int i, int startLocIndex, double[][] distMatr,
            double totalTravelTime, double currentDemand,
            int[] unVisitedLoc, BenchmarkInstance2 bmInstance, ArrayList visited_bestindex)
       {
            double sav_value = -1;
            for (int j = 1; j < unVisitedLoc.length; j++)
            {
               if (j != i && unVisitedLoc[j] ==0 && checkifexist(j, visited_bestindex))
               {

                  double checkTravelTimeij = calculateTotalTravelTime(totalTravelTime, distMatr[startLocIndex][i],100, bmInstance.dellist[i])
                           + bmInstance.dellist[i].servicetime + calculateTravelTime(distMatr[i][j], 100);                                          
                  double returnTimeidepot = calculateTotalTravelTime((checkTravelTimeij - calculateTravelTime(distMatr[i][j], 100)), distMatr[i][j],100,bmInstance.dellist[j])
                      + bmInstance.dellist[j].servicetime + calculateTravelTime(distMatr[j][0], 100);
                  double checkDemandij = currentDemand + bmInstance.dellist[j].demand + bmInstance.dellist[i].demand;

       
                  if (checkDemandij <= bmInstance.vehiclecapacity
                          && isSatisfyTimeWindow(checkTravelTimeij, bmInstance.dellist[j])
                          && isSatisfyTimeWindow(returnTimeidepot, bmInstance.dellist[0]))
                  { 
                    double dist_jTodepot = distMatr[j][0];
                    double dist_depotToj = distMatr[0][j];
                    double dist_iTodepot = distMatr[i][0];
                    double dist_depotToi = distMatr[0][i];
                    double dist_iToj = distMatr[i][j];
                                        
                    //changed madde on 25 Jan 2013
                    double tmp_sav_value = (dist_depotToj + dist_iTodepot) - dist_iToj;  
                    if(tmp_sav_value >= sav_value){
                        sav_value = tmp_sav_value;
                        System.out.println("i:"+i+"  "+ "j:"+j+"  "+"saving value: "+sav_value);
                    }                    
                  }  
                }
              }           
            return sav_value;   
       }

       public String Pass_Planning_results(){
           return planning_results;
       }
       
       public ArrayList Pass_timelist_results(){
           return superlist;
       }
       public Aroute2[] Pass_Routelist(){
           return RouteList;
       }
}   

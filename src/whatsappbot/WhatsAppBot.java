/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 *
 * @author Krishna Tyagi
 */
public class WhatsAppBot {

    //chrome webdriver
    WebDriver driver = new ChromeDriver();

     static BufferedWriter fw;


     WhatsAppBot(){
   //set the path for the chorme web driver

    driver.get("https://web.whatsapp.com");
     driver.manage().window().maximize();
     }

     public void createFile(String datetime,String driveName){
     try{
         fw = new BufferedWriter(new FileWriter(driveName+":\\testout_"+datetime+".txt"));
     }catch(IOException e){
     System.out.println(e);
     }
     }

     public void individualGroup(String groupName){
    


 System.out.println("=======start============");
   
    try{
         List<WebElement> allMessegeContainer = driver.findElements(By.xpath(".//div[@class='X7YrQ']"));
         WebElement groupElement  = null;
         for(int i=0;i<allMessegeContainer.size();i++){
             WebElement element= allMessegeContainer.get(i).findElement( By.className("_3H4MS"));
             if(groupName.equalsIgnoreCase(element.getText())){
             
             System.out.println("group is found");
             groupElement = allMessegeContainer.get(i);
             break;
             }
         }
     
    
     if(groupElement!=null){
     String numberOfNewMesseges = "0";
     List<WebElement> findClass =  groupElement.findElements(By.className("_0LqQ"));
 WebElement elem = findClass.get(findClass.size()-1);
//get the list of all three span tags presented inside the grenn icon div tag
          List<WebElement> spanelem = elem.findElements(By.tagName("span"));
//get the text of first span because it has the value of green icon which indicated towards 
           numberOfNewMesseges = spanelem.get(0).getText();
 driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
 
 if(numberOfNewMesseges.equals("")){
 numberOfNewMesseges = "0";
 }
 
       groupElement.click();
       
         boolean checkGroup = checkItsAGroup(); 
               //for being a group it should be true
              if(checkGroup) {
              
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
              //get all the messeges container  
              List<WebElement> messeges = driver.findElements(By.className("FTBzM"));
              int oldSize =  messeges.size();
                 
              //get the text from messeges  
             extractTheTextFromMesseges(numberOfNewMesseges, messeges);
         System.out.println("\t\t\tMonitoring Start..............   ");


              // messeges = driver.findElements(By.className("FTBzM"));
              int newSize = messeges.size();
              int count =0, sameValue = newSize;
             while(true && count<10){
             messeges = driver.findElements(By.className("FTBzM"));
             newSize = messeges.size();
             if(newSize==sameValue)
                 count++;
             else{
                 count=0;
                 sameValue=newSize;
             }
             Thread.sleep(5000);
             }
              messeges = driver.findElements(By.className("FTBzM"));
             newSize = messeges.size();
          //   System.out.println(oldSize+":"+newSize);
             if(newSize>oldSize)
             extractTheTextFromMesseges(Integer.toString(newSize-oldSize+1), messeges);

              
              }
              else{
              
              System.out.println("Sorry but it's not a group");
              }
       
     }else{
     System.out.println("group not found");
     }
     
    }catch(NoSuchElementException e){
    System.out.println("there is no such an element");
    }   catch (InterruptedException ex) {
            Logger.getLogger(WhatsAppBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    


     }


     /* this function is used to perform all the task inside the driver. First, it will open the driver only and wait for the manual opening*/
     public void OpenApp(){
         int time=10;
   


 System.out.println("=======start============");
     //if user type start then process will move forward

     try{
        while(time>0){

      //getting the list of all participants in left panel
       List<WebElement> greenIcon = driver.findElements(By.xpath(".//div[@class='X7YrQ']"));


         //this will check the visibility of green icon ehich indicates a new messege
       for(int i=0;i<greenIcon.size();i++){

      // driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
//getting the green icon containing div element
         //  WebElement elem = greenIcon.get(i).findElement(By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div/div["+(i+1)+"]/div/div/div[2]/div[2]/div[2]"));
  WebDriverWait wait=new WebDriverWait(driver, 20);
             wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("_0LqQ")));
             
         List<WebElement> findClass =  greenIcon.get(i).findElements(By.className("_0LqQ"));
 WebElement elem = findClass.get(findClass.size()-1);
//get the list of all three span tags presented inside the grenn icon div tag
          List<WebElement> spanelem = elem.findElements(By.tagName("span"));
//get the text of first span because it has the value of green icon which indicated towards 
          String numberOfNewMesseges = spanelem.get(0).getText();
 driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
 //check for the value. tis condition will not work if there is no green icon which means no new messege        
          if(!numberOfNewMesseges.equals("")){
           //click on the left panel's elements fro opening the message tab
              greenIcon.get(i).click();
               //check group or not
              boolean checkGroup = checkItsAGroup(); 
               //for being a group it should be true
              if(checkGroup) {

              driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
              //get all the messeges container  
              List<WebElement> messeges = driver.findElements(By.className("FTBzM"));
              int oldSize =  messeges.size();
               
              //get the text from messeges  
             extractTheTextFromMesseges(numberOfNewMesseges, messeges);
             System.out.println("\t\t\tMonitoring Start..............   ");


           /**-------- monitoring function------*/
              int newSize = messeges.size();
              int count =0, sameValue = newSize;
             while(true && count<5){
             messeges = driver.findElements(By.className("FTBzM"));
             newSize = messeges.size();
             if(newSize==sameValue)
                 count++;
             else{
                 count=0;
                 sameValue=newSize;
             }
             Thread.sleep(5000);
             }
              messeges = driver.findElements(By.className("FTBzM"));
             newSize = messeges.size();
           //  System.out.println(oldSize+":"+newSize);
             if(newSize>oldSize)
             extractTheTextFromMesseges(Integer.toString(newSize-oldSize+1), messeges);

          }
          }


       }

        time--;
      Thread.sleep(2000);
        }
     }catch(NoSuchElementException e){
         System.out.println(e);
         try {
                   fw.close();
               } catch (IOException ex) {
                   Logger.getLogger(WhatsAppBot.class.getName()).log(Level.SEVERE, null, ex);
               }
     }  catch (InterruptedException ex) {  
            Logger.getLogger(WhatsAppBot.class.getName()).log(Level.SEVERE, null, ex);
             try {
                 fw.close();
             } catch (IOException ex1) {
                 Logger.getLogger(WhatsAppBot.class.getName()).log(Level.SEVERE, null, ex1);
             }
        }  

     }
    //function for group validation 
     public boolean checkItsAGroup(){
     WebElement main = driver.findElement(By.xpath("//*[@id=\"main\"]/header/div[2]/div[2]/span"));
              String members = main.getAttribute("title");

     return members.contains("group");
     }

     public void extractTheTextFromMesseges(String numberOfNewMesseges,List<WebElement> messeges ){
           try{
           //get all the messeges
            WebDriverWait wait=new WebDriverWait(driver, 20);
             wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("_0LqQ")));
             
           //index of first new messege
              int indexOfFirstNewMessege = messeges.size()-Integer.parseInt(numberOfNewMesseges);
              //now loop through to all new messeges 
              for(int j=indexOfFirstNewMessege;j<messeges.size();j++){


                 if(checkForOnlyText(messeges.get(j))){
                     System.out.println("\t\tMessege has captured");
                       //get the messege
                  WebElement elementHavingMessege = messeges.get(j).findElement(By.className("selectable-text"));

                  WebElement messegeInfo = messeges.get(j).findElement(By.className("copyable-text"));
                      //  System.out.println(elementHavingMessege.getText()+"Sender info:"+messegeInfo.getAttribute("data-pre-plain-text"));
                   StringBuilder  sb  = new StringBuilder(elementHavingMessege.getText());
                    sb.append('\t');
                   sb.append(" [send By");
                   sb.append(messegeInfo.getAttribute("data-pre-plain-text"));
                   sb.append("]");
                        fw.write(sb.toString());
                        fw.newLine();


                 }
                  }
                  System.out.println("=========");


           }catch(NoSuchElementException e){
           System.out.println(e);
           try {
                   fw.close();
               } catch (IOException ex) {
                   Logger.getLogger(WhatsAppBot.class.getName()).log(Level.SEVERE, null, ex);
               }
           }catch(Exception e){
                System.out.println(e);
               try {
                   fw.close();
               } catch (IOException ex) {
                   Logger.getLogger(WhatsAppBot.class.getName()).log(Level.SEVERE, null, ex);
               }
           } 
           }
     //check its a text element or not
     public boolean checkForOnlyText(WebElement element){
          String active = "_2ugFP";
         WebElement divElement = element.findElement(By.tagName("div"));
          return divElement.getAttribute("class").contains(active);
     }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String currentDirectory = System.getProperty("user.dir");
         System.setProperty("webdriver.chrome.driver", currentDirectory+"\\chromedriver_win32\\chromedriver.exe");
          WhatsAppBot openBrowser = new WhatsAppBot();
      
           System.out.println("\t\t=======================================Welcome===================================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================== WhatsApp Chat Bot============================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================================================================================");
        System.out.println("\t\t=================================================================================================");

      System.out.println("\t\tPlease Scan the QR Code after that you can start\n\n");
    
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     
       
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");  
   LocalDateTime now = LocalDateTime.now();
   String filetime = dtf.format(now);
 System.out.println("\t please enter drive name in which you want to save the doucument.");
 String driveName =  br.readLine();
 openBrowser.createFile(filetime, driveName.toUpperCase());
  System.out.println("Please enter the index value of the operation which you want to perform");
  System.out.println("\n 1 for monitoring only a single group.\n 2 for monitoring all the groups whenever a new messege occur.");
  System.out.println("Plese enter the value:");
  int operation =  Integer.parseInt(br.readLine());
  switch(operation){
  
      case 1:System.out.println("You want to monitor only a single group\t\n\tPlease Enter the name of the group.");
          String s = br.readLine();
          
          openBrowser.individualGroup(s);
          break;
      case 2: System.out.println("You want to monitor all the groups for new messeges.");     
             openBrowser.OpenApp();
             break;
      default:System.out.println("there is no such a operation .\n exit.");       
  }
  
   //openBrowser.OpenApp();
       try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(WhatsAppBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

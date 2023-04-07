/* Quincy Yoshida
Project */

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import java.util.ArrayList;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.application.Application;

public class Project extends Application
{
   BorderPane bp = new BorderPane();
   GridPane grid = new GridPane();
   HBox top = new HBox();
   
   private Button buttTop = new Button();          //Creates all the buttons
   private Button buttLeft = new Button();
   private Button buttRight = new Button();
   private Button buttBot = new Button();
   
   int ballsLeft = 15;
   int movesPossible = 2;
   Label label1 = new Label (); 
          
   GamePane [][] gpArray = new GamePane [4][4];    //creates the game board of circles and buttons
   
   public void start(Stage stage)
   {            
      FlowPane center = new FlowPane();
      center.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));             
      
      for (int i = 0; i < 4; i++)        //adds each button and circle into the aarray
      {
         for (int j = 0; j < 4; j++)
         {
            gpArray[i][j] = new GamePane(i,j);
            grid.add(gpArray[i][j],i,j);
         }
      }
      gpArray[0][2].setVisible(false);
      
      kick();
      
      label1.setText("Balls Left: "+ballsLeft+"    Possible Moves: "+movesPossible);
      
      center.setAlignment(Pos.CENTER);
      center.getChildren().add(grid);
      bp.setCenter(center);      
        
      top.getChildren().add(label1);   
      bp.setTop(label1);
      
      Scene scene = new Scene(bp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project");
      stage.show(); 
   }
   
   public class GamePane extends GridPane
   {
      private Button buttTop = new Button();          //Creates all the buttons
      private Button buttLeft = new Button();
      private Button buttRight = new Button();
      private Button buttBot = new Button();
      private Canvas canvas = new Canvas(80,80);
      
      GraphicsContext gc = canvas.getGraphicsContext2D(); 
      
      private int x;
      private int y;
      private int buttonName;
      
      public GamePane(int x, int y)
      {
         gc.setFill(Color.BLUE);      //adds circle to canvas
         gc.fillOval(0,0,80,80);
         
         buttTop.setPrefSize(80,20);      //adds sets dimensions of buttons
         buttLeft.setPrefSize(20,80);
         buttRight.setPrefSize(20,80);
         buttBot.setPrefSize(80,20);
         
         add(buttTop,1,0);      //adds components to gridpane
         add(buttLeft,0,1);
         add(canvas,1,1);
         add(buttRight,2,1);
         add(buttBot,1,2);
         
         this.x = x;
         this.y = y;
         
         buttTop.setOnAction(new buttonListener());
         buttLeft.setOnAction(new buttonListener());
         buttRight.setOnAction(new buttonListener());
         buttBot.setOnAction(new buttonListener());
      }
      
      public Button getBTop()            //gets the top button
      {
         return buttTop;
      }
      public Button getBLeft()            //gets the left button
      {
         return buttLeft;
      }
      public Button getBRight()            //gets the right button
      {
         return buttRight;
      }
      public Button getBBot()            //gets the bottom button
      {
         return buttBot;
      }
      
      public class buttonListener implements EventHandler<ActionEvent>
      {
         public void handle(ActionEvent event) 
         {
            if (event.getSource() == buttTop)
            {
               buttonName = 0;                                 //Top Button
               click(x,y,buttonName);
            }
            if (event.getSource() == buttLeft)
            {
               buttonName = 1;                                 //Left Button
               click(x,y,buttonName);
            }
            if (event.getSource() == buttRight)
            {
               buttonName = 2;                                 //Right Button
               click(x,y,buttonName);
            }
            if (event.getSource() == buttBot)
            {
               buttonName = 3;                                 //Bottom Button
               click(x,y,buttonName);
            }  
              
            label1.setText("Balls Left: "+ballsLeft+"    Possible Moves: "+movesPossible); 
            if (movesPossible == 0 && ballsLeft != 1)      //tells you if you lose
            {
               label1.setText("YOU LOSE");
               bp.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));  
            }
            if (ballsLeft == 1)
            {
               label1.setText("YOU WIN");                  //tells you if you win
               bp.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));  
            }        
         }
      }   
   }
   
   public void click(int x, int y, int buttonName)
   {
      if (buttonName == 0) //top
      {
         if (y <= 1)
         {
            gpArray[x][y].setVisible(false);
            gpArray[x][y+1].setVisible(false);
            gpArray[x][y+2].setVisible(true);
         }
      }
      if (buttonName == 1) //left
      {
         if (x <= 1)
         {
            gpArray[x][y].setVisible(false);
            gpArray[x+1][y].setVisible(false);
            gpArray[x+2][y].setVisible(true);
         }         
      }
      if (buttonName == 2) //right
      {
         if (x >= 2)
         {
            gpArray[x][y].setVisible(false);
            gpArray[x-1][y].setVisible(false);
            gpArray[x-2][y].setVisible(true);
         }
      }
      if (buttonName == 3) //bottom
      {
         if (y >= 2)
         {
            gpArray[x][y].setVisible(false);
            gpArray[x][y-1].setVisible(false);
            gpArray[x][y-2].setVisible(true);
         }
      }
      kick();
   }
   
   public void kick ()
   {
      movesPossible = 0;
      ballsLeft = 0;
      for (int x = 0; x < 4; x++)
      {
         for (int y = 0; y < 4; y++)          //sets all the buttons to non visible
         {
            gpArray[x][y].getBTop().setVisible(false);
            gpArray[x][y].getBLeft().setVisible(false);
            gpArray[x][y].getBRight().setVisible(false);
            gpArray[x][y].getBBot().setVisible(false);
         }
      }
      for (int i = 0; i < 4; i++)    //figures out if the moves are possible or not
      {
         for (int j= 0; j < 4; j++)
         {         
            if (gpArray[i][j].isVisible() == false)
            {
               if (i-2 >= 0)
               {
                  if (gpArray[i-1][j].isVisible() == true && gpArray[i-2][j].isVisible() == true)
                  {
                     gpArray[i-2][j].getBLeft().setVisible(true);
                     movesPossible++;                        
                  }
               }
            
               if (i+2 <= 3)
               {
                  if (gpArray[i+1][j].isVisible() == true && gpArray[i+2][j].isVisible() == true)
                  {
                     gpArray[i+2][j].getBRight().setVisible(true);
                     movesPossible++;
                  }
               }
                  
               if (j-2 >= 0)
               {
                  if (gpArray[i][j-1].isVisible() == true && gpArray[0][j-2].isVisible() == true)
                  {
                     gpArray[i][j-2].getBTop().setVisible(true);
                     movesPossible++;
                  }
               }
               if (j+2 <= 3)
               {
                  if (gpArray[i][j+1].isVisible() == true && gpArray[i][j+2].isVisible() == true)
                  {
                     gpArray[i][j+2].getBBot().setVisible(true);
                     movesPossible++;
                  }
               }
            }                     
         }
      }
      for (int i = 0; i < 4; i++)   //counts the number of balls left
      {
         for (int j = 0; j < 4; j++)
         {
            if (gpArray[i][j].isVisible() == true)
            {
               ballsLeft++;
            }
         }
      }
   }
   
   public static void main(String[] args)
   {
      launch(args);
   }
}
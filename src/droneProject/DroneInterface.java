package droneProject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;


/**
 * @author ronniebarker
 * GUI interface
 */
public class DroneInterface extends Application {
    private int canvasSize = 512;                // constant for canvas size
    private MyCanvas mc;                        // canvas in which arena drawn
    private DroneArena arena;                // object for drone arena
    private VBox rtPane;                        // Vertical box for displaying status of drones
    private boolean animationOn = false;        //animation off at beginning

    /**
     * show where pieces are, in pane on right
     */
    public void drawStatus() {
        rtPane.getChildren().clear();                    // clear rtpane
        // now create label
        Label l = new Label(arena.toString());
        rtPane.getChildren().add(l);                // add label to pane
    }

    /**
     * draw the drone arena
     */
    public void displaySystem() {
        mc.clearCanvas(); //clear canvas
        arena.showDrones(mc); //show pieces
        drawStatus();
    }

    /**
     * load arena from file
     */
    public void openFile() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                JFileChooser chooser = new JFileChooser();
                int approve = chooser.showOpenDialog(null);
                if (approve == JFileChooser.APPROVE_OPTION) {				// if possible
                    File selFile = chooser.getSelectedFile();				//selected file

                    if(selFile.isFile()){ //exits and is a file
                        //set up file and stream
                        try {
                            FileReader fileReader = new FileReader(selFile);
                            BufferedReader bufReader = new BufferedReader(fileReader);
                            String input;
                            input = bufReader.readLine();

                            int line = 0;
                            int droneCount = 0;
                            int xSize = 0;
                            int ySize = 0;
                            double x = 0;
                            double y = 0;
                            int ID = 0;
                            char colour = 'b';
                            double speed = 0;
                            double angle = 0;
                            while (input!= null) { 		//loop to include whole file
                                if (line == 0) {		//first line has x size
                                    xSize = Integer.parseInt(input);
                                }
                                else if (line == 1) {		//second line has y size
                                    ySize = Integer.parseInt(input);
                                    arena = new DroneArena(xSize, ySize);	  	//create arena of this size
                                }

                                else {						// if past size of arena, add drones
                                    if(droneCount == 0) {		//if at beginning set colour
                                        colour = input.charAt(0);
                                    }
                                    if(droneCount == 1) {		//if at beginning set ID
                                        ID = Integer.parseInt(input);
                                    }
                                    if(droneCount == 2) {		//next add x co ordinate
                                        x = Double.parseDouble(input);
                                    }
                                    if(droneCount == 3) {		//next add y co ordinate
                                        y = Double.parseDouble(input);
                                    }
                                    if(droneCount == 4) {		//next add speed if has one
                                        if (input.length() > 0){
                                            speed = Double.parseDouble(input);
                                        }

                                    }
                                    if(droneCount == 5) {		//next add angle if has one
                                        if (input.length() > 0){
                                            angle = Double.parseDouble(input);
                                        }

                                        //add to arena
                                        arena.addPiece(colour, x, y, ID, speed, angle);
                                        droneCount = -1;		//reset droneCount
                                    }
                                    droneCount ++;

                                }
                                line ++;

                                input = bufReader.readLine();
                            }
                            displaySystem();
                            bufReader.close();
                        }

                        catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }




                    }

                }
            }
        });
        t1.start();

    }


    /**
     * Function to show a message,
     *
     * @param TStr title of message block
     * @param CStr content of message
     */
    private void showMessage(String TStr, String CStr) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(TStr);
        alert.setHeaderText(null);
        alert.setContentText(CStr);

        alert.showAndWait();
    }

    /**
     * function to show in a box ABout the programme
     */
    private void showAbout() {
        showMessage("About", "Ronnie's Drone Simulation");
    }

    /**
     * Function to set up the menu
     */
    MenuBar setMenu() {
        MenuBar menuBar = new MenuBar();        // create menu

        Menu mHelp = new Menu("Help");            // have entry for help
        // then add sub menus for About and Help
        // add the item and then the action to perform
        MenuItem mAbout = new MenuItem("About");
        mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAbout();                // show the about message
            }
        });
        mHelp.getItems().addAll(mAbout);    // add submenus to Help

        // now add File menu
        Menu mFile = new Menu("File");
        MenuItem mExit = new MenuItem("Exit");
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);                        // quit program
            }
        });

        //add save option
        MenuItem mSave = new MenuItem("Save");
        mSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                arena.saveArena();                        // save arena
            }
        });

        //add load option
        MenuItem mLoad = new MenuItem("Load Arena");
        mLoad.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                openFile();                        // load arena
            }
        });

        mFile.getItems().addAll(mSave, mLoad, mExit);

        menuBar.getMenus().addAll(mFile, mHelp);    // menu has File and Help

        return menuBar;                    // return the menu, so can be added
    }

    /**
     * set up the buttons and return so can add to borderpane
     *
     * @return
     */
    private HBox setButtons() {
        // create button
        Button btnAnimOn = new Button("Start Animation");
        // now add handler
        btnAnimOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animationOn = true;
            }
        });

        Button btnAnimOff = new Button("Stop Animation");
        // now add handler
        btnAnimOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animationOn = false;
            }
        });

        // create button
        Button btnnewD = new Button("New Drone");
        // now add handler
        btnnewD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addDrone('d');

                displaySystem();
            }
        });

        // create button
        Button btnnewO = new Button("New Obstacle");
        // now add handler
        btnnewO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addDrone('o');
                displaySystem();
            }
        });

        // create button
        Button btnnewB = new Button("New Bird");
        // now add handler
        btnnewB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addDrone('b');

                displaySystem();
            }
        });

        return new HBox(btnAnimOn, btnAnimOff, btnnewD, btnnewO, btnnewB);
    }

    /**
     * main function ... sets up canvas, menu, buttons and timer
     */
    @Override
    public void start(Stage stagePrimary) throws Exception {
        stagePrimary.setTitle("UQ829259");

        BorderPane bp = new BorderPane();            // create border pane

        bp.setTop(setMenu());                        // create menu, add to top

        Group root = new Group();                    // create group
        Canvas canvas = new Canvas(canvasSize, canvasSize);
        // and canvas to draw in
        root.getChildren().add(canvas);            // and add canvas to group
        mc = new MyCanvas(canvas, canvasSize, canvasSize);
        // create MyCanvas passing size
        arena = new DroneArena(canvasSize, canvasSize);

        bp.setCenter(root);                            // put group in centre pane

        rtPane = new VBox();                        // set vBox for listing data
        bp.setRight(rtPane);                        // put in right pane

        Scene scene = new Scene(bp, canvasSize * 1.6, canvasSize * 1.2);
        // create scene so bigger than canvas,

        new AnimationTimer()			// create timer
        {
            public void handle(long currentNanoTime) {
                // define handler for what do at this time
                if (animationOn) {
                    arena.moveAllDrones();                    // move drones
                    displaySystem();                            // now clear canvas and draw system							// now clear canvas and draw system
                }
            }
        }.start();


        bp.setBottom(setButtons());                    /// add button to bottom

        stagePrimary.setScene(scene);
        stagePrimary.show();
    }
}
package droneProject;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

/**
 * @author ronniebarker
 *  Class to handle a canvas, used by different GUIs
 */
public class MyCanvas {
    int xCanvasSize = 512;				// constants for relevant sizes, default values set
    int yCanvasSize = 512;
    GraphicsContext gc;

    /**
     * constructor sets up relevant Graphics context and size of canvas
     * @param g
     * @param xcs
     */
    public MyCanvas(Canvas g, int xcs, int ycs) {
        gc = g.getGraphicsContext2D();
        xCanvasSize = xcs;
        yCanvasSize = ycs;
    }

    /**
     * get width of canvas
     * @return int width of canvas
     */
    public int getXCanvasSize() {
        return xCanvasSize;
    }

    /**
     * get height of canvas
     * @return int height of canvas
     */
    public int getYCanvasSize() {
        return yCanvasSize;
    }

    /**
     * clear the canvas
     */
    public void clearCanvas() {
        gc.setFill(Color.LIGHTBLUE); //set colour to blue (sky)
        gc.fillRect(0,  0,  xCanvasSize,  yCanvasSize);		// clear canvas
    }


    /**
     * function to convert char c to actual colour used
     * @param c
     * @return Color
     */
    Color colFromChar (char c){
        Color ans = Color.BLACK;
        switch (c) {
            case 'y' :	ans = Color.YELLOW;
                break;
            case 'w' :	ans = Color.WHITE;
                break;
            case 'r' :	ans = Color.RED;
                break;
            case 'g' :	ans = Color.GREEN;
                break;
            case 'b' :	ans = Color.BLUE;
                break;
            case 'o' :	ans = Color.ORANGE;
                break;
        }
        return ans;
    }

    /**
     * set the fill colour to color c
     * @param c
     */
    public void setFillColour (Color c) {
        gc.setFill(c);
    }

    /**
     * show the circle at position x,y , radius r in colour defined by col
     * @param x
     * @param y
     * @param rad
     * @param col
     */
    public void showCircle(double x, double y, double rad, char col) {
        setFillColour(colFromChar(col));			// set the fill colour
        showCircle(x, y, rad);						// show the circle
    }

    /**
     * show the circle in the current colour at x,y size rad
     * @param x
     * @param y
     * @param rad
     */
    public void showCircle(double x, double y, double rad) {
        gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
    }

    /**
     * Show Text .. by writing string s at position x,y
     * @param x
     * @param y
     * @param s
     */
    public void showText (double x, double y, String s) {
        gc.setTextAlign(TextAlignment.CENTER);							// set horizontal alignment
        gc.setTextBaseline(VPos.CENTER);								// vertical
        gc.setFill(Color.WHITE);										// colour in white
        gc.fillText(s, x, y);											// print score as text
    }


}


package Project1;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {

    private static JFrame myFrame;

    // number of threads to use. Min is 32
    private static final int numThreads = 32;

    public static void main(String[] args) {
        // array that holds all the solutions
        final ChartManager[] charts = new ChartManager[numThreads];
        // creates an initial seating chart
        final ChartSnapshot startingChart = new ChartSnapshot();

        // init the charts with a starting chart
        for (int i = 0; i < charts.length; i++) {
            // init the Chart Manager
            charts[i] = new ChartManager(startingChart);
        }

        // start up the GUI
        initializeGUI(charts);

        // for each chart, start adding seats
        for (ChartManager cm : charts) {
            cm.start();
        }
        try {
            System.in.read();
        } catch (IOException e) {

        } finally {
            for (ChartManager cm : charts) {
                cm.interrupt();
            }
            myFrame.dispose();
        }
    }

    public static void initializeGUI(ChartManager[] charts){
        // new window
        myFrame = new JFrame("Seating Charts");
        // GUI terminates correctly
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final int rows = (int)Math.min((64/(ChartPanel.ROWS +1)), Math.floor(Math.sqrt(numThreads)));
        myFrame.setLayout(new GridLayout(rows,0,20,10));

        // for each Project1.ChartManager, create and set panels
        for (ChartManager cm : charts) {

            ChartPanel panel = new ChartPanel(cm.getBestChart());

            myFrame.getContentPane().add(panel);

            cm.setPanel(panel);
        }

        // packs the frames, no wasted space
        myFrame.pack();
        // visible so we can see the frame
        myFrame.setVisible(true);
    }

}

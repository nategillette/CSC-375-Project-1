package Project1;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class allows the system to represent a single Chart Graphically
 */
public class ChartPanel extends JPanel {
    // Uses the values in Project1.ChartSnapshot
    public static final int ROWS = ChartSnapshot.ROWS;
    public static final int COLS = ChartSnapshot.COLS;
    // Utilizes the gridPanel to keep the solutions organized
    private final JPanel gridPanel;

    JLabel[] cells = new JLabel[ROWS*COLS];

    /**
     * Creates a new Project1.ChartPanel that displays the seating chart
     * @param chart: this is a specific seating chart
     */
    public ChartPanel(ChartSnapshot chart){
        // orientation settings
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        gridPanel = new JPanel(new GridLayout(ROWS,COLS));
        this.add(gridPanel);

        // init cells
        for(int i =0; i < cells.length; i++){

            cells[i] = new JLabel("O");

            cells[i].setOpaque(true);

            gridPanel.add(cells[i]);
        }

        // first paint
        update(chart);
    }

    /**
     * Updates the seating chart with new seats
     * @param chart: The chart to be displayed
     */
    void update(ChartSnapshot chart){
        // indexes the array of charts
        int index = 0;
        // for each row
        for(int i =0; i < ROWS; i++){
            // for each column
            for(int j = 0; j < COLS; j++){
                // background color should be the color of the chart at this location
                cells[index].setBackground(getColor(chart.type(i,j)));
                // increment the index
                index++;
            }
        }
    }

    /**
     * Returns the color of the seat
     * @param c: a seat as an ENUM of colors
     * @return: returns a color
     */
    public static Color getColor(Colors c){
        if(c == Colors.Green) return Color.green;
        else if (c == Colors.Red) return Color.red;
        else return Color.white;
    }
}

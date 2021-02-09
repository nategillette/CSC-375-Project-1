package Project1;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Exchanger;

public class ChartManager extends Thread{
    //  current solution
    private ChartSnapshot chart;
    // best solution
    private ChartSnapshot bestChart;
    // current number of chairs
    private int metric;
    // highest number of chairs
    private int bestMetric;
    // inits the Project1.ChartPanel to display, starts as null
    private ChartPanel display = null;
    // keeps time of last drawn, inits to 0
    private long lastDraw = 0;
    // I found this to allow me to swap ChartSnapshots easily
    private static final Exchanger<ChartSnapshot> SWAP_SPOT = new Exchanger<ChartSnapshot>();

    private final int MAX_TIMES = 15;

    public ChartManager(ChartSnapshot cs){
        chart = cs;
        bestChart = cs;

        metric = ChartSnapshot.numChairs(cs);
        bestMetric = metric;

        setDaemon(true);

    }

    @Override
    public void run() {
        // thread spawner
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        final int rows = ChartSnapshot.ROWS;
        final int cols = ChartSnapshot.COLS;
        // keeps track of how many times drawn
        int count = 0;

        while (!interrupted()) {

            final int row1 = rand.nextInt(rows);
            final int col1 = rand.nextInt(cols);

            final int row2 = rand.nextInt(rows);
            final int col2 = rand.nextInt(cols);

            final Colors seat1 = chart.type(row1, col2);

            final Colors seat2 = chart.type(row2, col2);

            final ChartSnapshot newChart = chart.replace(seat1, row2, col2).replace(seat2, row1, col1);
            // this will keep the chart with the highest number of chairs, will force swap 5% of time
            keepBest(newChart, (rand.nextInt(20) == 1));
            // 10% chance to swap
            if(rand.nextInt(10)==1) {
                try {
                    final ChartSnapshot trial = SWAP_SPOT.exchange(chart);

                    if (ChartSnapshot.numChairs(chart) == ChartSnapshot.numChairs(trial)) {
                        keepBest(trial, (rand.nextInt(20) == 1));
                    }
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
            // if you can display, it's been a long enough time, and there hasn't been too many displays
            if (display != null && lastDraw + 1000 < System.currentTimeMillis() && count < MAX_TIMES) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    display.update(chart);
                });
                // updates last time drawn
                lastDraw = System.currentTimeMillis();

                count ++;
            }
            // if drawn enough times
            if(count == MAX_TIMES){
                // display the best chart found
                display.update(bestChart);
            }

            try {
                //waiting between swaps to avoid total load of my system.
                sleep(5);
            } catch (InterruptedException e) {
                //be sure we don't miss our termination signal
                this.interrupt();

            }
        }
    }

    public int getBestMetric() {return bestMetric;}

    public ChartSnapshot getBestChart(){return bestChart;}

    public int getLastMetric(){return metric;}

    public ChartSnapshot getLastChart(){return chart;}

    private void keepBest(ChartSnapshot newChart, boolean succeed){

        final int newMetric = ChartSnapshot.numChairs(newChart);

        if(newMetric > metric){
            metric = newMetric;
            chart = newChart;

            if(newMetric > bestMetric){
                bestMetric = newMetric;
                bestChart = newChart;
            }
        }

        else if(succeed){
            metric = newMetric;
            chart = newChart;
        }
    }

    public void setPanel(ChartPanel panel){display = panel;}
}

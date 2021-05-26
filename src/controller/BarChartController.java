package controller;

import model.MyDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.ui.*;
import org.jfree.data.category.DefaultCategoryDataset;
import view.CommuteVsShelterCostGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.*;

import static view.Tool.BACKGROUND_COLOUR;

public class BarChartController extends ToolController implements ActionListener {

    //create an barChartGui to refer to
    CommuteVsShelterCostGUI barChartGui;
    int rangeMax[] = new int[5];
    int commuteTypeCount[][] = new int[5][6];
    //constructor
    public BarChartController(CommuteVsShelterCostGUI barChartGui){
        this.barChartGui = barChartGui;
        setUpListeners();
    }

    //adds action listeners to buttons
    public void setUpListeners(){
        
        barChartGui.markhamButton.addActionListener(this);
        barChartGui.auroraButton.addActionListener(this);
        barChartGui.newMarketButton.addActionListener(this);
        barChartGui.vaughnButton.addActionListener(this);
        barChartGui.richmondButton.addActionListener(this);
    
    }

    //set information available for barchartgui
    @Override
    public void initializeDataToDisplay (MyDataset[] dataset) {

        String groupNameJourneyToWork = barChartGui.getValidGroupNames(0).get(0);
        String groupNameProfileOfHousing = barChartGui.getValidGroupNames(1).get(0);
        barChartGui.setDataGroup(groupNameJourneyToWork);
        barChartGui.setDataGroup2(dataset[1].getDataset().get(groupNameProfileOfHousing));

        createChart(barChartGui.getValidGroupNames(1).get(0), barChartGui.getDisplayedData());

    }

    //creates dataset to display in chart
    private DefaultCategoryDataset createDataSet(String currentCity) {
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<ArrayList<String>> rawData1 = FileImportController.rawData[1];
        ArrayList<ArrayList<String>> rawData2 = FileImportController.rawData[0];

        //2d array holding ranges as rows and totals of each commute type as columns


        //iterate through the barChartGui datagroup
        for(int i = 1; i < rawData1.size(); i++ ){

            int parsedData = (int)(Double.parseDouble(rawData1.get(i).get(69)));

            //if the city name in the row in the spreadsheet matches city button pressed and monthly shelter rent amount
            //is within the range, add up all the commute type amounts and store it in the 2d array
             if(rawData1.get(i).get(3).equals(currentCity) && (parsedData >= 601 &&
                    parsedData <= 1000)){

                commuteTypeCount[1][0] += (int)(Double.parseDouble(rawData2.get(i).get(10)));
                commuteTypeCount[1][1] += (int)(Double.parseDouble(rawData2.get(i).get(11)));
                commuteTypeCount[1][2] += (int)(Double.parseDouble(rawData2.get(i).get(12)));
                commuteTypeCount[1][3] += (int)(Double.parseDouble(rawData2.get(i).get(13)));
                commuteTypeCount[1][4] += (int)(Double.parseDouble(rawData2.get(i).get(14)));
                commuteTypeCount[1][5] += (int)(Double.parseDouble(rawData2.get(i).get(15)));

            }else if(rawData1.get(i).get(3).equals(currentCity) && (parsedData >= 1001 &&
                    parsedData <= 1400)){

                commuteTypeCount[2][0] += (int)(Double.parseDouble(rawData2.get(i).get(10)));
                commuteTypeCount[2][1] += (int)(Double.parseDouble(rawData2.get(i).get(11)));
                commuteTypeCount[2][2] += (int)(Double.parseDouble(rawData2.get(i).get(12)));
                commuteTypeCount[2][3] += (int)(Double.parseDouble(rawData2.get(i).get(13)));
                commuteTypeCount[2][4] += (int)(Double.parseDouble(rawData2.get(i).get(14)));
                commuteTypeCount[2][5] += (int)(Double.parseDouble(rawData2.get(i).get(15)));

            }else if (rawData1.get(i).get(3).equals(currentCity) && (parsedData >= 1401 &&
                    parsedData <= 1800)){

                commuteTypeCount[3][0] += (int)(Double.parseDouble(rawData2.get(i).get(10)));
                commuteTypeCount[3][1] += (int)(Double.parseDouble(rawData2.get(i).get(11)));
                commuteTypeCount[3][2] += (int)(Double.parseDouble(rawData2.get(i).get(12)));
                commuteTypeCount[3][3] += (int)(Double.parseDouble(rawData2.get(i).get(13)));
                commuteTypeCount[3][4] += (int)(Double.parseDouble(rawData2.get(i).get(14)));
                commuteTypeCount[3][5] += (int)(Double.parseDouble(rawData2.get(i).get(15)));

            }else if(rawData1.get(i).get(3).equals(currentCity) && (parsedData > 1800)){

                commuteTypeCount[4][0] += (int)(Double.parseDouble(rawData2.get(i).get(10)));
                commuteTypeCount[4][1] += (int)(Double.parseDouble(rawData2.get(i).get(11)));
                commuteTypeCount[4][2] += (int)(Double.parseDouble(rawData2.get(i).get(12)));
                commuteTypeCount[4][3] += (int)(Double.parseDouble(rawData2.get(i).get(13)));
                commuteTypeCount[4][4] += (int)(Double.parseDouble(rawData2.get(i).get(14)));
                commuteTypeCount[4][5] += (int)(Double.parseDouble(rawData2.get(i).get(15)));

            }
        }


        //calculate the largest commute type amount for each range
        largestValue(commuteTypeCount, rangeMax);

        //add information to dataset
        dataset.addValue(rangeMax[1], currentCity, "601-1000");
        dataset.addValue(rangeMax[2], currentCity, "1001-1400");
        dataset.addValue(rangeMax[3], currentCity, "1401-1800");
        dataset.addValue(rangeMax[4], currentCity, "1800+");

        updateCommonCommuteTypeDisplay(commuteTypeCount, rangeMax, rawData2);

        return dataset;
    }

    private void updateCommonCommuteTypeDisplay(int[][] commuteTypeCount, int[] rangeMax, ArrayList<ArrayList<String>> rawData2) {
        String line1 = barChartGui.getLine1();
        String line2 = barChartGui.getLine2();
        String line3 = barChartGui.getLine3();
        String line4 = barChartGui.getLine4();
            for(int j = 0; j < 5; j++) {
                if (commuteTypeCount[1][j] == rangeMax[1]) {
                    barChartGui.setLine1("Common Commute displayed for $601-1000: " + rawData2.get(0).get(10 + j));
                    barChartGui.setTexts(String.format("<html>%s<br/><br/>%s<br/><br/>%s<br/><br/>%s</html>", line1, line2, line3, line4));
                    barChartGui.getCommuteDisplay().setText(barChartGui.getTexts());
                    System.out.println("updated label");

                }
                if(commuteTypeCount[2][j] == rangeMax[2]){
                    barChartGui.setLine2("Common Commute displayed for $1001-1400: " + rawData2.get(0).get(10 + j));
                    barChartGui.setTexts(String.format("<html>%s<br/><br/>%s<br/><br/>%s<br/><br/>%s</html>", line1, line2, line3, line4));
                    barChartGui.getCommuteDisplay().setText(barChartGui.getTexts());

                }
                if(commuteTypeCount[3][j] == rangeMax[3]){
                    barChartGui.setLine3("Common Commute displayed for $1401-1800: " + rawData2.get(0).get(10 + j));
                    barChartGui.setTexts(String.format("<html>%s<br/><br/>%s<br/><br/>%s<br/><br/>%s</html>", line1, line2, line3, line4));
                    barChartGui.getCommuteDisplay().setText(barChartGui.getTexts());

                }
                if(commuteTypeCount[4][j] == rangeMax[4]){
                    barChartGui.setLine4("Common Commute displayed for $1800+: " + rawData2.get(0).get(10 + j));
                    barChartGui.setTexts(String.format("<html>%s<br/><br/>%s<br/><br/>%s<br/><br/>%s</html>", line1, line2, line3, line4));
                    barChartGui.getCommuteDisplay().setText(barChartGui.getTexts());


                }
            }





    }


    //setting up the bar chart
    public void createChart (String groupNameProfileOfHousing, DefaultCategoryDataset displayedData) {
        
        String chartTitle = " Commute Type V.S " + groupNameProfileOfHousing;
        String xAxisLabel = groupNameProfileOfHousing + "($)";
        String valueAxisLabel = "Number of People";


        barChartGui.setBarChart(ChartFactory.createBarChart(
                chartTitle, xAxisLabel, valueAxisLabel,
                displayedData, PlotOrientation.VERTICAL,
                true, true, false));
        barChartGui.getBarChart().setBackgroundPaint(BACKGROUND_COLOUR);

        if (barChartGui.getChartPanel()!=null) {
            barChartGui.remove(barChartGui.getChartPanel());
        }
        barChartGui.setChartPanel(new ChartPanel(barChartGui.getBarChart()));
        barChartGui.getChartPanel().setBounds(50, 100, 1000, 450);
        barChartGui.add(barChartGui.getChartPanel());
        
    }

    //find largest value in each row of 2d array and store it in rangeMax array
    private void largestValue(int commuteTypeCount[][], int rangeMax[]) {

        for(int i = 0; i < commuteTypeCount.length; i++){

            rangeMax[i] = commuteTypeCount[i][0];

            for(int j = 0; j < commuteTypeCount[i].length; j++)
                if(rangeMax[i] < commuteTypeCount[i][j])
                    rangeMax[i] = commuteTypeCount[i][j];

        }

    }


    //if user presses a city button, change the current city to corresponding city name, create the proper dataset
    //and update the bar chart
    @Override
    public void actionPerformed (ActionEvent e) {

        if(e.getSource() == barChartGui.markhamButton){
            System.out.println("woo");
            barChartGui.setCurrentCity("Markham");
            barChartGui.setDisplayedData(createDataSet(barChartGui.getCurrentCity()));
            createChart(barChartGui.getValidGroupNames(1).get(0), barChartGui.getDisplayedData());
            barChartGui.getChartPanel().repaint();
            barChartGui.getCommuteDisplay().repaint();
        }else if(e.getSource() == barChartGui.richmondButton){
            barChartGui.setCurrentCity("Richmond Hill");
            barChartGui.setDisplayedData(createDataSet(barChartGui.getCurrentCity()));
            createChart(barChartGui.getValidGroupNames(1).get(0), barChartGui.getDisplayedData());
            barChartGui.getChartPanel().repaint();
            barChartGui.getCommuteDisplay().repaint();

        }else if(e.getSource() == barChartGui.auroraButton){
            barChartGui.setCurrentCity("Aurora");
            barChartGui.setDisplayedData(createDataSet(barChartGui.getCurrentCity()));
            createChart(barChartGui.getValidGroupNames(1).get(0), barChartGui.getDisplayedData());
            barChartGui.getChartPanel().repaint();
            barChartGui.getCommuteDisplay().repaint();

        }else if(e.getSource() == barChartGui.newMarketButton){
            barChartGui.setCurrentCity("Newmarket");
            barChartGui.setDisplayedData(createDataSet(barChartGui.getCurrentCity()));
            createChart(barChartGui.getValidGroupNames(1).get(0), barChartGui.getDisplayedData());
            barChartGui.getChartPanel().repaint();
            barChartGui.getCommuteDisplay().repaint();

        }else if(e.getSource() == barChartGui.vaughnButton){
            barChartGui.setCurrentCity("Vaughan");
            barChartGui.setDisplayedData(createDataSet(barChartGui.getCurrentCity()));
            createChart(barChartGui.getValidGroupNames(1).get(0), barChartGui.getDisplayedData());
            barChartGui.getChartPanel().repaint();
            barChartGui.getCommuteDisplay().repaint();

        }
    
    }


}

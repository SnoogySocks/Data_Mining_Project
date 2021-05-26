package view;

import model.*;
import controller.FileImportController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.*;
import util.Category;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CommuteVsShelterCostGUI extends Tool{

    //components
    private JLabel titleLabel = new JLabel(new ImageIcon("img/titles/shelterCostVsCommonCommuteTypeTitle.png"));
    private JLabel buttonTitleLabel = new JLabel("Select City for Data Display");
    
    public JButton markhamButton = new JButton("Markham");
    public JButton vaughnButton = new JButton("Vaughan");
    public JButton richmondButton = new JButton("Richmond Hill");
    public JButton auroraButton = new JButton("Aurora");
    public JButton newMarketButton = new JButton("Newmarket");
    
    private DefaultCategoryDataset displayedData = new DefaultCategoryDataset();
    private JFreeChart barChart;
    private ChartPanel chartPanel;
    private JLabel commuteDisplay = new JLabel("Ranges");
    private ArrayList<Category> dataGroup2;

    private String currentCity;
    private String line1 = "Common Commute for $601-1000: ";
    private String line2 = "Common Commute for $1001-1400: ";
    private String line3 = "Common Commute for $1401-1800: ";
    private String line4 = "Common Commute for $1800+: ";
    private String text = String.format("<html>%s<br/><br/>%s<br/><br/>%s<br/><br/>%s</html>", line1, line2, line3, line4);

    //constructor
    public CommuteVsShelterCostGUI () {
        
        markhamButton.setFont(new Font("Tahoma",Font.PLAIN, 15));
        vaughnButton.setFont(new Font("Tahoma",Font.PLAIN, 15));
        richmondButton.setFont(new Font("Tahoma",Font.PLAIN, 15));
        auroraButton.setFont(new Font("Tahoma",Font.PLAIN, 15));
        newMarketButton.setFont(new Font("Tahoma",Font.PLAIN, 15));
        
        titleLabel.setBounds(0, 0, 1366, 768);
        add(titleLabel);

        commuteDisplay.setOpaque(true);
        Border border2 = BorderFactory.createLineBorder(Color.BLACK, 2);
        commuteDisplay.setBorder(border2);
        commuteDisplay.setFont(new Font("Tahoma", Font.BOLD, 10));
        commuteDisplay.setHorizontalAlignment(SwingConstants.LEFT);
        commuteDisplay.setVerticalAlignment(SwingConstants.CENTER);
        commuteDisplay.setBackground(Color.CYAN);
        commuteDisplay.setBounds(1100, 150, 245, 150);

        commuteDisplay.setText(text);
        add(commuteDisplay);

        add(getBackButton());
        
        buttonTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 3);
        buttonTitleLabel.setBorder(border);
        buttonTitleLabel.setBounds(490, 555, 350, 35);
        add(buttonTitleLabel);
        
        markhamButton.setBounds(400, 600, 100, 40);
        vaughnButton.setBounds(530, 600, 100, 40);
        richmondButton.setBounds(660, 600, 150, 40);
        auroraButton.setBounds(840, 600, 100, 40);
        newMarketButton.setBounds(580, 650, 150, 40);
        
        add(markhamButton);
        add(vaughnButton);
        add(richmondButton);
        add(auroraButton);
        add(newMarketButton);
        
    }
    //getters and setters
    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        
        this.currentCity = currentCity;
    }
    
    public DefaultCategoryDataset getDisplayedData () {
        return displayedData;
    }
    
    public void setDisplayedData (DefaultCategoryDataset displayedData) {
        this.displayedData = displayedData;
    }
    
    public JFreeChart getBarChart () {
        return barChart;
    }
    
    public void setBarChart (JFreeChart barChart) {
        this.barChart = barChart;
    }
    
    public ChartPanel getChartPanel () {
        return chartPanel;
    }
    
    public void setChartPanel (ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
    
    public ArrayList<Category> getDataGroup2 () {
        return dataGroup2;
    }
    
    public void setDataGroup2 (ArrayList<Category> dataGroup2) {
        this.dataGroup2 = dataGroup2;
    }

    public JLabel getCommuteDisplay() {
        return commuteDisplay;
    }

    public void setCommuteDisplay(JLabel commuteDisplay) {
        this.commuteDisplay = commuteDisplay;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

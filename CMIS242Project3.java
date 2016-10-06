package cmis.pkg242.project.pkg3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * 
 * @author AArgento
 * @date 25 September 2016
 * @class CMIS 242
 * @purpose Provide all required elements for GUI that allows user to input a value
 *          for n and based on selection of iterative or recursive radio buttons
 *          return the value of "n = 2(n-1) + (n-2)". This value will be populated
 *          in an uneditable text box along with a similar text box populated 
 *          with the efficiency of the algorithm (efficiency being defined as 
 *          number of calls to the method taken to achieve achieve result. 
 */

public class CMIS242Project3 extends JFrame {
    
    public static void main(String[] args) {
        CMIS242Project3 userInterface = new CMIS242Project3();
        userInterface.setLocationRelativeTo(null);
        userInterface.setResizable(false);        
        userInterface.setVisible(true);
    }//end main

    //define button group and radio buttons
    private final ButtonGroup typeGroup = new ButtonGroup();   
    private JRadioButton iterative = new JRadioButton("Iterative");
    private JRadioButton recursive = new JRadioButton("Recursive");
   
    //define labels
    private final JLabel labelN = new JLabel("Enter n:");
    private final JLabel labelResult = new JLabel("Result:");
    private final JLabel labelEfficiency = new JLabel("Efficiency:");
    
    //define text fields
    private JTextField textN = new JTextField("   enter value   ");
    private JTextField textResult = new JTextField("                     ");
    private JTextField textEfficiency = new JTextField("                     ");
    
    //define button
    private final JButton buttonCompute = new JButton("Compute");
    
    //define option panel
    private final JOptionPane optionPane = new JOptionPane();
    
    //define variables for files
    FileWriter fileWriter;
    File log = new File("Log.csv");
    
    //list to store vaules needed for file
    ArrayList<String> logList = new ArrayList<>();
    
    //user entry variable
    private int nValue;

    //constructor for CMIS242Project3
    public CMIS242Project3() {
        
        //set title and size of UI
        super("CMIS 242 - Project 3");
        setSize(330, 130); 
       
        //set defaults of specific objects
        iterative.setSelected(true);
        textEfficiency.setEditable(false);
        textResult.setEditable(false);
        
        //define required panels
        JPanel radioPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel outputPanel = new JPanel();

        //add panels to frame
        add(radioPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
        add(outputPanel, BorderLayout.SOUTH);
        
        //add radio buttons radioPanel and define radio group
        this.typeGroup.add(iterative);
        this.typeGroup.add(recursive);
        radioPanel.add(iterative);
        radioPanel.add(recursive);
        
        //add label and entry text box to inputPanel
        inputPanel.add(labelN);
        inputPanel.add(textN);
        
        //add Compute button to buttonPanel
        buttonPanel.add(buttonCompute);
        
        //add labels and results text boxes to outputPanel
        outputPanel.add(labelResult);
        outputPanel.add(textResult);
        outputPanel.add(labelEfficiency);
        outputPanel.add(textEfficiency);

        //define all required tool tips
        buttonCompute.setToolTipText("Click this to button compute result and"
                + " efficency.");
        iterative.setToolTipText("Select for iterative method");
        recursive.setToolTipText("Select for recursive method");
        textResult.setToolTipText("Value of nth number in sequence");
        textEfficiency.setToolTipText("Number of loops to achive result");
        
        //listener for tool tip action based on iterative radio button selection
        iterative.addActionListener((ActionEvent e) -> {
            textEfficiency.setToolTipText("Number of loops to achive result");
        }); 
        
        //listener for tool tip action based on recursive radio button selection
        recursive.addActionListener((ActionEvent e) -> {
            textEfficiency.setToolTipText("Number of method calls to achive result");
        });
        
        textN.setToolTipText("Enter n to determine value of nth number in sequence");
        
        //clear userInput textN text field on mouse click
        textN.addMouseListener(new MouseAdapter(){
            @Override
                public void mouseClicked(MouseEvent e){
                    clearEntryValue();
                }
            });

        //listener for compute button action
        buttonCompute.addActionListener((ActionEvent e) -> {
            setEntryValue();
            if (nValue > 30 || nValue < 0) {
                errorPopUp();
            } else if (iterative.isSelected()) {
                textResult.setText(String.valueOf(Sequence.iterativeMethod(nValue)));
                textEfficiency.setText(String.valueOf(Sequence.getIterativeEfficiency()));
            } else if (recursive.isSelected()) {
                textResult.setText(String.valueOf(Sequence.recursiveMethod(nValue)));
                textEfficiency.setText(String.valueOf(Sequence.getRecursiveEfficiency()));
            }
            //clearEntryValue();
        }); 
        
        //default constructor for ProgramExit
        ProgramExit exit = new ProgramExit();
        
        //calls listener on Program exit
        addWindowListener(exit);

}//end constructor

    //retrieves n value from nText field. Ensures number is valid
    //sets calls method to set value to "" if invalid.
    private int getEntryValue() {
        try {
            return Integer.parseInt(textN.getText());
        } catch (NumberFormatException e) {
            errorPopUp();
            clearEntryValue();
            return 0;
        }
    }
    
    //method to set nValue 
    private void setEntryValue() {
        this.nValue = getEntryValue();
    }

    // Clears the text entry field
    private void clearEntryValue() {
        textN.setText("");
    }

    //defines method for JOptionPane to be displayed on invalid inputs
    private void errorPopUp() {
        JOptionPane.showMessageDialog(optionPane, "ERROR: Invalid input! \nValue"
                + " is either not an iteger or not between 0 and 30. \nPlease "
                + "enter an integer between 0 and 30.");
    }
    
    //listener for program exit
    class ProgramExit extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            try {
                writeFile();
            } catch (NullPointerException e1) {
                System.out.println("Null Pointer Exception. \nProgram Exiting...");
                System.exit(0);
            }
            System.exit(0);
        }
        
        //method to write effiency data from iterative and recursive methods
        //to csv file.
        private void writeFile() {

            logList.add("n,Value of n,Iterative,Recursive");
        
            for (int i = 0; i <11; i++){
                logList.add(i + "," + Sequence.recursiveMethod(i) + ", " 
                        + Sequence.getIterativeEfficiencyForFile(i)+
                        ", " + Sequence.getRecursiveEfficiencyForFile(i));
            }
            
            try {
                fileWriter = new FileWriter(log);
                for (String log : logList) {
                    fileWriter.write(log + System.getProperty("line.separator"));
                }
                fileWriter.close();
            } catch (IOException e) {
                e.getMessage();
            }
            
        }//end writeFile
        
    }//end ProgramExit
    
}//end CMIS242Project3

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;



public class Calculator {
    Color customBlack = new Color(28, 28, 28);
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%", "√"};


    JFrame frame = new JFrame("Calculator");
    JLabel dispLabel = new JLabel();
    JPanel dispPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String B = null;
    String operator = null;


    Calculator(){
        
        frame.setSize(360, 540);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        dispLabel.setBackground(customBlack);
        dispLabel.setForeground(Color.white);
        dispLabel.setFont(new Font("Tahoma", Font.PLAIN, 80));
        dispLabel.setHorizontalAlignment(JLabel.RIGHT);
        dispLabel.setText("0");
        dispLabel.setOpaque(true);

        dispPanel.setLayout(new BorderLayout());
        dispPanel.add(dispLabel);
        frame.add(dispPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);


        for(int i = 0; i < buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Tahoma", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
 
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }
            else  if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
                
            } else{
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
            
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("=")) {
                            if (A != null && operator != null) {
                                B = dispLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);
            
                                switch (operator) {
                                    case "+":
                                        dispLabel.setText(removeZeroDecimal(numA + numB));
                                        break;
                                    case "-":
                                        dispLabel.setText(removeZeroDecimal(numA - numB));
                                        break;
                                    case "÷":
                                        if (numB == 0) {
                                            dispLabel.setText("Error"); // Handle division by zero
                                        } else {
                                            dispLabel.setText(removeZeroDecimal(numA / numB));
                                        }
                                        break;
                                    case "×":
                                        dispLabel.setText(removeZeroDecimal(numA * numB));
                                        break;
                                }
                                clearAll();
                            }
                        } else if (Arrays.asList("÷", "×", "-", "+").contains(buttonValue)) {
                            if (operator == null) {
                                A = dispLabel.getText();
                                dispLabel.setText("0");
                            }
                            operator = buttonValue;
                        }
                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("AC")) {
                            clearAll();
                            dispLabel.setText("0");
                        } else if (buttonValue.equals("+/-")) {
                            double numDisplay = Double.parseDouble(dispLabel.getText());
                            numDisplay *= -1;
                            dispLabel.setText(removeZeroDecimal(numDisplay));
                        } else if (buttonValue.equals("%")) {
                            double numDisplay = Double.parseDouble(dispLabel.getText());
                            numDisplay /= 100;
                            dispLabel.setText(removeZeroDecimal(numDisplay));
                        } else if (buttonValue.equals("√")) {
                            double numDisplay = Double.parseDouble(dispLabel.getText());
                            if (numDisplay < 0) {
                                dispLabel.setText("Error"); // Handling negative numbers (invalid for real square roots)
                            } else {
                                numDisplay = Math.sqrt(numDisplay);
                                dispLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                    } else {
                        if (buttonValue.equals(".")) {
                            if (!dispLabel.getText().contains(".")) {
                                dispLabel.setText(dispLabel.getText() + buttonValue);
                            }
                        } else {
                            if (dispLabel.getText().equals("0")) {
                                dispLabel.setText(buttonValue);
                            } else {
                                dispLabel.setText(dispLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
            
            
        }
        frame.setVisible(true);
    }
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }
    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
    
}

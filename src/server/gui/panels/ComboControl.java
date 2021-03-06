package server.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import server.gui.actions.ActionEvents;
import server.gui.actions.MouseEvents;
import server.sys.SubjectImplementation;
import server.sys.observer.EmotivObserver;
import server.sys.observer.PassedData;
import util.Constants;

/**
 * This class is used as a general utility for a field needing an increment and decrement
 * with a text box
 * 
 * @author Cephas Armstrong-Mensah
 * @author Group 1 #001 - #013
 * @version 1.0
 * @since 29MAR2018
 *
 */
public class ComboControl extends JPanel implements EmotivObserver {
  private static final long serialVersionUID = -7926485727775080081L;

  private JTextField outputTextBox;
  private JLabel incrementButton;
  private JLabel decrementButton;
  private JPanel panel;

  private double step;
  private boolean isFrequency;

  private SubjectImplementation er;
  private String id;
  private String buttonText;

  /**
   * @param newWidth - the width for the entire panel
   * @param step - the next step size when button is clicked
   * @param isFrequency - determine if this field is for frequency
   */
  public ComboControl(SubjectImplementation er, int newWidth, double step, boolean isFrequency,
      String id) {
    this.id = id;
    this.er = er;
    this.step = step;
    this.isFrequency = isFrequency;

    setLayout(null);
    this.setMinimumSize(new Dimension(70, 30));
    setBounds(0, 0, newWidth < 70 ? 70 : newWidth, 30);
    buttonText = "Send";
    initialize(newWidth);
  }

  private void initialize(int newWidth) {
    outputTextBox = new JTextField("0.0");
    outputTextBox.setHorizontalAlignment(SwingConstants.LEFT);
    outputTextBox.setBounds(0, 0, newWidth - 30, 30);
    outputTextBox.addActionListener(new ActionEvents(this));

    panel = new JPanel();
    panel.setBounds(newWidth - 28, 0, 28, 30);
    panel.setLayout(null);

    incrementButton = new JLabel(Character.toString((char) 0x028C));
    incrementButton.setBounds(0, 0, 26, 15);
    incrementButton.setVerticalAlignment(SwingConstants.BOTTOM);
    incrementButton.setHorizontalTextPosition(SwingConstants.CENTER);
    incrementButton.setHorizontalAlignment(SwingConstants.CENTER);

    decrementButton = new JLabel("v");
    decrementButton.setBounds(0, 15, 26, 15);
    decrementButton.setVerticalAlignment(SwingConstants.TOP);
    decrementButton.setHorizontalTextPosition(SwingConstants.CENTER);
    decrementButton.setHorizontalAlignment(SwingConstants.CENTER);

    panel.add(decrementButton);
    panel.add(incrementButton);

    add(panel);
    add(outputTextBox);

    if (this.id.equals("Neutral") || this.id.equals("Overall Skill") || this.id.equals("Skill")) {
      incrementButton.setEnabled(false);
      decrementButton.setEnabled(false);
      outputTextBox.setEnabled(false);
    } else {
      decrementButton.addMouseListener(new MouseEvents(this, this.id));
      incrementButton.addMouseListener(new MouseEvents(this, this.id));
    }
  }

  private void formatDoubleFirst(double value) {
    DecimalFormat df = new DecimalFormat("0.00");
    this.outputTextBox.setText(df.format(value));
    this.outputTextBox.setForeground(Color.BLACK);
    outputTextBox.setBorder(null);

    if (buttonText.equalsIgnoreCase("Send") || buttonText.equalsIgnoreCase("Suspend")) {
      buttonText = "Start";
    }

    er.sendButtonText(buttonText, this.outputTextBox.getText());
  }

  public void setOutputText(String txt) {
    this.outputTextBox.setText(txt);
  }

  public String getOutputText() {
    return outputTextBox.getText();
  }

  public void incrementOutputText(String switcher) {
    try {
      double x = Double.parseDouble(outputTextBox.getText());
      double value = 0.0;

      if (isFrequency) {
        value = 0.01;
        if (x >= 0.01) {
          value = x + step;
        }
      } else if (!isFrequency) {
        if (x + step > 1.0) {
          value = 1.0;
        } else if (x + step > 0.0) {
          value = x + step;
        }
      } else {
        throw new IOException();
      }

      formatDoubleFirst(value);
      if (switcher.equals("Upper Face") || switcher.equals("Lower Face")) {
        er.updateFacialPanel(true);
      } else if (switcher.equals("Performance")) {
        er.updatePerformance(true);
      }
    } catch (NumberFormatException | IOException exception) {
      outputTextBox.setForeground(Color.RED);
      outputTextBox.selectAll();
      outputTextBox.setBorder(new LineBorder(Constants.RED));
      er.sendButtonText("Suspend", "0.0");
    }
  }

  public void decrementOutputText(String switcher) {
    try {
      double x = Double.parseDouble(outputTextBox.getText());

      double value = 0.00;
      if (!isFrequency) {
        if (x - step >= 0.0) {
          value = x - step;
        }
      } else if (isFrequency) {
        value = 0.01;
        if (x - step > 0.01) {
          value = x - step;
        }
      } else {
        throw new IOException();
      }
      formatDoubleFirst(value);
      if (switcher.equals("Upper Face") || switcher.equals("Lower Face")) {
        er.updateFacialPanel(true);
      } else if (switcher.equals("Performance")) {
        er.updatePerformance(true);
      }
    } catch (NumberFormatException | IOException exception) {
      outputTextBox.setForeground(Constants.RED);
      outputTextBox.selectAll();
      outputTextBox.setBorder(new LineBorder(Constants.RED));
      er.sendButtonText("Suspend", "0.0");
    }
  }

  public void validateOutputText() {
    try {
      double value = Double.parseDouble(outputTextBox.getText());

      if (isFrequency && value >= 0.0) {
        formatDoubleFirst(value);
      } else if (!isFrequency && (value >= 0.0 && value <= 1.0)) {
        formatDoubleFirst(value);
      } else {
        throw new IOException();
      }
    } catch (NumberFormatException | IOException exception) {
      outputTextBox.setForeground(Constants.RED);
      outputTextBox.selectAll();
      outputTextBox.setBorder(new LineBorder(Constants.RED));
      er.sendButtonText("Suspend", "0.0");
    }
  }

  public void showHideBorder(JLabel clickedButton, boolean needsBorder) {
    if (needsBorder) {
      clickedButton.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
    } else {
      clickedButton.setBorder(null);
    }
  }

  @Override
  public void update(PassedData passedData) {
    buttonText = passedData.buttonText;
  }
}

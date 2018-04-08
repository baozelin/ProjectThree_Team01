package server.sys.observer;

import data.EmotivData;

/**
 * This class contains data being passed through the observer
 * 
 * @author Cephas Armstrong-Mensah
 * @version 1.0
 * @since 07APR2018
 */
public class PassedData {
  public EmotivData data;
  public String buttonText;
  public double interval;

  public PassedData(EmotivData data, String buttonText, double interval) {
    this.data = data;
    this.buttonText = buttonText;
    this.interval = interval;
  }
}

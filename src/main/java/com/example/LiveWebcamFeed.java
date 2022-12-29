package com.example;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

//import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JSlider;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class LiveWebcamFeed extends JFrame {

  private static final long serialVersionUID = 1L;
  private final JLabel lblWebcamFeed = new JLabel("");
  private JPanel contentPane;

  public static void main(String[] args) {
    LiveWebcamFeed frame = new LiveWebcamFeed();
    frame.setVisible(true);

    // Gibt die verfügbaren WEBcams aus
    // Einkommentieren ----------->
    // List<Webcam> webcams = Webcam.getWebcams();
    // // Iterate over the list of webcams and print their names
    // for (Webcam webcam : webcams) {
    // System.out.println(webcam.getName());
    // }
    // <-------------- Einkommentieren

  }

  public LiveWebcamFeed() {

    // Set up the JFrame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    setLayout(new BorderLayout());

    // Create a panel to hold the webcam image
    JPanel webcamPanel = new JPanel();
    webcamPanel.setLayout(new FlowLayout());

    webcamPanel.add(lblWebcamFeed);

    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    lblWebcamFeed.setBounds(0, 0, 450, 300);
    contentPane.add(lblWebcamFeed);

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        BufferedImage image = getWebcamImage();
        lblWebcamFeed.setIcon(new ImageIcon(image));
      }
    }, 0, 100, TimeUnit.MILLISECONDS);
  }

  private BufferedImage getWebcamImage() {
    Dimension size = WebcamResolution.VGA.getSize();
    Webcam webcam = Webcam.getDefault();
    webcam.setViewSize(size);

    webcam.open();

    return webcam.getImage();
  }
}
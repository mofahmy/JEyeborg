package com.mofahmy.JEyeborg;
import javax.swing.*;

import java.awt.*;
import java.io.File;

public class JEyeborg extends JFrame {
	
	private JPanel canvasPanel;
	private JPanel infoPanel;
	private JLabel infoLabel;
	
	public static void main(String[] args) {
		new JEyeborg();
	}
	
	public JEyeborg() {
		setTitle("JEyeborg");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		canvasPanel = new JPanel(); // Will hold the primary image
		infoPanel = createInfoPanel();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		String fileAbsolutePath = null;
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			 File selectedFile = fileChooser.getSelectedFile();
			 fileAbsolutePath = selectedFile.getAbsolutePath();
		} else {
			System.exit(1);
		}
		
		canvasPanel.setLayout(new FlowLayout());
		canvasPanel.add(new Canvas(this, fileAbsolutePath));
		
		add(canvasPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.PAGE_END);
		
		setSize(canvasPanel.WIDTH, canvasPanel.HEIGHT + 200);
		setVisible(true);
		
		pack();
	}
	
	private JPanel createInfoPanel() {
		JPanel infoPanel = new JPanel();
		
		String info = String.format("Click and drag your mouse along the image to hear its colors.");
		infoLabel = new JLabel(info);
		
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		infoPanel.add(infoLabel);
		
		return infoPanel;
	}
	
	
	public void updateInfo(int r, int g, int b, double frequency) {
		Color bgColor = new Color(r,g,b);
		Color textColor;
		
		int threshold = 170;
		if(r < threshold && g < threshold && b < threshold) {
			  textColor = Color.WHITE;
		} else {
			  textColor = Color.BLACK;
		}
		
		infoPanel.setBackground(bgColor);
		infoLabel.setForeground(textColor);
		
		String newInfo = String.format("Red: %03d Green: %03d Blue: %03d %5s Frequency: %03.3f Hz", r, g, b, " ", frequency);
		infoLabel.setText(newInfo);
	}
	
	public void setInfoError(String error) {
		infoLabel.setText("ERROR: " + error);
	}
	

}

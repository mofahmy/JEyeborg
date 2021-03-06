package com.mofahmy.JEyeborg;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Canvas extends JLabel implements MouseListener, MouseMotionListener  {
	
	private BufferedImage img;
	private BufferedImage subImg;
	
	private Cursor cursor;
	
	private SonochromaticPlayer player;
	private JEyeborg parent;
	
	public Canvas(JEyeborg parent, String pathToImage) {
		this.parent = parent;
		
		img = loadImage(pathToImage);
		if (img == null) { return; }
		
		ImageIcon icon = new ImageIcon(img);
		this.setIcon(icon);
		this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		
		setCanvasCursor();
		addMouseListener(this);
		addMouseMotionListener(this);
		
		initializeSoundPlayer();
	}
	
	private void initializeSoundPlayer() {
		player = new SonochromaticPlayer();
	}
	
	private BufferedImage loadImage(String pathToImage) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(pathToImage));
		} catch (IOException e) {
			parent.setInfoError("Failed to load specified image: " + pathToImage);
			return null;
		}
		
		return img;
	}
	
	private void setCanvasCursor() {
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.indexOf("win") > - 1) {
			System.out.println("Windows");
			cursor = new Cursor(Cursor.WAIT_CURSOR);
		} else {
			System.out.println("NOT Windows");
			cursor = Cursor.getDefaultCursor();
		}
	}
	
	public BufferedImage getCurrentSubImage() {
		return subImg;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int pixel = img.getRGB(e.getX(), e.getY());
		int[] rgb = ColorDetector.getRGB(pixel);
		int hue = ColorDetector.getHue(pixel);
		double frequency = player.getHueFrequency(hue);
		
		parent.updateInfo(rgb[0], rgb[1], rgb[2], frequency);
		player.play(hue);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setCursor(cursor);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Do nothing
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing
	}
}

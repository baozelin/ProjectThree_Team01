package client.gui;
import java.awt.MenuBar;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class creates a hamburger menu for the client application 
 *
 * @author Group 1 #001 - #013
 * @version 1.0
 * @since 2018-04-04
 *
 */

public class ClientHamburgerMenu extends JMenuBar {
	
	public ClientHamburgerMenu() {
	//JMenuBar menuBar = new JMenuBar();
	
	JMenu menu = new JMenu("MENU");
	menu.setIcon(new ImageIcon("img/icons8-menu-10.png"));
	//menuBar.add(menu);
	add(menu);
	JMenu applicationsOption = new JMenu("APPLICATION");
	menu.add(applicationsOption);
	
	JMenuItem composerOption = new JMenuItem("EMOTIV Xavier Composer");
	applicationsOption.add(composerOption);
	composerOption.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			server.gui.MainHandler serverObject = new server.gui.MainHandler();
		}
	});
	
	JMenuItem screenshotOption = new JMenuItem("Save Current Screnshot");
	applicationsOption.add(screenshotOption);
	
	JMenu connectOption = new JMenu("CONNECT");
	menu.add(connectOption);
	
	JMenuItem connectComposer = new JMenuItem("Connect Composer");
	connectComposer.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			ConnectToServerFrame connectFrame = new ConnectToServerFrame();
			connectFrame.setVisible(true);
		}
	});
	connectOption.add(connectComposer);
	
	JMenuItem reconnectComposer = new JMenuItem("Reconnect Composer");
	connectOption.add(reconnectComposer);
	
	JMenu detectionsOption = new JMenu("DETECTIONS");
	menu.add(detectionsOption);
	
	JMenuItem facialExpressions = new JMenuItem("Facial Expressions");
	detectionsOption.add(facialExpressions);
	facialExpressions.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			client.gui.EmotivControlPanel obj = EmotivControlPanel.getInstance();
			obj.showFacialGraph();
		}
	});
	
	JMenuItem performanceMetrices = new JMenuItem("Performance Metrics");
	detectionsOption.add(performanceMetrices);
	performanceMetrices.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			client.gui.EmotivControlPanel obj = EmotivControlPanel.getInstance();
			obj.showPerformanceMetric();
		}
	});
		
	JMenu helpOption = new JMenu("HELP");
	menu.add(helpOption);
	
	JMenuItem emotivOnGithub = new JMenuItem("Emotiv On Github");
	helpOption.add(emotivOnGithub);
	
	JMenuItem aboutOption = new JMenuItem("About Xavier Control Panel");
	helpOption.add(aboutOption);
}
}

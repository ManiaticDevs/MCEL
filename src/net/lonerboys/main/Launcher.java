package net.lonerboys.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.lonerboys.toolbox.JsonTool;
import net.minecraft.client.Minecraft;

public class Launcher {

	static JFrame frame = new JFrame();
	public static void main(String[] args) {
		String user = System.getProperty("user.home").replace("C:\\Users\\", "");
		
		frame.setSize(856, 480);
		
		JLabel text = new JLabel("");
		text.setBounds((856-95)/2,(480-30)-10/2, 0, 0);  
		JButton button = new JButton("Launch");
		button.setBounds((856-95)/2,(480-30)/2,95,30);  
		if(!JsonTool.GetUUIDFromName(user).contentEquals("null :[")) {
			text.setText("You are allowed to use this!");
			button.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					System.out.println(JsonTool.GetUUIDFromName(user));
					frame.setVisible(false);
					Minecraft.main(user);
				}  
			});
		} else {
			text.setText("You are not allowed to use this!");
			button.setEnabled(false);
		}
		frame.add(button,BorderLayout.CENTER);
	
		
		frame.setLayout(new BorderLayout());  
		frame.setVisible(true);
		frame.add(text,BorderLayout.CENTER);
		frame.revalidate();
	}
	
	private static void setNull() {
		frame = null;
	}
}

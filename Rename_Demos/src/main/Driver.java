package main;

import javax.swing.JOptionPane;

public class Driver 
{
	public static void main(String[] args)
	{
		try 
		{
			Demo renameDemos = new Demo();
			JOptionPane.showMessageDialog(null, "Demos have been renamed.", "Finished", JOptionPane.INFORMATION_MESSAGE);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

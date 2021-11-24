package soe;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * SOEApp - main application driver that runs the 
 *          SieveOfErastosthenes program.
 */
public class SOEApp 
{
	// Declare the SOEFrame member attribute
	private SOEFrame m_frame = null;
	
	// Program entry point
	public static void main(String[] args) 
	{
		// Create an instance of this class
		new SOEApp();
	}		
	
	public SOEApp()
	{
		// Create and open the GUI frame
		m_frame = new SOEFrame();
		
		// Add a window listen to the GUI frame to
		// cleanly exit when closed
		m_frame.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing( WindowEvent e )
            {
                System.exit( 0 );
            }
        });
	}
}

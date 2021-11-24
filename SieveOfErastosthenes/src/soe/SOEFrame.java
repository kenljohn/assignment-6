package soe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * SOEFrame - a class which extends JFrame and serves as the GUI for
 * the SieveOfErastosthenes program.
 */
public class SOEFrame extends JFrame
{
	private static final long serialVersionUID = 9027373291684078654L;

	/*
	 * Member attributes
	 */
	private final JTextField m_inputField = new JTextField();
	private final JButton m_goButton = new JButton("Find Prime Numbers");
	private final SieveOfErastosthenes m_soe = new SieveOfErastosthenes();
	
	/*
	 * SOEFrame constructor
	 */
    public SOEFrame()
    {
        try
        {
            init();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
  
    /*
     * init - builds and initializes the UI components of the GUI
     */
    private void init() throws Exception
    {
        setTitle("Sieve of Erastosthenes");

        // Setup main frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(240, 340));
        setMaximumSize(new Dimension(screenSize.width, screenSize.height));
        setPreferredSize(new Dimension(480, 680 ));
        setSize( new Dimension(480, 680));
        
        // Setup the console view
        ConsoleView consoleView = new ConsoleView();
        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new GridBagLayout());
        consolePanel.setMinimumSize(new Dimension(240, 257));
        consolePanel.setMaximumSize(new Dimension(screenSize.width, screenSize.height));
        consolePanel.setPreferredSize(new Dimension(480, 594));
        consolePanel.setBackground(consoleView.getMsgArea().getBackground());
        
        consolePanel.add(consoleView, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                0, 5, 0, 0), 0, 0));        
      
        // Setup the top/input panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setMinimumSize(new Dimension(240, 86) );
        topPanel.setMaximumSize(new Dimension(screenSize.width, 86));
        topPanel.setPreferredSize(new Dimension(480, 86) );
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        
        JLabel inputLabel = new JLabel("Enter integer (> 1):  ");
        
        m_inputField.setMinimumSize(new Dimension(80, 23) );
        m_inputField.setMaximumSize(new Dimension(80, 23));
        m_inputField.setPreferredSize(new Dimension(80, 23) );
        
        m_goButton.setMinimumSize(new Dimension(200, 30) );
        m_goButton.setMaximumSize(new Dimension(200, 30));
        m_goButton.setPreferredSize(new Dimension(200, 30) );
   
        topPanel.add(inputLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                    10, 0, 0, 0), 0, 0));        
        topPanel.add(m_inputField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                    10, 0, 0, 0), 0, 0));        
        topPanel.add(m_goButton, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                    10, 0, 10, 0), 0, 0));        
        
        // Add the top and console panels to the main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(consolePanel, BorderLayout.CENTER);
        
        // Center the GUI on the screen
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        setLocation((screenSize.width - frameSize.width) / 2,
            (screenSize.height - frameSize.height) / 2);
        
        // Define callback methods before displaying the GUI
        defineCallbacks();
        
        // Display the GUI
        setVisible(true);
    }
    
    /*
     * defineCallbacks - private method that defines event callbacks and
     * listeners for event handling
     */
    private void defineCallbacks()
    {
    	// Call the findPrimes method when the 'go' button is pressed
    	m_goButton.addActionListener(new ActionListener()
    	{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				findPrimes();
			}
    	});
    	
    	// Call the findPrimes method when the enter key is pressed
    	// while the input field has focus
    	m_inputField.addActionListener(new ActionListener()
    	{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				findPrimes();
			}
    	});
    }
    
    /*
     * findPrimes - retrieves the value from the input field and
     * attempts to convert to an integer. If the conversion fails 
     * or the resulting integer value is less than 2, an error is 
     * written to stdout. Otherwise, the integer value is passed
     * to the SieveOfErastosthenes object to identify and output
     * the prime numbers for the input.
     */
    private void findPrimes()
    {
		try 
		{
			int limit = Integer.parseInt( m_inputField.getText() );
			
			if (limit > 1)
				m_soe.findPrimes(limit);
			else
				System.out.println("Expected: Integer > 1");
		} 
		catch (NumberFormatException e) 
		{
			System.out.println("Expected: Integer > 1");
		} 
    }
}
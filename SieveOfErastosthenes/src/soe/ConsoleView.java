package soe;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * ConsoleView - a UI panel that displays data written to stdout and
 * stderr in a JTextArea component. 
 */
public class ConsoleView extends JPanel 
{
	private static final long serialVersionUID = -3597395891764465896L;
	
	// Data members
    protected JTextArea m_msgArea = new JTextArea();
    private final JScrollPane m_scrollPane = new JScrollPane();
    private final DualPrintStream outStream = new DualPrintStream(System.out);
    private final DualPrintStream errStream = new DualPrintStream(System.err);
    private final JPopupMenu m_popupMenu = new JPopupMenu();
    private final JMenuItem m_clearMenuItem = new JMenuItem("Clear");

    /*
     * ConsoleView constructor 
     */
    public ConsoleView()
    {
        try
        {
            init();
            defineCallbacks();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * init - initializes members and UI components
     */
    private void init() throws Exception
    {
        setLayout(new BorderLayout());
        System.setOut(new PrintStream(outStream, true ));
        System.setErr(new PrintStream( errStream, true ));
        m_msgArea.setFont(new Font("Courier New", 0, 12 ));
        m_msgArea.setEditable(false);
        m_popupMenu.add( m_clearMenuItem );
        m_scrollPane.setBorder(BorderFactory.createEmptyBorder());
        m_scrollPane.getViewport().add(m_msgArea, (Object) null);
        add(m_scrollPane, BorderLayout.CENTER );
    }

    /*
     * getMsgArea - provides access to the JTextArea member so that
     * it can be included in the larger UI
     */
    public JTextArea getMsgArea()
    {
        return m_msgArea;
    }

    /*
     * defineCallbacks - private method that defines event callbacks and
     * listeners for event handling
     */
    private void defineCallbacks()
    {
        m_msgArea.addMouseListener( new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				// Display popup menu on right mouse button click
		        if ( SwingUtilities.isRightMouseButton( e ) )
		            m_popupMenu.show( e.getComponent(), e.getX(), e.getY() );
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
	
		});
        
        // Set caret position in document when text area's internal
        // document is updated
        m_msgArea.getDocument().addDocumentListener( new DocumentListener()
        {
            @Override
            public void changedUpdate( DocumentEvent e )
            {
                m_msgArea.setCaretPosition(
                    m_msgArea.getDocument().getLength() );
            }

            @Override
            public void insertUpdate( DocumentEvent e )
            {
                m_msgArea.setCaretPosition(
                    m_msgArea.getDocument().getLength() );
            }

            @Override
            public void removeUpdate( DocumentEvent e )
            {
                m_msgArea.setCaretPosition(
                    m_msgArea.getDocument().getLength() );
            }
        });
        
        // Clear the text area when the 'clear menu' context menu 
        // option is selected
        m_clearMenuItem.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent ae )
            {
                clearText();
            }
        });
    }

    /*
     * clearText - clears the contents of text area
     */
    private void clearText()
    {
        m_msgArea.setText( "" );
        m_msgArea.setCaretPosition( 0 );
    }

    /*
     * DualPrintStream class - class that overrides PrintStream to enable 
     * stdout and stderr to be written to the text area
     */
    class DualPrintStream extends PrintStream
    {
        DualPrintStream( PrintStream ps )
        {
            super( ps );
        }

        /*
         * write(byte) - writes an array of bytes to the text area
         */
        @Override
        public void write( byte[] b )
        {
            try
            {
                String tmpString = new String( b );
                m_msgArea.append( tmpString );
                super.write( b );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                setError();
            }
        }

        /*
         * write(byte) - writes a specified segment of an array of bytes 
         * to the text area
         */
        @Override
        public void write( byte[] buf, int off, int len )
        {
            try
            {
                String tmpString = new String( buf, off, len );
                m_msgArea.append( tmpString );
                super.write( buf, off, len );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                setError();
            }
        }
    }
}

import javax.swing.*;
public class Driver
{
    public static void main(String[] args)
    {
        Input i = new Input();
        i.start();
        JFrame frame = new JFrame( "SHOOTING GAME" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocation( 460, 240 );
        frame.setVisible( true );
        Board b = new Board();
        frame.getContentPane().add( b );
        frame.pack();
        b.go();
    }
}
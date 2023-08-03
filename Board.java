import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.io.*;
import java.awt.image.*;
public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener, ActionListener
{
    //instance variable
    //create a reference for a Square
    private Square[] squares;
    private int count;
    private Sound kill;
    private int timer;
    private JLabel timeTracker;
    private JLabel lifeTracker;
    private JLabel gunstatus;
    private JLabel ending;
    private JLabel remaining;
    private int shot;
    private int deadcount;
    private boolean killwin = false, done = false;
    private Image image;
    private JButton restart;
    private boolean offscreen = false;
    private int mouseX;
    private int mouseY;
    //constructor
    public Board()
    {
        this.setLayout( null );
        this.setPreferredSize( new Dimension( 1000, 600 ) );
        try
        {
            this.getFileImage( "therangenew.jpg" );
        }catch( Exception e ){}

        this.setBackground( new Color( 135, 206, 235 ) );
        count = 10;
        squares = new Square[count];
        for( int i = 0; i < squares.length; i++ )
        {
            squares[i] = new Square( i, squares );
        }
        //create a Player object;

        kill = new Sound( "ABShot.wav" );

        //create a JLabel object
        timeTracker = new JLabel( "Timer: " + timer );
        this.add(timeTracker);
        timeTracker.setBounds(10,10,480,50);
        timeTracker.setFont(new Font("Serif",Font.BOLD,48));
        timeTracker.setForeground(Color.WHITE);
        timeTracker.setVisible(true);

        gunstatus = new JLabel( "" );
        //gunstatus = new JLabel( "GUN: READY" );
        this.add(gunstatus);
        gunstatus.setBounds(10,550,480,50);
        gunstatus.setFont(new Font("Serif",Font.BOLD,34));
        gunstatus.setForeground(Color.GREEN);
        gunstatus.setHorizontalAlignment(SwingConstants.LEFT);

        remaining = new JLabel( "REMAINING: " + ( count - deadcount ) );
        this.add(remaining);
        remaining.setBounds(510,550,480,50);
        remaining.setFont(new Font("Serif",Font.BOLD,34));
        remaining.setForeground(Color.WHITE);
        remaining.setHorizontalAlignment(SwingConstants.RIGHT);

        ending = new JLabel();
        this.add(ending);
        ending.setBounds(0,250,1000,100);
        ending.setFont(new Font("Arial",Font.BOLD,65));
        ending.setForeground(Color.WHITE);
        ending.setHorizontalAlignment(SwingConstants.CENTER);
        ending.setVisible(false);

        restart = new JButton( "Go Again?" );
        this.add( restart );
        restart.setBounds( 400, 400, 200, 100 );
        restart.setVisible( false );

        //create a keyListener
        this.addKeyListener(this);
        restart.addActionListener( this );
        this.addMouseListener( this );
        this.addMouseMotionListener( this );
        this.setFocusable(true);
    }

    public void actionPerformed( ActionEvent event )
    {
        JButton temp = (JButton)event.getSource();
        if( temp == restart )
        {
            squares = new Square[count];
            for( int i = 0; i < squares.length; i++ )
            {
                squares[i] = new Square( i, squares );
            }
            timer = 0;
            remaining.setText( "REMAINING: " + count );
            killwin = false;
            done = false;
            shot = 0;
            this.repaint();
            this.requestFocusInWindow();
            restart.setVisible( false );
            ending.setVisible( false );
        }
    }

    private void getFileImage( String fileName ) throws InterruptedException, IOException
    {
        FileInputStream in = new FileInputStream( fileName );
        byte[] b = new byte[in.available()];
        in.read( b );
        in.close();
        image = Toolkit.getDefaultToolkit().createImage( b );
        MediaTracker mt = new MediaTracker( this );
        mt.addImage( image, 0 );
        mt.waitForAll();
    }

    public void go()
    {
        while( true )
        {
            while( !done )
            {
                try
                {
                    //500?
                    Thread.sleep( 150 );
                    timer += 150;
                }catch( InterruptedException ex ){}
                for( int i = 0; i < squares.length; i++ )
                {
                    boolean noWin = false;
                    if( squares[i].alive() == false )
                    {
                        deadcount++;
                        remaining.setText( "REMAINING: " + ( count - deadcount ) );
                        if( squares[i].shrunk() )
                        {
                            noWin = true;
                        }
                    }
                    if( deadcount == count )
                    {
                        done = true;
                        if( !noWin )
                        {
                            killwin = true;
                        }
                    }
                    squares[i].shrink();
                }
                deadcount = 0;
                if( timer - shot >= 500 && offscreen == false )
                {
                    gunstatus.setForeground(Color.GREEN);
                    //gunstatus.setText( "GUN: READY" );
                }
                try
                {
                    //causes the program to pause for 5 milliseconds
                    Thread.sleep( 5 );
                    timer += 5;
                    timeTracker.setText( "Timer: " + (double)timer/1000 );
                }catch( InterruptedException ex ){}
                //updates the screen
                this.repaint();
                this.requestFocusInWindow();
            }
            if( killwin )
            {
                ending.setText( "YOU WIN! YOUR TIME IS: " + (double)timer/1000 );
            }
            else
            {
                ending.setText( "YOU LOST!" );
            }
            ending.setVisible( true );
            restart.setVisible( true );
        }
    }

    public void mouseMoved( MouseEvent event )
    {}

    public void mouseDragged( MouseEvent event )
    {}

    //never use this one
    public void mouseClicked( MouseEvent event )
    {}

    public void mousePressed( MouseEvent event )
    {
        int mX = event.getX();
        int mY = event.getY();
        for( int i = 0; i < squares.length; i++ )
        {
            if( squares[i].alive() && squares[i].isHit( mX, mY ) )
            {
                squares[i].die();
                kill.play();
                this.repaint();
                this.requestFocusInWindow();
            }
        }
        /*if( timer - shot >= 500 )
        {
        mouseX = event.getX();
        mouseY = event.getY();
        shot = timer;
        gunstatus.setForeground(Color.ORANGE);
        gunstatus.setText( "GUN: RELOADING" );
        }*/ 
    }

    public void mouseReleased( MouseEvent event )
    {}

    public void mouseEntered( MouseEvent event )
    {
        gunstatus.setForeground(Color.GREEN);
        //gunstatus.setText( "GUN: READY" );
        offscreen = false;
    }

    public void mouseExited( MouseEvent event )
    {
        gunstatus.setForeground(Color.RED);
        //gunstatus.setText( "GUN: INACTIVE" );
        offscreen = true;
    }

    public void keyTyped( KeyEvent event )
    {}

    public void keyPressed( KeyEvent event )
    {}

    public void keyReleased( KeyEvent event )
    {}

    //update the shapes on the Board
    public void paintComponent( Graphics page )
    {
        //clears the Board of previous shapes
        super.paintComponent( page );
        page.drawImage( image, 0, 0, this );
        //draws the square
        for( int i = 0; i < squares.length; i++ )
        {
            squares[i].draw( page );
        }
    }

    public static int collide( Square a, Square b )
    {
        if(a.getRight()>=b.getLeft()&&a.getLeft()<=b.getRight()
        &&a.getBottom()>=b.getTop()&&a.getTop()<=b.getBottom())
        {
            //a collision happened
            //find the smallest x difference
            int xDiff=Math.abs(a.getLeft()-b.getRight());
            if(Math.abs(a.getRight()-b.getLeft())<xDiff)
            {
                xDiff=Math.abs(a.getRight()-b.getLeft());
            }
            //find the smallest y difference
            int yDiff=Math.abs(a.getTop()-b.getBottom());
            if(Math.abs(a.getBottom()-b.getTop())<yDiff)
            {
                yDiff=Math.abs(a.getBottom()-b.getTop());
            }
            //determine direction of collision
            if( xDiff < yDiff )
            {
                return 1; //horizontal collision
            }
            return 2; //vertical collision
        }
        return 0;
    }

    public boolean shot( int x, int y, int bx, int by, int r )
    {
        if( Math.sqrt( ( x - bx ) * (x - bx ) + ( y - by ) * ( y - by ) ) <= r + 10 )
        {
            return true;
        }
        return false;
    }
}
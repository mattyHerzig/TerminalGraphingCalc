import java.awt.*;
import java.lang.Math.*;
public class Square
{
    //instance variables for position and size
    private int x;
    private int y;
    private int size;
    private Color color;
    private boolean alive = true;
    private boolean shrunk = false;

    //constructor to initialize instance variables
    public Square( int index, Square[] squares )
    {
        boolean viable = false;
        size = 75;
        color = new Color( 255, 0, 0 );
        x = (int)(Math.random()*701) + 100;
        y = (int)(Math.random()*301) + 100;
        for( int i = 0; i < squares.length && i != index; i++ )
        {
            while( Board.collide( this, squares[i] ) > 0 )
            {
                x = (int)(Math.random()*701) + 100;
                y = (int)(Math.random()*301) + 100;
            }
        }
    }

    public boolean isHit( int mX, int mY )
    {
        if( Math.sqrt( ( getCenterX() - mX ) * ( getCenterX() - mX ) + ( getCenterY() - mY ) * ( getCenterY() - mY ) ) <= size / 2 )
        {
            return true;
        }
        return false;
    }

    public int getCenterX()
    {
        return x + size/2;
    }

    public int getCenterY()
    {
        return y + size/2;
    }

    public int getRadius()
    {
        return size/2;
    }

    public int getLeft()
    {
        return x;
    }

    public int getRight()
    {
        return x + size;
    }

    public int getTop()
    {
        return y;
    }

    public int getBottom()
    {
        return y + size;
    }

    public void die()
    {
        alive = false;
        x = -size - 10;
        y = -size - 10;
    }

    public boolean alive()
    {
        return alive;
    }

    public boolean shrunk()
    {
        return shrunk;
    }
    
    public void shrink()
    {
        if( alive )
        {
            size -= 2;
            x++;
            y++;
            if( size <= 0 )
            {
                die();
                shrunk = true;
            }
        }
    }

    public void draw( Graphics page )
    {
        page.setColor( color );
        page.fillOval( x, y, size, size );
    }
}
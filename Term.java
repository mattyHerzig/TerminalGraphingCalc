import java.util.*;
public class Term
{
    private String t, innerFunction;
    private int ce = 1;
    private boolean sign;
    //private boolean inner;

    public Term( String s, boolean negative )
    {
        t = s;
        sign = !negative;
        /*if( t.indexOf( "^1" ) == t.length() - 2 && t != "0" )
        {
        t += "^1";
        }*/
        /*if( s.indexOf( "(" ) != -1 )
        {
        inner = true;
        innerFunction = t.substring( t.indexOf( "(" ) + 1, t.indexOf( ")" ) );
        ce = Integer.parseInt( t.substring( 0, t.indexOf( "(" ) ) );
        }
        else */if ( s.indexOf( "x" ) != - 1 )
        {
            if( !t.substring( 0, t.indexOf( "x" ) ).equals( "" ) )
            {
                ce = Integer.parseInt( t.substring( 0, t.indexOf( "x" ) ) );
                t = t.substring( t.indexOf( "x" ) );
            }
        }
        else if ( !s.equals( "" ) )
        {
            ce = Integer.parseInt( s );
        }
    }

    public boolean isPositive()
    {
        return sign;
    }

    public String toString()
    {
        if( sign )
        {
            return ce + t;
        }
        return "-" + ce + t;
    }

    public String getTerm()
    {
        return t;
    }
    
    public int getCoefficient()
    {
        return ce;
    }
    
    public String setTermDerivative()
    {
        if( t.indexOf( "x" ) == -1 )
        {
            ce = 0;
            t = "";
        }
        else if( t.indexOf( "^" ) != -1 )
        {
            int ex = Integer.parseInt( t.substring( t.indexOf( "^" ) + 1 ) );
            if( ex == 1 )
            {
                t = "";
            }
            else
            {
                ce *= ex;
                t = t.substring( t.indexOf( "x" ), t.indexOf( "^" ) + 1 ) + ( ex - 1 );
            }
        }
        else if( t.indexOf( "x" ) != -1 )
        {
            if( sign )
            {
                t = "";
                return new Integer(ce).toString();
            }
            t = "";
            return "-" + new Integer(ce).toString();
        }
        /*if( inner )
        {
        Calculator c = new Calculator( innerFunction );
        if( sign )
        {
        return ce + "(" + c.getDerivative() + ")" + t;
        }
        return "-" + ce + "(" + c.getDerivative() + ")" + t;
        }*/
        if( sign )
        {
            return ce + t;
        }
        return "-" + ce + t;
    }
}
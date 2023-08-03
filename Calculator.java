import java.util.*;
import java.lang.Math.*;
public class Calculator
{
    private ArrayList<Term> terms = new ArrayList<Term>(); //put in GetDerivative?
    private String[][] graph;
    private String f;

    public Calculator( String t )
    {
        f = t;
        terms = seperateTerms( f );
    }

    public String getDerivative()
    {
        String a = "";
        for( int i = 0; i < terms.size(); i++ )
        {
            String term = terms.get(i).setTermDerivative();
            if( term.indexOf( "^1" ) == term.length() - 2 && term != "0" && term.length() - 2 > 0 )
            {
                a += "+" + term.substring( 0, term.length() - 2 );
            }
            else
            {
                a += "+" + term;
            }
        }
        a = a.substring( 1 );
        while( a.indexOf( "+-" ) != -1 )
        {
            a = a.substring( 0, a.indexOf( "+-" ) ) + "-" + a.substring( a.indexOf( "+-" ) + 2 );
        }
        if( a.indexOf( "+0" ) != -1 || a.indexOf( "-0" ) != -1 )
        {
            while( a.indexOf( "+0" ) != -1 )
            {
                a = a.substring( 0, a.indexOf( "+0" ) ) + a.substring( a.indexOf( "+0" ) + 2 );
            }
            while( a.indexOf( "-0" ) != -1 )
            {
                a = a.substring( 0, a.indexOf( "-0" ) ) + a.substring( a.indexOf( "-0" ) + 2 );
            }
        }
        if( a.equals( "" ) )
        {
            return "0";
        }
        return f=a;
    }

    private static int countSigns( String s )
    {
        int count = 0; //remove everything inside () too
        while( s.indexOf( "+" ) != -1 )
        {
            s = s.substring( 0, s.indexOf( "+" ) ) + s.substring( s.indexOf( "+" ) + 1 );
            count++;
        }
        while( s.indexOf( "-" ) != -1 )
        {
            s = s.substring( 0, s.indexOf( "-" ) ) + s.substring( s.indexOf( "-" ) + 1 );
            count++;
        }
        return count;
    }

    public int insertX( int x )
    {
        int s = 0;
        for( int i = 0; i < terms.size(); i++ )
        {            
            int caret = terms.get(i).getTerm().indexOf( "^" );
            int to;
            if( caret == -1 )
            {
                if( terms.get(i).getTerm().indexOf( "x" ) != -1 )
                {
                    to = terms.get(i).getCoefficient() * x;
                }
                else
                {
                    to = terms.get(i).getCoefficient();
                }
            }
            else
            {
                int ex = Integer.parseInt( terms.get(i).getTerm().substring( caret + 1 ) );
                to = terms.get(i).getCoefficient() * (int)Math.pow( x, ex );
            }
            if( terms.get(i).isPositive() )
            {
                s += to;
            }
            else
            {
                s -= to;
            }
        }
        return s;
    }

    public static ArrayList<Term> seperateTerms( String s )
    {
        ArrayList<Term> list = new ArrayList<Term>();
        while( s.indexOf( " " ) != -1 )
        {
            s = s.substring( 0, s.indexOf( " " ) ) + s.substring( s.indexOf( " " ) + 1 );
        }
        while( s.indexOf( "(" ) != -1 )
        {
            int index = s.indexOf( "(" );
            boolean looking = true;
            while( looking )
            {
                int open = 0;
                int close = 0;
                index++;
                if( s.substring( index, index + 1 ).equals( ")" ) )
                {
                    open++;
                }
                else if( s.substring( index, index + 1 ).equals( ")" ) )
                {
                    close++;
                }
                if( open == close + 1 && s.substring( index, index + 1 ).equals( ")" ) )
                {
                    looking = false;
                }
            }
            s = s.substring( 0, s.indexOf( "(" ) ) + s.substring( index + 1 );
        }
        if( s.indexOf( "+" ) == -1 && s.indexOf( "-" ) == -1 )
        {
            list.add( new Term( s, false) );
            s = "";
        }
        while( !s.equals( "" ) )
        {
            if( countSigns( s ) == 0 )
            {
                list.add( new Term( s, false ) );
                s = "";
            }
            int index = Integer.MAX_VALUE;
            int plus = s.indexOf( "+" );
            int minus = s.indexOf( "-" );
            if( plus != -1 && minus == -1 )
            {
                index = plus;
            }
            else if( minus != -1 && plus == -1 )
            {
                index = minus;
            }
            else if( plus != -1 && minus != -1 )
            {
                if( plus < minus )
                {
                    index = plus;
                }
                else
                {
                    index = minus;
                }
            }
            if( index == plus )
            { 
                list.add( new Term( s.substring( 0, s.indexOf( "+" ) ), false ) );
                s = s.substring( s.indexOf( "+" ) + 1 ); 
            }
            else if( countSigns( s ) == 1 && minus == 0 )
            {
                list.add( new Term( s.substring( 1 ), true ) );
                s = "";
            }
            else if ( minus == 0 )
            {
                int secondPlus = s.substring( 1 ).indexOf( "+" );
                int secondMinus = s.substring( 1 ).indexOf( "-" );
                if( secondPlus != -1 )
                {
                    secondPlus++;
                }
                if( secondMinus != -1 )
                {
                    secondMinus++;
                }
                if( secondPlus != -1 && secondMinus != -1 && secondPlus < secondMinus )
                {
                    list.add( new Term( s.substring( 1, secondPlus ), true ) );
                    s = s.substring( secondPlus + 1 );
                }
                else if( secondPlus != -1 && secondMinus != -1 )
                {
                    list.add( new Term( s.substring( 1, secondMinus ), true ) );
                    s = s.substring( secondMinus ); //FIXED? or "-1" required...
                }
                else if( secondPlus != -1 )
                {
                    list.add( new Term( s.substring( 1, secondPlus ), true ) );
                    s = s.substring( secondPlus + 1 );
                }
                else
                {
                    list.add( new Term( s.substring( 1, secondMinus ), true ) );
                    s = s.substring( secondMinus + 1 );
                }
            }
            else if( index == minus && minus != 0 )
            {
                list.add( new Term( s.substring( 0, s.indexOf( "-" ) ), false ) );
                s = s.substring( s.indexOf( "-" ) ); 
            }
        }
        return list;
    }

    public void graph( int s ) //parameter should be odd
    {
        System.out.println();
        graph = new String[s][s];
        int max = Integer.MIN_VALUE;
        int maxdig = 0;
        int[] yvalues = new int[s];
        for( int i = 0; i < s; i++ ) //GETS Y-VALUES
        {
            int x = i - s / 2;
            int y = insertX( x );
            if( Math.abs( y ) > max )
            {
                max = Math.abs( y );
            }
            if( Integer.toString( x ).length() > maxdig )
            {
                maxdig = Integer.toString( x ).length();
            }
            if( Integer.toString( y ).length() > maxdig )
            {
                if( y < 0 )
                {
                    maxdig = Integer.toString( y ).length();
                }
                else
                {
                    maxdig = Integer.toString( y ).length() + 1;
                }
            }
            if( y > 0 && Integer.toString( y ).length() == maxdig )
            {
                maxdig++;
            }
            yvalues[i] = y;
        }
        String[] slopes = new String[s];
        for( int i = 1; i < s - 1; i++ )
        {
            double slope = ( yvalues[i+1] - yvalues[i-1] ) / 2.0;
            String slopesign;
            if( slope > 0 )
            {
                slopesign = "/";
            }
            else if( slope == 0 )
            {
                slopesign = "-";
            }
            else
            {
                slopesign = "\\";
            }
            slopes[i] = slopesign;
            if( i == 1 )
            {
                slopes[0] = slopesign;
            }
            if( i == s - 2 )
            {
                slopes[s-1] = slopesign;
            }
        }
        for( int c = 0; c < s; c++ ) //MAKES THE GRAPH
        {
            int x = c - s / 2;
            int y = insertX( x );
            boolean spot = false;
            for( int r = 0; r < s; r++ )
            {
                int ay = (int)(( ( s / 2 - r ) / (double)( s / 2 ) ) * max ); //IT WORKED
                //watch for out of bounds
                int space = maxdig;
                String point = "";
                if( s/2 == r )
                {
                    point = Integer.toString( x );
                    space = maxdig - point.length();
                }
                if( s/2 == c )
                {
                    point = Integer.toString( ay );
                    space = maxdig - point.length();
                }
                if( y >= ay && !spot )
                {
                    point = slopes[c];
                    spot = true;
                    space = maxdig - 1;
                }
                for( int i = 0; i < space; i++ )
                {
                    point += " ";
                }
                graph[r][c] = point;
                if( y >= ay && !spot )
                {
                    r = s;
                }
            }
        }
        for( int r = 0; r < s; r++ ) //PRINTS THE GRAPH
        {
            for( int c = 0; c < s; c++ )
            {
                System.out.print( graph[r][c] );
            }
            System.out.println();
        }
        System.out.println();
    }

    /*public static int digitCount( int s )
    {
    int i = 0;
    while ( s > 0 ) //FIX
    {
    i++;
    s /= 10 ;
    }
    return i;
    }*/
}
import java.util.Scanner;
public class Input
{
    public void start()
    {
        boolean again = true;
        int d = 0;
        Scanner in = new Scanner( System.in );
        System.out.println( "Give me a polynomial, f(x)" );
        String f = in.nextLine();
        System.out.println();
        Calculator c = new Calculator( f );
        while( again )
        {
            System.out.println( "What would you like to do?" );
            System.out.println( "1 - View the rules" );
            System.out.println( "2 - Differentiate it" );
            /*System.out.println( "3 - Graph" );
            System.out.println( "4 - New f(x)" );*/
            System.out.println( "3 - Insert x-value" );
            System.out.println( "4 - Graph f^(" + d + ")(x)" );
            System.out.println( "5 - Procrastinate\n" );
            int a = in.nextInt();
            System.out.println();
            if( a == 1 )
            {
                System.out.println( "Please use integers" );
                System.out.println( "Please order polynomials from highest to smallest degree\n" );
            }
            if( a == 2 )
            {
                d++;
                System.out.println( "f^(" + d + ")(x) = " + c.getDerivative() + "\n" ); 
            }
            if( a == 3 )
            {
                System.out.println( "Give me x\n" );
                int x = in.nextInt();
                System.out.println( "f^(" + d + ")(" + x + ") = " + c.insertX( x ) + "\n" ); 
            }
            if( a == 4 )
            {
                System.out.println( "Give me the domain ( e.g. \"5\" => [-5,5] )\n" );
                int domain = in.nextInt();
                c.graph( domain * 2 + 1 );
            }
            if( a == 5 )
            {
                again = false;
            }
        } 
    }
}
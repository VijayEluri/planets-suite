/**
 * 
 */
package odfvalidator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import eu.planets_project.services.datatypes.DigitalObject;
import eu.planets_project.services.validate.ValidateResult;

/**
 * @author <a href="mailto:Andrew.Jackson@bl.uk">Andy Jackson</a>
 *
 */
public class ODFValidatorWrapper {
    Main main = new Main();
    
    /**
     * main method
     * @param aArgs
     */
    public static void main(String[] aArgs) {
        
        String aConfigFileName = null;
        String aFilterFileName = null;
        String aSchemaFileName = null;
        String aOutputFileName = null;
        String aExcludeRegExp = null;
        boolean bPrintGenerator = false;
        boolean bUseMathDTD = false;
        boolean bPrintHelp = false;
        boolean bPrintVersion = false;
        boolean bRecursive = false;
        int nLogLevel = Logger.ERROR;
        int nMode = ODFPackageValidator.VALIDATE;
        List<String> aFileNames = new Vector<String>();
        String aVersion = null;

        boolean bCommandLineValid = true;
        List<String> aArgList = Arrays.asList(aArgs);
        Iterator<String> aArgIter = aArgList.iterator();
        while( aArgIter.hasNext() && bCommandLineValid )
        {
            String aArg = aArgIter.next();
            if( aArg.equals("-c") )
            {
                nMode = ODFPackageValidator.CHECK_CONFORMANCE;
            }
            else if( aArg.equals("-d") )
            {
                bUseMathDTD = true;
            }
            else if( aArg.equals("-f") )
            {
                if( aArgIter.hasNext() )
                    aFilterFileName = aArgIter.next();
                else
                    bCommandLineValid = false;
            }
            else if( aArg.equals("-g") )
            {
                bPrintGenerator = true;
            }
            else if( aArg.equals("-h") )
            {
                bPrintHelp = true;
            }
            else if( aArg.equals("-o") )
            {
                if( aArgIter.hasNext() )
                    aOutputFileName = aArgIter.next();
                else
                    bCommandLineValid = false;
            }
            else if( aArg.equals("-r") )
            {
                bRecursive = true;
            }
            else if( aArg.equals("-s") )
            {
                nMode = ODFPackageValidator.VALIDATE_STRICT;
            }
            else if( aArg.equals("-v") )
            {
                nLogLevel = Logger.INFO;
            }
            else if( aArg.equals("-w") )
            {
                nLogLevel = Logger.WARNING;
            }
            else if( aArg.equals("-x") )
            {
                if( aArgIter.hasNext() )
                    aExcludeRegExp = aArgIter.next();
                else
                    bCommandLineValid = false;
            }
            else if( aArg.equals("-C") )
            {
                if( aArgIter.hasNext() )
                    aConfigFileName = aArgIter.next();
                else
                    bCommandLineValid = false;
            }
            else if( aArg.equals("-S") )
            {
                if( aArgIter.hasNext() )
                    aSchemaFileName = aArgIter.next();
                else
                    bCommandLineValid = false;
            }
            else if( aArg.equals("-V") )
            {
                bPrintVersion = true;
            }
            else if( aArg.equals("-1.0") || aArg.equals("-1.1") || aArg.equals("-1.2") )
            {
                aVersion = aArg.substring(1);
            }
            else if( aArg.startsWith("-") )
            {
                System.out.print(aArg);
                System.out.println(": unknown option, use '-' for help");
                return;
            }
            else
            {
                aFileNames.add( aArg );
            }
        }

        // check usage
        if( bPrintHelp || bPrintVersion )
        {
            bCommandLineValid = true;
        }
        else if( bPrintGenerator )
        {
            bCommandLineValid = aFileNames.size() > 0;
        }
        else if( aConfigFileName != null )
        {
            bCommandLineValid = aConfigFileName.length() > 0;
        }
        else
        {
            bCommandLineValid = aFileNames.size() > 0;
        }

        // print help
        if( !bCommandLineValid || bPrintHelp )
        {
            printUsage();
            return;
        }
        if( bPrintVersion )
        {
            System.out.print("odfvalidator v");
//            System.out.println( VERSION );
            return;
        }
        
        try
        {
            // Print generator (does not require config file)
            if( bPrintGenerator ) 
            {
                MetaInformation aMetaInformation = new MetaInformation( System.out );
                Iterator<String> aIter = aFileNames.iterator();
                while( aIter.hasNext() )
                    aMetaInformation.getInformation(aIter.next());
                return;
            }
            
            // Read configuration
            Configuration aConfig = null;
            if( aConfigFileName != null )
            {
                File aConfigFile = new File( aConfigFileName );
                try
                {
                    aConfig = new Configuration( aConfigFile );
                }
                catch( FileNotFoundException e )
                {
                    if( aConfigFileName != null )
                    {
                        System.out.println( aConfigFile.getAbsolutePath() + ": file not found.");
                        return;
                    }
                }
                catch( IOException e )
                {
                    System.out.println("error reading " + aConfigFile.getAbsolutePath() + ": " + e.getLocalizedMessage() );
                    return;
                }
            }
            
            if( aSchemaFileName != null )
            {
                aConfig = new Configuration();
                aConfig.setProperty( Configuration.STRICT_SCHEMA, aSchemaFileName );
            }

            PrintStream aOut = aOutputFileName != null ? new PrintStream( aOutputFileName ) : System.out;
            ODFValidator aValidator = new ODFValidator( aConfig, nLogLevel, aVersion, bUseMathDTD );

            if( aConfigFileName != null )
            {
                aValidator.validate(aOut, aConfig, nMode );
            }
            else
            {
                aValidator.validate(aOut, aFileNames, aExcludeRegExp, nMode, bRecursive, aFilterFileName );
            }
        }
        catch( ODFValidatorException e )
        {
            System.out.println( e.getMessage() );
            System.out.println( "Validation aborted." );
        }
        catch( FileNotFoundException e )
        {
            System.out.println( e.getMessage() );
            System.out.println( "Validation aborted." );
        }
    }
    
    /**
     * @param dob
     * @return the result of the validation of the ODF file
     */
    public static ValidateResult validateODF( DigitalObject dob ) {
        
        Configuration aConfig = null;
        String aVersion = null;
        /*
         *             else if( aArg.equals("-1.0") || aArg.equals("-1.1") || aArg.equals("-1.2") )
            {
                aVersion = aArg.substring(1);
            }
         */
        boolean bUseMathDTD = false;
        int nMode = ODFPackageValidator.VALIDATE;
        // nMode = ODFPackageValidator.CHECK_CONFORMANCE;
        String aBaseURI = null;
       
        try
        {

            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            //PrintStream aOut = aOutputFileName != null ? new PrintStream( aOutputFileName ) : System.out;
            PrintStream aOut = new PrintStream(buf);
            
            ODFValidator aValidator = new ODFValidator( aConfig, Logger.INFO, aVersion, bUseMathDTD );

            //aValidator.validate(aOut, aFileNames, aExcludeRegExp, nMode, bRecursive, aFilterFileName );
            aValidator.validateStream(aOut, dob.getContent().read(), aBaseURI , nMode, null);
        }
        catch( ODFValidatorException e )
        {
            System.out.println( e.getMessage() );
            System.out.println( "Validation aborted." );
        }
        
        return null;
    }
        
    private static void printUsage()  
    {
        System.out.println( "usage: odfvalidator -g <odffiles>");  
        System.out.println( "       odfvalidator [-r] [-c|-s] [-d] [-v|-w] [-f <filterfile>] [-x <regexp>] [-o outputfile] [-1.0|-1.1|-1.2] <odffiles>");  
        System.out.println( "       odfvalidator [-r] [-c|-s] [-d] [-v|-w] [-f <filterfile>] [-x <regexp>] [-o outputfile] -S <schemafile> <odffiles>");  
        System.out.println( "       odfvalidator [-c|-s] [-v|-w] [-d] [-o outputfile] -C <configfile>");  
        System.out.println( "       odfvalidator -h");  
        System.out.println( "       odfvalidator -V");  
        System.out.println();  
        System.out.println( "-C: Validate using configuration file <configfile>" );  
        System.out.println( "-S: Use schema <schemafile> for validation" );  
        System.out.println( "-V: Print version" );  
        System.out.println( "-c: Check conformance" );  
        System.out.println( "-d: Use MathML DTD rather than MathML2 schema for validation" );  
        System.out.println( "-f: Use filterfile <filterfile>" );  
        System.out.println( "-g: Show <odffiles> generators and exit" );  
        System.out.println( "-h: Print this help and exit" );  
        System.out.println( "-o: Store validation errors in <outputfile>" );  
        System.out.println( "-r: Process directory recursivly" );  
        System.out.println( "-s: Validate against strict schema" );  
        System.out.println( "-v: Verbose output, including generator and warnings" );  
        System.out.println( "-w: Print warnings" );  
        System.out.println( "-x: Exclude paths that match <regexp>" );  
        System.out.println();  
        System.out.println( "If no option is provided, <odffiles> are validated using <schemafile>" );  
    }  
  
}

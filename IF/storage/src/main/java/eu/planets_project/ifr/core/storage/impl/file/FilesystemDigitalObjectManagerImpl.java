/**
 * 
 */
package eu.planets_project.ifr.core.storage.impl.file;

import eu.planets_project.ifr.core.storage.api.DigitalObjectManager;
import eu.planets_project.ifr.core.storage.api.PDURI;
import eu.planets_project.ifr.core.storage.api.query.Query;
import eu.planets_project.ifr.core.storage.api.query.QueryValidationException;
import eu.planets_project.services.datatypes.DigitalObjectContent;
import eu.planets_project.services.datatypes.DigitalObject;
import eu.planets_project.services.datatypes.Content;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the DigitalObjectManager interface based upon a file system
 * 
 * @author <a href="mailto:carl.wilson@bl.uk">Carl Wilson</a>
 *
 */
public class FilesystemDigitalObjectManagerImpl implements DigitalObjectManager {
	/** The logger instance */
    private static Log _log = LogFactory.getLog(FilesystemDigitalObjectManagerImpl.class);
    
    /** The extension used for storing digital object metadata */
    private final static String DO_EXTENSION = ".planets.do";

    /** The name of this data registry instance */
    private String _name = null;
	/** This is the root directory of this particular Data Registry */
	private File _root = null;

	/**
	 * @param name
	 * 		The name of the data registry
	 * @param root
	 * 		A directory that is the root for this data registry
	 * @return
	 * 		A new FilesystemDigitalObjectManagerImpl instance based upon a root directory
	 * @throws IllegalArgumentException 
	 */
	public static DigitalObjectManager getInstance(String name, File root) throws IllegalArgumentException {
		return new FilesystemDigitalObjectManagerImpl(name, root);
	}

	/**
	 * A convenience instantiator that create the File object for the user
	 * @param name
	 * 		The name of the data registry
	 * @param rootPath
	 * 		The string path to a directory that is the root for this data registry
	 * @return
	 * 		A new FilesystemDigitalObjectManagerImpl instance based upon a root directory
	 * @throws IllegalArgumentException 
	 */
	public static DigitalObjectManager getInstance(String name, String rootPath) throws IllegalArgumentException {
		return new FilesystemDigitalObjectManagerImpl(name, new File(rootPath));
	}

	/**
	 * @see eu.planets_project.ifr.core.storage.api.DigitalObjectManager#list(java.net.URI)
	 */
	public List<URI> list(URI pdURI) {
		// The return array of URIs contains the contents for the passed URI
		ArrayList<URI> retVal = null;

		// First lets look at the passed URI, if it's null then we need to return the root 
		FilesystemDigitalObjectManagerImpl._log.debug("Testing for null URI");
		if (pdURI == null)
		{
			FilesystemDigitalObjectManagerImpl._log.debug("URI is empty so return root URI only");
			retVal = new ArrayList<URI>();
			try {
				retVal.add( PDURI.formDataRegistryRootURI("localhost", "8080", this._name+"/") );
			} catch (URISyntaxException e) {
				FilesystemDigitalObjectManagerImpl._log.error("URI Syntax exception");
				FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
				FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
				return null;
			} 
			return retVal; 
		}
		FilesystemDigitalObjectManagerImpl._log.debug("URI is NOT NULL");
		PDURI realPdURI;
		String fullPath = null;
		try {
			realPdURI = new PDURI(pdURI);
			fullPath = this._root.getCanonicalPath() + File.separator + realPdURI.getDataRegistryPath();
			File searchRoot = new File(fullPath);
			FilesystemDigitalObjectManagerImpl._log.info("Looking at: "+pdURI+" -> "+searchRoot.getCanonicalPath());	
			if (searchRoot.exists() && searchRoot.isDirectory()) {
				// Create a filter to avoid do metadata files
				FilenameFilter filter = new FilenameFilter() {
					public boolean accept(File f, String name) {
						return !name.endsWith(FilesystemDigitalObjectManagerImpl.DO_EXTENSION);
					}
				};
				String[] contents = searchRoot.list(filter);
				retVal = new ArrayList<URI>();
				for (String s : contents) {
					retVal.add(new URI(pdURI+"/"+s));
				}
			}
		} catch (URISyntaxException e) {
			FilesystemDigitalObjectManagerImpl._log.error("URI Syntax exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			return null;
		} catch (UnsupportedEncodingException e) {
			FilesystemDigitalObjectManagerImpl._log.error("Unsupported encoding exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			return null;
		} catch (IOException e) {
			FilesystemDigitalObjectManagerImpl._log.error("IO exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			return null;
		}

		return retVal;
	}

	/**
	 * @see eu.planets_project.ifr.core.storage.api.DigitalObjectManager#retrieve(java.net.URI)
	 */
	public DigitalObject retrieve(URI pdURI)
			throws DigitalObjectNotFoundException {
		DigitalObject retObj = null;
	
		try {
			PDURI parsedURI = new PDURI(pdURI);
			parsedURI.replaceDecodedPath(parsedURI.getDataRegistryPath() + FilesystemDigitalObjectManagerImpl.DO_EXTENSION);
			String fullPath = this._root.getCanonicalPath() + File.separator + parsedURI.getDataRegistryPath();
			StringBuilder fileData = new StringBuilder(1024);
			BufferedReader reader = new BufferedReader(new FileReader(fullPath));
			char[] buf = new char[1024];
			int numRead = 0;
			while((numRead=reader.read(buf)) != -1) {
				fileData.append(buf, 0, numRead);
			}
			reader.close();
			DigitalObject.Builder dob = new DigitalObject.Builder(fileData.toString());
			// Also turn the content reference into a real file stream:
			DigitalObjectContent c = Content.byReference(dob.getContent().read());
			dob.content(c);
			retObj = dob.build();
		} catch (UnsupportedEncodingException e) {
			FilesystemDigitalObjectManagerImpl._log.error("Unsupported encoding exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			throw new DigitalObjectNotFoundException("Couldn't retrieve Digital Object due to unupported encoding error", e);
		} catch (URISyntaxException e) {
			FilesystemDigitalObjectManagerImpl._log.error("URI Syntax exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			throw new DigitalObjectNotFoundException("Couldn't retrieve Digital Object due to URI Syntax error", e);
		} catch (FileNotFoundException e) {
			FilesystemDigitalObjectManagerImpl._log.error("File Not Found exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			throw new DigitalObjectNotFoundException("The DigitalObject was not found", e);
		} catch (IOException e) {
			FilesystemDigitalObjectManagerImpl._log.error("IO exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			throw new DigitalObjectNotFoundException("Couldn't retrieve Digital Object due to IO problem", e);
		}
		
		return retObj;
	}

	/**
	 * @see eu.planets_project.ifr.core.storage.api.DigitalObjectManager#store(java.net.URI, eu.planets_project.services.datatypes.DigitalObject)
	 */
	public void store(URI pdURI, DigitalObject digitalObject)
			throws DigitalObjectNotStoredException {
		try {
			// get the path from the URI to store at
			PDURI _parsedURI = new PDURI(pdURI);
			String path = _parsedURI.getDataRegistryPath();

			// We need to append the path to the root dir of this registry for the data
			File doBinary = new File(this._root.getCanonicalPath() + 
					File.separator + path);
            File doDir = doBinary.getParentFile();
            if( ! doDir.exists() ) doDir.mkdirs();
			File doMetadata = new File(this._root.getCanonicalPath() + 
					File.separator + path + FilesystemDigitalObjectManagerImpl.DO_EXTENSION);
			
			// Persist the object to a file
			InputStream inStream = digitalObject.getContent().read();
			OutputStream binStream = new FileOutputStream(doBinary);
			byte[] buf = new byte[1024];
			int len;
			while ((len = inStream.read(buf)) > 0) {
				binStream.write(buf, 0, len);
			}
			inStream.close();
			binStream.close();
			
			// Now write the Digital Object metadata to the file
			// First we need to update the purl and the content object reference
			URI purl = doBinary.toURI();
	        /* Create the content: */
	        DigitalObjectContent c1 = Content.byReference(purl.toURL());
	        /* Given these, we can instantiate our object: */
	        DigitalObject object = new DigitalObject.Builder(digitalObject).permanentUri(purl).content(c1).build();
			OutputStream outStream = new FileOutputStream(doMetadata);
			outStream.write(object.toXml().getBytes());
			outStream.close();
		} catch (UnsupportedEncodingException e) {
			FilesystemDigitalObjectManagerImpl._log.error("Unsupported encodeing exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			throw new DigitalObjectNotStoredException("Couldn't store Digital Object due to unupported encoding error", e);
		} catch (URISyntaxException e) {
			FilesystemDigitalObjectManagerImpl._log.error("URI Syntax exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			throw new DigitalObjectNotStoredException("Couldn't store Digital Object due to URI Syntax error", e);
		} catch (IOException e) {
			FilesystemDigitalObjectManagerImpl._log.error("IO exception");
			FilesystemDigitalObjectManagerImpl._log.error(e.getMessage());
			FilesystemDigitalObjectManagerImpl._log.error(e.getStackTrace());
			throw new DigitalObjectNotStoredException("Couldn't store Digital Object due to IO problem", e);
		}
	}
	
	
	
	/* (non-Javadoc)
     * @see eu.planets_project.ifr.core.storage.api.DigitalObjectManager#getQueryModes()
     */
    public List<Class<? extends Query>> getQueryTypes() {
        return null;
    }

    /* (non-Javadoc)
     * @see eu.planets_project.ifr.core.storage.api.DigitalObjectManager#isWritable(java.net.URI)
     */
    public boolean isWritable(URI pdURI) {
        return true;
    }

    /* (non-Javadoc)
     * @see eu.planets_project.ifr.core.storage.api.DigitalObjectManager#list(java.net.URI, eu.planets_project.ifr.core.storage.api.query.Query)
     */
    public List<URI> list(URI pdURI, Query q) throws QueryValidationException {
        return null;
    }

    //=============================================================================================
	// PRIVATE METHODS
	//=============================================================================================
	/**
	 * We don't want the no arg constructor used as every file based registry should have a
	 * root directory
	 */
	private FilesystemDigitalObjectManagerImpl() {
	}

	/**
	 * Constructor that instantiates the data registry on a root directory
	 * @param name
	 * 		A non null, none empty java.lang.String that provides a name for this data registry
	 * @param root
	 * 		A directory that is the root for this data registry, this should be a none null
	 * 		java.io.File that is initialised to point to an existing directory
	 * @throws IllegalArgumentException
	 * 		When one of the passed parameters doesn't satisfy the criteria documented above
	 */
	private FilesystemDigitalObjectManagerImpl(String name, File root) throws IllegalArgumentException {
		// Check the passed arguments, this will throw the IllegalArgumentException if not OK
		this.checkConstructorArguments(name, root);
		
		// OK we have a root directory so assign it and assign the name
		this._root = root;
		this._name = name;
	}
	
	private void checkConstructorArguments(String name, File root) throws IllegalArgumentException {
		// Ensure root is not null
		if (root == null) {
			String message = "Supplied root dir is null";
			FilesystemDigitalObjectManagerImpl._log.error(message);
			throw new IllegalArgumentException(message);
		// first make sure it exists and is a directory
		} else if (root.exists()) {
			// OK root exists but it MUST be a directory
			if (!root.isDirectory()) {
				String message = root.getPath() + " is not a directory";
				FilesystemDigitalObjectManagerImpl._log.error(message);
				throw new IllegalArgumentException(message);
			}
		// It doesn't exist so lets create the directory
		} else {
			String message = "Directory " + root.getPath() + " doesn't exist";
			FilesystemDigitalObjectManagerImpl._log.error(message);
			throw new IllegalArgumentException(message);
		}

		// Name should not be null or empty
		if (name == null) {
			String message = "The supplied name should not be null";
			FilesystemDigitalObjectManagerImpl._log.error(message);
			throw new IllegalArgumentException(message);
		} else if (name.length() < 1) {
			String message = "The supplied name should not be empty";
			FilesystemDigitalObjectManagerImpl._log.error(message);
			throw new IllegalArgumentException(message);
		}
	}
}
/**
 * 
 */
package eu.planets_project.tb.gui.backing;

import java.io.File;

import eu.planets_project.ifr.core.common.logging.PlanetsLogger;
import eu.planets_project.tb.api.TestbedManager;
import eu.planets_project.tb.api.model.Experiment;
import eu.planets_project.tb.api.persistency.ExperimentPersistencyRemote;
import eu.planets_project.tb.gui.util.JSFUtil;
import eu.planets_project.tb.gui.backing.FileUploadBean;
import eu.planets_project.tb.gui.backing.exp.NewExpWizardController;
import eu.planets_project.tb.impl.model.ExperimentImpl;
import eu.planets_project.tb.impl.persistency.ExperimentPersistencyImpl;
import eu.planets_project.tb.impl.serialization.ExperimentViaJAXB;


/**
 * @author <a href="mailto:Andrew.Jackson@bl.uk">Andy Jackson</a>
 *
 */
public class UploadManager {
    private static PlanetsLogger log = PlanetsLogger.getLogger(UploadManager.class, "testbed-log4j.xml");

    /**
     * Triggers the page's file upload element, takes the selected data and 
     * transfers it into the Testbed's file repository. The reference to this file
     * is layed into the system's application map.
     * @return: Returns an instance of the FileUploadBean (e.g. for additional operations as .getEntryName, etc.)
     * if the operation was successful or null if an error occured
     */
    public static FileUploadBean uploadFile(boolean keep){
    	FileUploadBean file_upload = getCurrentFileUploadBean();
    	try{
    		//trigger the upload command
    		String result = file_upload.upload(keep);
    		log.info("Got result: "+result);
    		
    		if(!result.equals("success-upload")){
    			return null;
    		}
    	}
    	catch(Exception e){
    		//In this case an error occured ("error-upload"): just reload the page without adding any information
    		log.error("error uploading file to Testbed's input folder: "+e.toString());
    		return null;
    	}
    	
    	return file_upload;
    }

    /**
     * Helper to fetch the FileUploadBean from the request
     * @return
     */
    public static FileUploadBean getCurrentFileUploadBean(){
    	return (FileUploadBean)JSFUtil.getManagedObject("FileUploadBean");
    }
    
    /**
     * Helper to upload an experiment from a file and import it.
     * 
     * This checks the version number of the uploaded file and modifies the import if necessary.
     * 
     * @param uploaded The File containing the serialised experiment.
     * @return "success" if all goes well.
     */
    public String importExperiment( File uploaded ) {
        log.info("Importing experiment from file: "+uploaded.getPath());
        ExperimentImpl exp = ExperimentViaJAXB.readFromFile(uploaded);
        log.info("Parsed into Experiment: "+exp.getExperimentSetup().getBasicProperties().getExperimentName());
        // Create a new Experiment:
        TestbedManager testbedMan = (TestbedManager) JSFUtil.getManagedObject("TestbedManager");
        // Merge into DB
        ExperimentPersistencyRemote dao_r = ExperimentPersistencyImpl.getInstance();
        Experiment newExp = testbedMan.createNewExperiment();
        exp.setEntityID(newExp.getEntityID());
        dao_r.updateExperiment(exp);
        testbedMan.updateExperiment(exp);
        log.info("Persisted experiment.");
        return "success";
    }

}
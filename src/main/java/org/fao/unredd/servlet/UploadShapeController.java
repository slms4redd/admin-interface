package org.fao.unredd.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartResolver;

/**
* This class is the servlet to handle the requests to the upload services (single/multiple uploads).
*
* @author Alfonsetti
*/
public class UploadShapeController extends AdminGUIAbstractServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2615382554675728029L;
	
	private Pattern pattern;
	private Matcher matcher;
	
	//private MultipartResolver multipartResolver;
	
	// Test Path
	final static private String UPLOAD_PATH_TEST		= "C:\\Users\\workstation\\Desktop\\uploads\\";
	final static private String UPLOAD_PATH_SHP 		= "/var/tmp/shp/";
	final static private String UPLOAD_PATH_GEOTIFF		= "/var/tmp/geotiff/";
	final static private String UPLOAD_PATH_TIMESERIES 	= "/var/tmp/timeseries/";
	
	// Upload URL. Properties File
	final static private String UPLOAD_PATH_FILE		= "resources/upload-directories.properties";
	
	// Allowed extensions for vector file
	final static private String shp_shp			 		= "shp";
	final static private String shp_shx			 		= "shx";
	final static private String shp_prj			 		= "prj";
	final static private String shp_dbf			 		= "dbf";
	
	// Allowed extensions for raster file
	final static private String rst_tif			 		= "tif";
	final static private String rst_tiff		 		= "tiff";
	
	// Regular Expression to check shp files extensions
	private static final String IMAGE_SHP_PATTERN 		= "([^\\s]+(\\.(?i)(shp|shx|prj|dbf))$)";
	private static final String IMAGE_RST_PATTERN 		= "([^\\s]+(\\.(?i)(tif|tiff))$)";
	private static final String ZIP_PATTERN 			= "([^\\s]+(\\.(?i)(zip))$)";
	
	/*@SuppressWarnings("unused")
	private void initMultipartResolver(ApplicationContext context)
	  {
	    try
	    {
	      this.multipartResolver = ((MultipartResolver)context.getBean("multipartResolver", MultipartResolver.class));
	      System.out.println("Using MultipartResolver [" + this.multipartResolver + "]");
	    }
	    catch (NoSuchBeanDefinitionException ex)
	    {
	      this.multipartResolver = null;
	      System.out.println("Unable to locate MultipartResolver with name 'multipartResolver': no multipart request handling provided");
	    }
	  }*/
	
	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		System.out.println("Shape Load");
		out.print("Request content length is " + request.getContentLength() + "<br/>"); 
	    out.print("Request content type is " + request.getHeader("Content-Type") + "<br/>");
	    System.out.println("files from form: "+request.getAttribute("file"));
	    System.out.println("files from form: "+request.getAttribute("name"));
		
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	    
	    if(isMultipart){
            
	    	ServletFileUpload upload = new ServletFileUpload();
	    	
	    	try{
	    		FileItemIterator iter = upload.getItemIterator(request);
		        
	    		FileItemStream item = null;
		         String name = "";
		        
		         InputStream stream = null;
		         
		         while (iter.hasNext()) {
		        	 item = iter.next();
		        	 name = item.getFieldName();
		        	 System.out.println("item: "+item);
		        	 stream = item.openStream();
		        	 
		        	 if(item.isFormField()) { 
		        		 System.out.println("Get Vector File - IF");
		        		 out.write("Form field " + name + ": " + Streams.asString(stream) + "<br/>");
		        	 }
		        	 else {
		        		 System.out.println("Get Vector File - ELSE");
		        		 name = item.getName();
		                
		                System.out.println("name: " + name);
		                
		                if(name != null && !"".equals(name)){
		                	//File Name
		                	String fileName = new File(item.getName()).getName();
		                	
		                	//Creating the directory to store file
		                	String rootPath = System.getProperty("catalina.home");
		                	System.out.println("rootPath: " + rootPath);
		                	File dir = new File(rootPath + File.separator + "tmpFiles");
		                	if (!dir.exists())
		                		dir.mkdirs();
		                	
		                	System.out.println("dir: " + dir);
		                	File file = new File(dir.getAbsolutePath() + File.separator +fileName);
		                	System.out.println("file: " + file.getAbsolutePath()+file.getName());
		                	
		                	/**
		                	 * The expected vector file is a shape file, made up of 4 files (.shp, .shx, .prj, .dbf). If they are not contained in the zip
		                	 * archive input is rejected.
		                	 */
		                	//TODO Check if it is a zip file validating the extension (zip) and the content (it has to contain the following sequence 0x504b0304)
		                	if(validate(file.getPath(), ZIP_PATTERN)) {
		                		System.out.println("A zip file has a .zip extension");
		                		
		                		out.write("Client file: " + item.getName() + " <br/>with file name "
                                        + fileName + " was uploaded.<br/>");
		                		
		                		// Copy file to temporary destination
	                			FileOutputStream fos = new FileOutputStream(file);
	                			long fileSize = Streams.copy(stream, fos, true);
	                			out.write("Size was " + fileSize + " bytes <br/>");
	                			out.write("File Path is " + file.getPath() + "<br/>");
	                			
		                		//TODO Check the type of the content (raster, timeseries, vector)
		                		//TODO if vector (shape file), check if there are the four files expected by GeoServer (.shp, .shx, .prj, .dbf).
		                		//TODO if raster check that only the accepted extensions are passed (tiff, tif)
		                		if(listContentsOfZipFile(file.getPath()).equals("none")) {
		                			System.out.println("Not a proper shape image file");
		                			//TODO delete file from the temporary location. 
		                			//TODO Manage exception. Error page to the user. Redirect the user to the main page.
		                		}
		                		else if(listContentsOfZipFile(file.getPath()).equals("shape")) {
		                			//TODO Move file to final destination on to the right GeoBatch upload directory for shape files
		                			System.out.println("Shape image file");
		                			
		                			/*TODO Move file to GeoServer extdata directory. Set ownership and access mode to the file in order to let
		                			 * Geoserver work with the file.*/
		                		}
		                		else if(listContentsOfZipFile(file.getPath()).equals("raster"))
		                			//TODO Move file to final destination on to the right GeoBatch upload directory for shape files
		                			System.out.println("Raster image file");
		                			//TODO delete file from the temporary location. 
		                			//TODO Manage exception. Error page to the user. Redirect the user to the Raster Upload page.
			                	}
		                	else {
		                		out.write("This is not a zip file");
		                		//TODO Manage exception. Error page to the user. Redirect the user to the Main Upload page.
		                	}
		                }
		            }
		         }
	     	} catch(FileUploadException fue) {
	     		out.write("Error on uploading the file. Details of the error are: "+fue);
	     	}
	    } 
	}
	
	/**
	 * List contents of the input file. 
	 * Note: The input file has to be a zip archive type.
	 * 
	 * @param fileName
	 * 		Full path of the zip file to be passed.
	 * 		Note: in this case it will be the temporary path.
	 * 
	 * @author Alfonsetti
	 */
	private String listContentsOfZipFile(String fileName) {
        
		String res = "none";
		
        try {
            ZipFile zipFile = new ZipFile(fileName);
            
            Enumeration zipEntries = zipFile.entries();
            
            while (zipEntries.hasMoreElements()) {
                
            	String tmp = ((ZipEntry)zipEntries.nextElement()).getName();
                //Process the name, here we just print it out
                System.out.println("Here checking the Zip file"+tmp);
                if(!validate(tmp, IMAGE_SHP_PATTERN)) {
                	System.out.println("Inside Zip. Shape");
                	return "shape";
                }
                else if(!validate(tmp, IMAGE_RST_PATTERN)) {
                	System.out.println("Inside Zip. Raster");
        			return "raster";
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return res;
    }
	
	/**
	 * Image extension validator
	 * 
	 * @param image
	 * 			raster, or shape, or timeseries.
	 * @return
	 * 			true if one of the regular expression matches the pattern configured, false otherwise
	 * @author Alfonsetti
	 */
	public boolean validate(String image, String filePattern) {
		
		System.out.println("Image: "+image);
		pattern = Pattern.compile(filePattern);
		matcher = pattern.matcher(image);
		System.out.println("result: "+matcher.matches());
		
		return matcher.matches();
	}
	
	private String getProperties() {
		
		System.out.println("Sono qui");
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(UPLOAD_PATH_FILE).getFile());
		
		System.out.println("#############File Abs Path: "+file.getAbsolutePath());
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {

			input = new FileInputStream(UPLOAD_PATH_FILE);

			System.out.println("Properties File: "+input.toString());
			
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("upload_path_shp"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
}

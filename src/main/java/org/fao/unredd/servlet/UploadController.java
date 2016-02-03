package org.fao.unredd.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
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

/**
* This class is the servlet to handle the requests to the upload services (single/multiple uploads).
*
* @author Alfonsetti
*/
public class UploadController extends AdminGUIAbstractServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2615382554675728029L;
	
	private Pattern pattern;
	private Matcher matcher;
	
	// Test Path
	final static private String UPLOAD_PATH_TEST		= "C:\\Users\\alfonsetti\\Desktop\\uploads\\";
	final static private String UPLOAD_PATH_SHP 		= "/var/tmp/shp/";
	final static private String UPLOAD_PATH_GEOTIFF		= "/var/tmp/geotiff/";
	final static private String UPLOAD_PATH_TIMESERIES 	= "/var/tmp/timeseries/";
	
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
	
	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		System.out.println("Raster Load");
		out.print("Request content length is " + request.getContentLength() + "<br/>"); 
	    out.print("Request content type is " + request.getHeader("Content-Type") + "<br/>");
		
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
		        		 out.write("Form field " + name + ": " + Streams.asString(stream) + "<br/>");
		        	}
		            else {
		                name = item.getName();
		                
		                System.out.println("name: " + name);
		                
		                if(name != null && !"".equals(name)){
		                   // File Name
		                	String fileName = new File(item.getName()).getName();
		                   out.write("Client file: " + item.getName() + " <br/>with file name "
		                                              + fileName + " was uploaded.<br/>");
		                   // File
		                   //File file = new File(getServletContext().getRealPath("/" + fileName));
		                   File file = new File(UPLOAD_PATH_TEST+fileName);
		                   
		                   // Copy file to temporary destination
		                   FileOutputStream fos = new FileOutputStream(file);
		                   long fileSize = Streams.copy(stream, fos, true);
		                   out.write("Size was " + fileSize + " bytes <br/>");
		                   out.write("File Path is " + file.getPath() + "<br/>");
		                   
		                   //TODO Check if it is a zip file validating the extension (zip) and the content (it has to contain the following sequence 0x504b0304)
		                   if(validate(file.getPath(), ZIP_PATTERN))
		                	   System.out.println("A zip file has to have a .zip extension");
		                   
		                   //TODO Check the type of the content (raster, timeseries, vector)
		                   //TODO if vector (shape file), check if there are the four files expected by GeoServer (.shp, .shx, .prj, .dbf).
		                   //TODO if raster check that only the accepted extensions are passed (tiff, tif)
		                   if(listContentsOfZipFile(file.getPath()).equals("none"))
		                	   System.out.println("Not a proper image file");
		                   else if(listContentsOfZipFile(file.getPath()).equals("shape"))
		                	   //TODO Move file to final destination on to the right GeoBatch upload directory for shape files
		                	   System.out.println("Shape image file");
		                   else if(listContentsOfZipFile(file.getPath()).equals("raster"))
		                	   //TODO Move file to final destination on to the right GeoBatch upload directory for shape files
		                	   System.out.println("Raster image file");
		                }
		            }
		         }
	     	} catch(FileUploadException fue) {out.write("fue!!!!!!!!!");}
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
                System.out.println(tmp);
                if(!validate(tmp, IMAGE_SHP_PATTERN))
                	return "shape";
                else if(!validate(tmp, IMAGE_RST_PATTERN))
        			return "raster";
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
	
}

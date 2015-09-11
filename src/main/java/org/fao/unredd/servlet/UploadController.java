package org.fao.unredd.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.stereotype.Controller;

/**
* This class is the servlet to handle the requests to the upload services (single/multiple uploads).
*
* @author Alfonsetti
*/
@Controller
public class UploadController extends AdminGUIAbstractServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2615382554675728029L;
	
	// Test Path
	final static private String upload_path_test		= "C:\\Users\\alfonsetti\\Desktop\\uploads\\";
	
	final static private String upload_path_shp 		= "/var/tmp/shp/";
	
	final static private String upload_path_geotiff		= "/var/tmp/geotiff/";
	
	final static private String upload_path_timeseries 	= "/var/tmp/timeseries/";
		
	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
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
		                   File file = new File(upload_path_test+fileName);
		                   
		                   FileOutputStream fos = new FileOutputStream(file);
		                   long fileSize = Streams.copy(stream, fos, true);
		                   out.write("Size was " + fileSize + " bytes <br/>");
		                   out.write("File Path is " + file.getPath() + "<br/>");
		                }
		            }
		         }
	     	} catch(FileUploadException fue) {out.write("fue!!!!!!!!!");}
	    } 
		
	}
	
}

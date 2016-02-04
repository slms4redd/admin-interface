<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://rawgit.com/jdorn/json-editor/master/dist/jsoneditor.min.js"></script>

<c:set var="bodyContent">
	<div id='title' style="width:800px; margin:0 auto;"><h2>Create the menu for the portal layers</h2></div>
    
    <div id='editor_holder' style="width:800px; margin:0 auto;"></div>
    <button id="submit">Submit (console.log)</button>
    
    <script>
      var schema = "";
      //retrieve the schema
      $.ajax({
		  dataType: "json",
		  url: "layers-menu-schemas/layers-menu-schema.json",
		  error: function(){
		  		alert('an error occurred while loading the schema file');
  			},
		  success: function(data, textStatus, jqXHR){
			  //retrieve the existing layers.json
			  schema=data;
		      $.ajax({
				  dataType: "json",
				  url: "LayerMenuBuilder?op=load",
				  success: buildEditor,
				  error: function(){ 
					  window.alert('an error occurred while loading the current json file');
				  }
			  });
		  }
	  });
      
      function buildEditor(data, textStatus, jqXHR){
	      JSONEditor.defaults.options.theme = 'bootstrap3';
	      var editor = new JSONEditor(document.getElementById('editor_holder'),
	    	{
			    theme : 'bootstrap3',
			    iconlib: 'bootstrap3',
			    disable_properties: 'true',
			    disable_collapse: 'true',
			    disable_edit_json: 'true',
			    schema: schema,
			    startval: data
	    	});
	      
	      
	      document.getElementById('submit').addEventListener('click',function() {
	    	  $.ajax({
	    		  method: "POST",
	    		  data: JSON.stringify(editor.getValue()),
				  url: "LayerMenuBuilder",
				  success: function(){
					  window.alert('layer-menu json file successfully updated!');
				  },
				  error: function(){
					  window.alert('an error occurred while storing the json file');
				  }
			  });
	          console.log(editor.getValue());
	      });
      }
    </script>
	
	
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>
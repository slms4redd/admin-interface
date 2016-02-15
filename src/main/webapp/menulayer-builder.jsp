<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://rawgit.com/jdorn/json-editor/master/dist/jsoneditor.min.js"></script>

<c:set var="bodyContent">
	<div id='title' style="width:800px; margin:0 auto;"><h2>Create the menu for the portal layers</h2></div>
    <button class="btn btn-primary"  data-toggle="modal" data-target="#myModal">Submit changes</button>
    
    <div class="alert_placeholder"></div>
    
    <div id='editor_holder' style="width:800px; margin:0 auto;"></div>
        <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">Submit changes</button>
    <div class="alert_placeholder"></div>
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
	        <p>Clicking on <strong>Update layers menu</strong> will immediately update the Menu layers on the portal<p>
	        <strong>Are you sure?</strong>
	        <p>A backup of the previous version will be saved on the server (NOTE <strong>only 1</strong> backup!)<p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Let me review my work</button>
	        <button type="button" id="submit" class="btn btn-primary">Update layers menu</button>
	      </div>
	    </div>
	  </div>
	</div>
	
    <script>
      var schema = "";
      //retrieve the schema
      $.ajax({
		  dataType: "json",
		  url: "layers-menu-schemas/layers-menu-schema.json",
		  error: function(){
		  		alert('an error occurred while loading the schema file','alert-danger');
  			},
		  success: function(data, textStatus, jqXHR){
			  //retrieve the existing layers.json
			  schema=data;
		      $.ajax({
				  dataType: "json",
				  url: "LayerMenuBuilder?op=load",
				  success: buildEditor,
				  error: function(){
					  alert('an error occurred while loading the current json file','alert-danger');
				  }
			  });
		  }
	  });
      
      function buildEditor(data, textStatus, jqXHR){
	      
    	  $( document ).ready(function(){
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
		      // DamianoG: brutal hack to change the background to the 3 main sections...
		      var rows = $("#editor_holder").find(".row").filter(function() {
				   return $(this).parents('.well').length === 1 
			  });
			  $(rows[0]).css({'background-color':'#F2F5A9'});
			  $(rows[1]).css({'background-color':'#ccff99'});
			  $(rows[2]).css({'background-color':'#ffc299'});
		      
		      
		      document.getElementById('submit').addEventListener('click',function() {
		    	  $('#myModal').modal('hide')
		    	  $.ajax({
		    		  method: "POST",
		    		  data: JSON.stringify(editor.getValue()),
					  url: "LayerMenuBuilder",
					  success: function(){
						  alert('layer-menu json file successfully updated!','alert-success');
					  },
					  error: function(){
						  alert('an error occurred while storing the json file','alert-danger');
					  }
				  });
		          console.log(editor.getValue());
		      });
    	  });
      }
      
      function alert(message, alertClass){
    	  $('.alert_placeholder').html('<div class="alert ' + alertClass +' alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>'+message+'</span></div>')
		  $(".alert_placeholder").fadeTo(3000, 1000).slideUp(500, function(){
			    $(".alert_placeholder").children().remove();
		  });
      }
    </script>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>
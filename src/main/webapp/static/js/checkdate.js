$(document).ready(function() {
		$('.errorintroduced').hide();
		$('.errordiscontinued').hide();
   		$('#addComputer').click(function(event){
	    	var dtValIn=$('#introduced').val();
	    	var dtValDis=$('#discontinued').val();
	    	
	    	if(ValidateDate(dtValIn))
	    	{
	    		$('.errorintroduced').hide();
	    	}
	    	else
	    	{
	    		alert(strings['error_date_alert']);
 	    		$('.errorintroduced').show();
	    		event.preventDefault();
    		}
	    	
		   	if(ValidateDate(dtValDis))
		   	{
		   		$('.errordiscontinued').hide();
		   	}
		   	else
		   	{
		   		alert(strings['error_date_alert']);
	 	   		$('.errordiscontinued').show();
		   		event.preventDefault();
	    	}
   		});
   		$('#editComputer').click(function(event){
	    	var dtValIn=$('#introduced').val();
	    	var dtValDis=$('#discontinued').val();
	    	
	    	if(ValidateDate(dtValIn))
	    	{
	    		$('.errorintroduced').hide();
	    	}
	    	else
	    	{
	    		alert(strings['error_date_alert']);
 	    		$('.errorintroduced').show();
	    		event.preventDefault();
    		}
	    	
		   	if(ValidateDate(dtValDis))
		   	{
		   		$('.errordiscontinued').hide();
		   	}
		   	else
		   	{
		   		alert(strings['error_date_alert']);
	 	   		$('.errordiscontinued').show();
		   		event.preventDefault();
	    	}
   		});
    	
    	function ValidateDate(dtValue) {
    		if (dtValue=="") {
    			return true;
    		}
    		var dtRegex = new RegExp(strings['error_regex']);
    		return dtRegex.test(dtValue);
    	}
});

 $(document).ready(function(){

$("#btn").click(function(e) {
	$.ajax({
	    url: "http://localhost:8080/weather/getData/"+$("#city").val(),
	    type: 'GET',
	    success: function(data){ 
	        $("#today").html("TODAY'S DATE: "+data.today);
			  $("#cityName").html("CITY NAME: "+data.city);
			  $("#weatherDesc").html("Overall Weather Description: "+data.weatherDesc);
			  $("#tempFahr").html("Temperature in Fahrenheit: "+data.tempFahr);
			  $("#tempCel").html("Temperature in Celsius: "+data.tempCel);
			  $("#sunrise").html("Sunrise: "+data.sunrise);
			  $("#sunset").html("Sunset: "+data.sunset);
	    },
	    error: function(data) {
	        alert('Error retireving data. Please verify input details. !');
	        $("#today").html("");
			  $("#cityName").html("");
			  $("#weatherDesc").html("");
			  $("#tempFahr").html("");
			  $("#tempCel").html("");
			  $("#sunrise").html("");
			  $("#sunset").html("");
	    }
	});
});
 });
 
 
 


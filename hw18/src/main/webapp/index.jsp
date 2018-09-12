<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Galerija</title>
		
		<style>
			body {
				background: #ffffff;
			}
			
		 	h1 {
		 		text-align: center;
		 		color: #29005b;
				font-family: Verdana;
				font-size: 50px;
				font-style: normal;
				font-variant: normal;
				font-weight: 1500;
				line-height: 26.4px;
			}
			
			h2 {
		 		color: #29005b;
				font-family: Verdana;
				font-size: 20px;
				font-style: normal;
				font-variant: normal;
				font-weight: 1500;
				line-height: 26.4px;
			}
			
			p {
				color: #000000;
				font-size: 20px;
				font-weight: 800;
			}
			
			.button {
			    border: none;
			    color: white;
			    padding: 9px 18px;
			    text-align: center;
			    text-decoration: none;
			    display: inline-block;
			    font-size: 16px;
			    margin: 2px 1px;
			    cursor: pointer;
			    border-radius: 10px;
			}
				
			.button1 { 
    			background-color: #520070; 
			}
		
			.button2 { 
    			background-color: #234893;  
			}
			
			img {
				margin: 2px 2px 2px 2px;
			}	
			
			.myDiv{
			    width: 100%;
			    height: 100%;
			    background: #ecdcf7;
			}  
		</style>
		
		<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="js/htmlescaping.js"></script>
		<script type="text/javascript"><!-- 
			$(document).ready( 
					function(){
						$.ajax({ 
							url: "servlets/getTags",
							dataType: "json",
							success: function(data) {
								var tags = data; // već je JSON zbog dataType gore...
								var html = "<h2>Tagovi za pretragu: </h2>";
								if(tags.length==0) {
									html = "<h2>Nema tagova za pretragu slika... </h2>"
								} else {
									for(var i=0; i < tags.length; i++) {
										var tag = "\"" + tags[i] + "\"";
										html += "<button type='button' class='button button1' onclick='getPictures(" + htmlEscape(tag) + ");'>" + htmlEscape(tags[i]) + "</button>";
										if(i == 0) {
											html += "</br>";
										}
									}
								}
							    $("#tagButtons").html(html);
							}
						});
				  }
			);
		
			function getPictures(tagName) {
				$.ajax({ 
					url: "servlets/getPictures",
					dataType: "json",
					data: {
						  tag : tagName
					},
					success: function(data) {
						var thumbnails = data; // već je JSON zbog dataType gore...
						var html = "<div class='myDiv'><h2>Rezultati pretrage: </h2>";
						if(thumbnails.length==0) {
							html = "<h2>Nema tagova za traženje slika... </h2>"
						} else {
							for(var i=0; i < thumbnails.length; i++) {
								html += "<img src='servlets/getImageByPath?path=" + htmlEscape(thumbnails[i]) + "' alt='HTML5 Icon' style='width:150px;height:150px;' onClick='getPicture(\"" + htmlEscape(thumbnails[i]) + "\");'>";
							}
						}
						html += "</div>"
					    $("#pictures").html(html);
					}
				});
				
			}
			
			function getPicture(picName) {
				console.log("picName");
				console.log(picName);
				var lastIndexOfSlash = picName.lastIndexOf('/');
				var lastIndexOfDot = picName.lastIndexOf('.');
				var name = picName.substring(lastIndexOfSlash + 1, lastIndexOfDot);
				console.log(name);
				$.ajax({ 
					url: "servlets/getPictureByPath",
					dataType: "json",
					data: {
						 pictureName : name
					},
					success: function(data) {
						var picture = data; // već je JSON zbog dataType gore...
						var html = "<div class='myDiv'><h2>Informacije o slici: </h2>";
						if(picture == null) {
							html = "<h2>Nema informacija o slici... </h2>"
						} else {
							html += "<p>Naziv: " + picture.name + "</p>"
							html += "<p>Opis: " + picture.description + "</p>"
							html += "<p>Tagovi: " + picture.tags + "</p>"
							html += "<div vertical-align:middle; text-align:center><img src='servlets/getImageByPath?path=" + picture.imagePath + "' alt='full image'></div>";
						}
						html += "</div>"
					    $("#pictures").html(html);
					}
				});
			}	
		//--></script>
	</head>
	<body>
	
		<h1>Galerija piva</h1>
		
		<div class="myDiv">
			<h2>Malo o pivu:</h2>
			
			<p>
			   Pivo je pjenušavo osvježavajuće piće s relativno malim sadržajem alkohola i karakteristične arome po hmelju,
			   a dobiva se alkoholnim vrenjem pivske sladovine pomoću pivskog kvasca. Najstarije je i najraširenije alkoholno
			   piće na svijetu te ima veliki nutritivni značaj za čovjeka.
			</p>
		</div>
		
		<div id="tagButtons">&nbsp;</div>
		
		<div id="pictures">&nbsp;</div>
	</body>
</html>
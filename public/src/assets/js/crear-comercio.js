// Initialize and add the map
async function initMap() {
    // The location of Uluru
    let uluru = {lat: 40.4165, lng: -3.70256};
    // The map, centered at Uluru
    let map = new google.maps.Map(document.getElementById("map"), {
      zoom: 5,
      center: uluru,
  
    });
  
          //Se añade el marker
         var marker1 = new google.maps.Marker({
            position: {lat: 40.4165, lng: -3.70256}, 
            map: map,
            draggable: true
          });
  
    google.maps.event.addListener(marker1, 'dragend', function() {
  
      $("#lat-comercio").val(marker1.getPosition().lat())
      $("#long-comercio").val(marker1.getPosition().lng())
        
    //alert('Latitud = '+marker1.getPosition().lat()+ ', Longitud = '+marker1.getPosition().lng());
  });
  }
  
    window.initMap = initMap;
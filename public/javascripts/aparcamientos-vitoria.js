function getSitiosInteres(){

  fetch('http://localhost:8080/api/ciudades/7ac16116-14cc-4caa-b739-3597bbf00f28/aparcamientos',{
    method: 'GET',
    headers: {
      'Access-Control-Allow-Origin': '*',
      'accept': 'application/json'
    }
  })
  .then(res => res.json())
  .then(res => res.sitios)
  .then(res => console.log(res.length))
  .catch( err => console.error(err));
}

// Initialize and add the map
async function initMap() {
  // The location of Uluru
  const uluru = { lat:42.8467, lng: -2.67164 };
  // The map, centered at Uluru
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: 13,
    center: uluru,
  });
  
  let sitios = await fetch('http://localhost:8080/api/ciudades/7ac16116-14cc-4caa-b739-3597bbf00f28/aparcamientos',{
    method: 'GET',
    headers: {
      'Access-Control-Allow-Origin': '*',
      'accept': 'application/json'
    }
  })
  .then(res => res.json())
  .then(res => {
    let sitios = res.aparcamientos;
    let markers = [];
  
    for(let i=0; i<sitios?.length-1 || 0;i++){
      if (typeof(sitios) !== 'undefined') {

      let links = "";
      
        let linkExternos = sitios[i]['resumen']['linkExternos'];
      

      if (linkExternos !== undefined){
        for(let i=0; i<linkExternos?.length-1 || 0;i++){
          if (sitios[i] !== undefined && sitios[i]['resumen'] !== undefined && sitios[i]['resumen']['linkExternos'] !== undefined){
          let cadena =  new String(sitios[i]['resumen']['linkExternos'][i])
          if(cadena.toString() != 'undefined'){
          //console.log(cadena.toString())
          links += '<a href=' +cadena.toString() + '>' + cadena.toString() + '</a> '
          links +=  '<br> '
          }
      }
    }
      }
     


      let pos = { lat: sitios[i]['resumen']['latitud'], lng: sitios[i]['resumen']['longitud'] };
      

        //Dialogo de marker
        const contentString ='<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h1 id="firstHeading" class="firstHeading">' + sitios[i]['resumen']['direccion'] +'</h1>' +
        '<div id="bodyContent">' +
      '<p>Attribution: Vitoria-Gasteiz ' +
      
      links +
      ".</p>" +
      "</div>" +
      "</div>";
      const infowindow = new google.maps.InfoWindow({
        content: contentString,
      });

      //Se añade el marker
      markers.push(new google.maps.Marker({
        position: pos,
        map: map,
        title: sitios[i]['resumen']['nombre']
      }));

      //LIstener que abre el dialogo
      markers[i].addListener("click", () => {
        infowindow.open({
          anchor: markers[i],
          map,
          shouldFocus: false,
        });
      });
    }
  }
})
  .catch( err => console.error(err));
  

}
  window.initMap = initMap;
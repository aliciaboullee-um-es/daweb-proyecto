function getSitiosInteres(){

  fetch('http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/sitiosInteres',{
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
    const uluru = { lat: 37.6713, lng: -1.69879 };
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 13,
      center: uluru,
    });
    
    let sitios = await fetch('http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/sitiosInteres',{
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'accept': 'application/json'
      }
    })
    .then(res => res.json())
    .then(res => {
      let sitios = res.sitios;
      let markers = [];
    
      for(let i=0; i<sitios?.length-1 || 0;i++){
        if (typeof(sitios) !== 'undefined') {

        let links = "";
        let linkExternos = sitios[i]['resumen']['linkExternos'];

        if (linkExternos !== undefined){
          for(let i=0; i<linkExternos?.length-1 || 0;i++){
            if (sitios[i]['resumen']['linkExternos'] !== undefined){
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
        let idSitio = sitios[i]['resumen']['id'];

        //Dialogo de marker
        const contentString ='<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h1 id="firstHeading" class="firstHeading">' + sitios[i]['resumen']['nombre'] +'</h1>' +
        '<div id="bodyContent">' +
        "<p>" + sitios[i]['resumen']['comentario'] + "</p>" +
        '<p>Attribution: Lorca ' +
        
        links +
        ".</p>" +`<button class="cssbuttons-io-button" onclick="cargarAparcamientos('${idSitio}')"> Ver aparcamientos cercanos <div class="icon"><svg height="24" width="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path d="M0 0h24v24H0z" fill="none"></path><path d="M16.172 11l-5.364-5.364 1.414-1.414L20 12l-7.778 7.778-1.414-1.414L16.172 13H4v-2z" fill="currentColor"></path></svg></div></button>`+
        "</div>" +
        "</div>"
        ;
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




  async function cargarAparcamientos(idSitio){

    document.getElementById("map").outerHTML = '<div id="map"> </div>';
    console.log("APARCAMIENTOS")
    let markers=[];
    let sitio = await fetch('http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/sitiosInteres/'+idSitio,{
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'accept': 'application/json'
      }
    })
    .then(async function(res){
      console.log(res);
      let sitio = await res.json();
      console.log(sitio);
    
      // The location of Uluru
      let uluru = { lat:sitio['latitud'], lng: sitio['longitud'] };
      console.log(uluru);
      // The map, centered at Uluru
      let map = new google.maps.Map(document.getElementById("map"), {
        zoom: 13,
        center: uluru,
      });
  
      console.log("Mapa inicializado");
  
      let aparcamientos = await fetch('http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/sitiosInteres/'+sitio['id']+'/aparcamientos',{
        method: 'GET',
        headers: {
          'Access-Control-Allow-Origin': '*',
          'accept': 'application/json'
        }
      })
      .then(res => res.json())
      .then(res => {
        let aparcamientos = res.aparcamientos;
  
        //Se añade el marker del sitio de interes
        let markerSitio = new google.maps.Marker({
          position: uluru,
          map: map,
          title: sitio['direccion'],
          icon: {                             
            url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png" 
          }
        });
      
        //Dialogo de marker del sitio de Interes
        const contentString ='<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h1 id="firstHeading" class="firstHeading">' + sitio['nombre'] +'</h1>' +
        '<div id="bodyContent">' +
        "<p>" + sitio['comentario'] + "</p>" +"</div>" + "</div>"
          ;
        const infowindow = new google.maps.InfoWindow({
          content: contentString,
        });
  
        //Listener que abre el dialogo
        markerSitio.addListener("click", () => {
          infowindow.open({
            anchor: markerSitio,
            map,
            shouldFocus: false,
          });
        });
  
        for(let i=0; i<aparcamientos?.length-1 || 0;i++){
  
          let pos = { lat: aparcamientos[i]['resumen']['latitud'], lng: aparcamientos[i]['resumen']['longitud'] };
          
  
          //Dialogo de marker
          const contentString ='<div id="content">' +
          '<div id="siteNotice"></div>' +
          '<h1 id="firstHeading" class="firstHeading">' + aparcamientos[i]['resumen']['direccion'] +'</h1>' + "</div>";
          
          const infowindow = new google.maps.InfoWindow({
            content: contentString,
          });
  
          //Se añade el marker
          markers.push(new google.maps.Marker({
            position: pos,
            map: map,
            title: aparcamientos[i]['resumen']['direccion']
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
      })
      .catch( err => console.error(err));
    })
    .catch( err => console.error(err));
  }
  
  
  window.cargarAparcamientos = cargarAparcamientos;
  window.initMap = initMap;
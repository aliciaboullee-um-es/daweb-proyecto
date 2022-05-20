
// Initialize and add the map
async function initMap() {
    // The location of Uluru
    let uluru = { lat:36.7196, lng: -4.42002 };
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 13,
      center: uluru,
    });
    
    let sitios = await fetch('/comercios/all',{
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'accept': 'application/json'
      }
    })
    .then(res => res.json())
    .then(res => {
      
      let sitios = res.comercios;
      
      let markers = [];
    
      for(let i=0; i<sitios?.length || 0;i++){        

        let pos = { lat: Number(sitios[i]['lat']), lng: Number(sitios[i]['lng']) };
        console.log(pos)

        //Dialogo de marker
        const contentString ='<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h1 id="firstHeading" class="firstHeading">' + sitios[i]['nombre'] +'</h1>' +
        '<div id="bodyContent">' + "<br>"+
        "<p>Descripción: " + sitios[i]['descripcion'] + "</p>" +
        "<p>Tipo: " + sitios[i]['tipo'] + "</p>" +
        "</p>" +
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
          title: sitios[i]['nombre']
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
    

  }

  window.initMap = initMap;
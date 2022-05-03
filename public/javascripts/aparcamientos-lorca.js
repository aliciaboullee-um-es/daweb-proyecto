function getSitiosInteres(){

  fetch('http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/aparcamientos',{
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
    
    let sitios = await fetch('http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/aparcamientos',{
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
        

        //Dialogo de marker
        const contentString ='<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h1 id="firstHeading" class="firstHeading">' + sitios[i]['resumen']['direccion'] +'</h1>' +
        '<div id="bodyContent">' + "<br>"+
        'Calificar: '+
        '<p class="clasificacion" style="padding-right: 300px;"> '+
        
        '<input id="radio1" type="radio" name="estrellas" value="5" onclick="valorar(5)">'+
        '<label for="radio1">★</label>'+
        '<input id="radio2" type="radio" name="estrellas" value="4" onclick="valorar(4)">'+
        '<label for="radio2">★</label>'+
        '<input id="radio3" type="radio" name="estrellas" value="3" onclick="valorar(3)">'+
        '<label for="radio3">★</label>'+
        '<input id="radio4" type="radio" name="estrellas" value="2" onclick="valorar(2)">'+
        '<label for="radio4">★</label>'+
        '<input id="radio5" type="radio" name="estrellas" value="1" onclick="valorar(1)">'+
        '<label for="radio5">★</label>'+
      '</p>'+
        '<p>Attribution: Lorca ' +
        
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

  function valorar(valoracion){

    
    swal({
      title: '¿Desea emitir una valoración de '+valoracion+'?',
      icon: 'warning',
      buttons: true,
      buttons: ['Cancelar','Confirmar'],
    }).then((willDelete) => {
      if (willDelete) {
        swal(
          "Operación exitosa", "Se ha valorado el aparcamiento.", "success"
        )

        //TODO: Aquí hay que llamar a la api para emitir la valoración
      } 
    })

  }
  window.valorar = valorar;
  window.initMap = initMap;
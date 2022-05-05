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
        '<h1  style="font-size: 1.5em;" id="firstHeading" class="firstHeading">' + sitios[i]['resumen']['direccion'] +'</h1>' +
        '<div id="bodyContent">' + "<br>" + 
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
          cargarOpiniones(sitios[i]['url']);
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


  async function cargarOpiniones(urlSitio){
/*
    let opiniones = await fetch('http://localhost:8080/api/.....',{
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'accept': 'application/json'
      }
    })
    .then(res => res.json())
    .then(res => {

      let opiniones = res.opiniones;
      let cadenaHtml = '';
      //Recorremos todas las opiniones obtenidas
      for(let i=0; i<opiniones?.length-1 || 0;i++){
        let coment = opiniones[i][comentario];
        //Obtener el json que representa a la lista de opiniones de la url que se pasa como parametro
        if (coment !== undefined) cadenaHtml+='<figure class="snip1157"> <blockquote> ' + coment + '<div class="arrow"></div> </blockquote>'
        else cadenaHtml+= '<figure class="snip1157">';
        cadenaHtml+='<div class="author"> <h5>'+ opiniones[i][usuario] + '<span>' + LIttleSnippets.net + '</span></h5></div></figure>';
      }
    } )
*/
    //En vez de url deberia ser el nombre del aparcamiento
    let formulario = '<h2 style="font-size: 1.7em;">'+ urlSitio +'</h2>' +
    '<br> <br>'+
    '<div id="formCrearOpinion" class"crearOpinion">' +
    '<label for="comentario">Comentario:</label>' + '<textarea id="comentario" placeholder="Introduzca un comentario sobre este aparcamiento" type="textarea" maxlength="300"></textarea>  <br>  '+
    '<label for="clasificacion">Calificar:</label>' +  
    '<p id="clasificacion" class="clasificacion" style="padding-right: 438px;"> '+
    
    '<input id="radio1" type="radio" name="estrellas" value="5">'+
    '<label for="radio1">★</label>'+
    '<input id="radio2" type="radio" name="estrellas" value="4">'+
    '<label for="radio2">★</label>'+
    '<input id="radio3" type="radio" name="estrellas" value="3">'+
    '<label for="radio3">★</label>'+
    '<input id="radio4" type="radio" name="estrellas" value="2">'+
    '<label for="radio4">★</label>'+
    '<input id="radio5" type="radio" name="estrellas" value="1">'+
    '<label for="radio5">★</label>'+
  '</p>'+ '<button>'+
  '<div class="svg-wrapper-1">'+
    '<div class="svg-wrapper">'+
      '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">'+
        '<path fill="none" d="M0 0h24v24H0z"></path>'+
        '<path fill="currentColor" d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"></path>'+
      '</svg>'+
    '</div>'+
  '</div>'+
  '<span>Enviar</span>'+
'</button>' + '</div>';


    let coment = "BLabababskjabsbaguisb agdhuiahgiudha iah duihahdiuahdh agduihaudhiu auidhgahedui aighduiagheudg iaghdh iuega idgsg iag dsg";
    let cadenaHtml ='<figure class="snip1157"> <blockquote> ' + coment + '<div class="arrow"></div> </blockquote>'
    cadenaHtml+='<div class="author"> <h5>'+ "Usuario1" + '<span> ' + cargarEstrellas(2) + '</span></h5></div></figure>';
    cadenaHtml +='<figure class="snip1157"> <blockquote> ' + '<div class="arrow"></div> </blockquote>'
    cadenaHtml+='<div class="author"> <h5>'+ "Usuario2" + '<span> ' + cargarEstrellas(3) + ' </span></h5></div></figure>'


    document.getElementById("tablon").innerHTML = cadenaHtml;
    document.getElementById("formOpinion").innerHTML = formulario;
  }

function cargarEstrellas(calificacion){
  let cadenaEstrellas = "";
  for(let i=0; i<calificacion; i++){
    cadenaEstrellas+='<label style="color: orange;">★</label>';
  }
  return cadenaEstrellas;
}


  window.valorar = valorar;
  window.initMap = initMap;
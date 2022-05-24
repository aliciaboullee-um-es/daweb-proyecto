function getSitiosInteres(){
  fetch('http://localhost:8082/api/ciudades/'+window.localStorage.getItem('ciudad')+'/sitiosInteres',{
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
  let idCiudad = window.localStorage.getItem('ciudad');
  fetch('http://localhost:8082/api/ciudades/'+idCiudad,{
    method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'accept': 'application/json'
      }
  })
  .then(res => res.json())
  .then(res => {
    // The location of Uluru
    const uluru = { lat: res['latitud'], lng: res['longitud'] };
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 13,
      center: uluru,
    });
    
      let sitios = res.sitios;
      let markers = [];
    
      for(let i=0; i<sitios?.length-1 || 0;i++){
        if (typeof(sitios) !== 'undefined') {

        let links = "";
        let linkExternos = sitios[i]['linkExternos'];

        if (linkExternos !== undefined){
          for(let i=0; i<linkExternos?.length-1 || 0;i++){
            if (sitios[i]['linkExternos'] !== undefined){
            let cadena =  new String(sitios[i]['linkExternos'][i])
            if(cadena.toString() != 'undefined'){
            //console.log(cadena.toString())
            links += '<a href=' +cadena.toString() + '>' + cadena.toString() + '</a> '
            links +=  '<br> '
            }
        }
      }
        }

        let pos = { lat: sitios[i]['latitud'], lng: sitios[i]['longitud'] };
        let idSitio = sitios[i]['id'];

        //Dialogo de marker
        const contentString ='<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h1 id="firstHeading" class="firstHeading">' + sitios[i]['nombre'] +'</h1>' +
        '<div id="bodyContent">' +
        "<p>" + sitios[i]['comentario'] + "</p>" +
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
          title: sitios[i]['nombre']
        }));

        //LIstener que abre el dialogo
        markers[i].addListener("click", () => {
          infowindow.open({
            anchor: markers[i],
            map,
            shouldFocus: false,
          });
          cargarOpiniones(sitios[i]['urlWikipedia']);
        });
      }
    }
  })
    .catch( err => console.error(err));
    

  }




  async function cargarAparcamientos(idSitio){

    document.getElementById("map").outerHTML = '<div id="map"> </div>';
    let markers=[];
    let sitio = await fetch('http://localhost:8082/api/ciudades/'+window.localStorage.getItem('ciudad')+'/sitiosInteres/'+idSitio,{
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'accept': 'application/json'
      }
    })
    .then(async function(res){
      console.log(res);
      let sitio = await res.json();
    
      // The location of Uluru
      let uluru = { lat:sitio['latitud'], lng: sitio['longitud'] };
      // The map, centered at Uluru
      let map = new google.maps.Map(document.getElementById("map"), {
        zoom: 13,
        center: uluru,
      });
  
      console.log("Mapa inicializado");
  
      let aparcamientos = await fetch('http://localhost:8082/api/ciudades/'+window.localStorage.getItem('ciudad')+'/sitiosInteres/'+sitio['id']+'/aparcamientos',{
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
          cargarOpiniones(sitios[i]['url']);
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
  

  function valorar(urlSitio){
    swal({
      title: '¿Desea emitir una valoración de '+$('input:radio[name=estrellas]:checked').val()+'?',
      icon: 'warning',
      buttons: true,
      buttons: ['Cancelar','Confirmar'],
    }).then((willDelete) => {
      const timeElapsed = Date.now();
const today = new Date(timeElapsed);
      if (willDelete) {
        swal(
          "Operación exitosa", "Se ha valorado el sitio de interés.", "success"
        )
        let url = 'http://localhost:8080/api/opiniones/valoraciones?url='+urlSitio
        //TODO: Aquí hay que llamar a la api para emitir la valoración
        fetch(url,{
        method: 'post',
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Content-Type':'application/json',
        }, //serialize JSON body
        body: JSON.stringify({
          'calificacion': $('input:radio[name=estrellas]:checked').val(),
          'comentario': document.getElementById("comentario").value,
          "correo":window.localStorage.getItem("mail")
          ,
          'fechaValoracion': today.toISOString()
        })
      })
      } 
    })

  }


  async function cargarOpiniones(urlSitio){
    
    await fetch('http://localhost:8080/api/opiniones?url='+urlSitio,{
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'accept': 'application/json'
      }
    })
    .then(res => res.json())
    .then(res => {
      let valoraciones = res['valoraciones'];
      let cadenaHtml = '';
      //Recorremos todas las opiniones obtenidas
      for(let i=0; i<valoraciones?.length || 0;i++){
        let coment = valoraciones[i]['comentario'];
        //Obtener el json que representa a la lista de opiniones de la url que se pasa como parametro
        if (coment !== undefined) cadenaHtml+='<figure class="snip1157"> <blockquote> ' + coment + '<div class="arrow"></div> </blockquote>'
        else cadenaHtml+= '<figure class="snip1157">';
        cadenaHtml+='<div class="author"> <h5>'+ valoraciones[i]['correo'] + '<span>'+ cargarEstrellas(valoraciones[i]['calificacion']) + '</span></h5></div></figure>';
      }
    

    //En vez de url deberia ser el nombre del aparcamiento
    let formulario = '<br> <h2 style="font-size: 1.7em;margin-left:1.1em;"> Valoraciones </h2>' +
    '<br> <br>'+
    '<div id="formCrearOpinion" style="margin-left:1.1em;" class"crearOpinion">' +
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
  '</p>'+ `<button onclick="valorar('${urlSitio}');">`+
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


    document.getElementById("tablon").innerHTML = cadenaHtml;
    document.getElementById("formOpinion").innerHTML = formulario;
  } )
  }

function cargarEstrellas(calificacion){
  let cadenaEstrellas = "";
  for(let i=0; i<calificacion; i++){
    cadenaEstrellas+='<label style="color: orange;">★</label>';
  }
  return cadenaEstrellas;
}

  window.valorar = valorar;
  window.cargarAparcamientos = cargarAparcamientos;
  window.initMap = initMap;
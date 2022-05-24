
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
          cargarOpiniones(sitios[i]['nombre']);
        });
      
    }
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
          "Operación exitosa", "Se ha valorado el comercio.", "success"
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


  async function cargarOpiniones(nombre){
    let urlSitio = 'http://localhost:3000/comercios/'+nombre;
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
    let formulario = '<br><h2 style="font-size: 1.7em;margin-left:1.1em;"> Valoraciones </h2>' +
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
  window.initMap = initMap;
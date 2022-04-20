function getSitiosInteres(){

  fetch('http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/sitiosInteres',{
    method: 'GET',
    headers: {
      'Access-Control-Allow-Origin': '*',
      'accept': 'application/json'
    }
  })
  .then(res => {
    let j = res.json();
    console.log(j);
    console.log(j['sitios']);
    let ar = [];
    for(let i=0; i<res.length-1; i++)
    ar.push(res[i]);
    return ar;
  })
  .then(res => console.log(res))
  .catch( err => console.error(err));
}

// Initialize and add the map
async function initMap() {
    // The location of Uluru
    const uluru = { lat: 37.6713, lng: -1.69879 };
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 12,
      center: uluru,
    });
    
    let sitios = await getSitiosInteres();
    let markers = [];
    
    for(let i=0; i<sitios?.length-1 || 0;i++){
      let pos = { lat: sitios[i]['resumen']['latitud'], lng: sitios[i]['resumen']['longitud'] };
      console.log("SE ANADe");
      markers.push(new google.maps.Marker({
        position: pos,
        map: map,
      }));
    }

  }
  window.initMap = initMap;
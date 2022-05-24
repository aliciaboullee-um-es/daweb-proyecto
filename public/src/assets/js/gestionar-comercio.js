
		function editar(oldname){
            let name = $('#nombre-registrar-comercio').val()
            let descripcion = $('#descripcion-registrar-comercio').val()
            let tipo = $('#tipo-registrar-comercio').val()
            let lat = $('#lat-comercio').val()
            let lng = $('#long-comercio').val()
    
        fetch('/comercios/change', {
            method:'post',                    
            redirect : 'follow',
            headers : new Headers({'Content-Type':'application/json'}),
            body: JSON.stringify({'nombre': name, 'descripcion':descripcion, 'tipo':tipo, 'lat':lat, 'lng':lng, 'oldname':oldname})})
            .then(resultado => {return resultado.json()})
            .then(fjson => {
                if(fjson['type'] == 'ok')
                    return swal("Operación exitosa", "Se ha editado correctamente.", "success").then(function() {
                      location.reload();
                    });                        
                else 
                    return swal("Operación fallida", "La edición se ha interrumpido.", "error");
            })
            }
    
        $(document).ready(function () {
        var table = $('#table_id').DataTable({
          "order": [[1, "desc"]],
          "columnDefs": [
            {
              "orderable": false, visible: true,
              "searchable": false, "targets": -1
            },
            {
              "orderable": false, visible: true,
              "searchable": false, "targets": -2
            }
          ]
        });
        //Edit
        $('#table_id tbody').on('click', '#btn-edit', function (e) {
          var data = table.row($(this).parents('tr')).data();
          var boton = "button";
     var form = document.createElement("div");
      form.innerHTML = `
              <form id="formulario-registrar-comercio" action=""
                                        method="POST" enctype="multipart/form-data"
                                        class="needs-validation form-perfil" novalidate>
    
                                        
                                            <label style="padding-left: 0px;" for="nombre-registrar-comercio"
                                                class="col-sm-4">Nombre:</label>
                                            <input id="nombre-registrar-comercio" type="text" name="nombre"
                                                value = "`+ data[0]+`" required>
                                        
                                            <label style="padding-left: 0px;" for="descripcion-registrar-comercio"
                                                class="col-sm-4">Descripcion:</label>
                                            <textarea id="descripcion-registrar-comercio" name="descripcion"
                                                rows="3" required class="form-control col-sm-11 ml-auto" style="padding-left: 5px;padding-top: 5px;" >`+ data[1]+`</textarea>
                                        
                                        <div class="row">
                                            <div class="container col-md-5">
                                                
                                                    <label style="padding-left: 0px;" for="tipo-registrar-comercio"
                                                        class="col-sm-4">Tipo:</label>
                                                    <input id="tipo-registrar-comercio" type="text" name="tipo"
                                                        class="form-control col-sm-8" maxlength="40" value = "`+ data[2]+`" required>
                                                
                                                    <label style="padding-left: 0px;" for="lat-comercio"
                                                        class="col-sm-4">Latitud:</label>
    
                                                    <input id="lat-comercio" type="text" class="search_latitude"
                                                        size="30" value = "`+ data[3]+`">
    
                                                    <label style="padding-left: 0px;" for="long-comercio"
                                                        class="col-sm-4">Longitud:</label>
                                                    <input id="long-comercio" type="text" class="search_longitude"
                                                        size="30" value = "`+ data[4]+`">
                                                    <br>
                                                    <button id="btn-editar" type="button" onclick="editar('`+data[0]+`')">Editar Comercio</button>
                                                        
                                                </div>
    
                                            </div>
                                            
                                        </div>
    
                                    </form>
              `;
    
              swal({
                title: 'Editar comercio',
                text: 'Introduzca los nuevos datos',
                content: form,
                buttons: {
                  cancel: "Cancelar",
                  
                }
              }).then((value) => {
                console.log(value);
              });
    
          
          })
    
        // delete
        $('#table_id tbody').on('click', '#btn-delete', function (e) {
          var data = table.row($(this).parents('tr')).data();
          //alert( data[1] +" - "+ data[ 5 ] );
          swal({
            title: '¿Estás seguro?',
            text: "El comercio está a punto de ser eliminado permanentemente y no podrá ser recuperado.",
            icon: 'warning',
            buttons: true,
                    buttons: ['Cancelar','Eliminar'],
                    dangerMode: true,
          }).then((willDelete) => {
              if (willDelete) {
                   fetch('/comercios/delete', {
            method:'post',                    
            redirect : 'follow',
            headers : new Headers({'Content-Type':'application/json'}),
            body: JSON.stringify({'comercio': data[0] })})
            .then(resultado => {return resultado.json()})
            .then(fjson => {
                if(fjson['type'] == 'ok')
                    swal("Operación exitosa", "Se ha eliminado el comercio correctamente.", "success").then(function() {
                      location.reload();
                    });                        
            
        })
    
              }
          })
        });
          });

// Initialize and add the map
async function initMap() {
    // The location of Uluru
    let uluru = {lat: 36.735053, lng: -4.609028};
    // The map, centered at Uluru
    let map = new google.maps.Map(document.getElementById("map"), {
      zoom: 13,
      center: uluru,
  
    });
  
          //Se añade el marker
         var marker1 = new google.maps.Marker({
            position: {lat: 36.735053, lng: -4.609028}, 
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
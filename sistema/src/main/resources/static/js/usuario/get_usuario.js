window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de usuarios con el método GET
      //nos devolverá un JSON con una colección de usuarios
      const url = URL_BASE + '/usuarios';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
      //recorremos la colección de usuarios del JSON
         for(usuario of data){
            //por cada usuario armaremos una fila de la tabla
            //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el usuario
            var table = document.getElementById("usuarioTable");
            var usuarioRow =table.insertRow();
            let tr_id = 'tr_' + usuario.id;
            usuarioRow.id = tr_id;

            //por cada usuario creamos un boton delete que agregaremos en cada fila para poder eliminarlo
            //dicho boton invocara a la funcion de java script deleteByKey que se encargará
            //de llamar a la API para eliminar un usuario
            let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + usuario.id + '\"' +
                                      ' type="button" onclick="deleteBy('+usuario.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

            //por cada usuario creamos un boton que muestra el id y que al hacerle clic invocará
            //a la función de java script findBy que se encargará de buscar el usuario que queremos
            //modificar y mostrar los datos del mismo en un formulario.
            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + usuario.id + '\"' +
                                      ' type="button" onclick="findBy('+usuario.id+')" class="btn btn-info btn_id">' +
                                      usuario.id +
                                      '</button>';

            //armamos cada columna de la fila
            //como primer columna pondremos el boton modificar
            //luego los datos del usuario
            //como ultima columna el boton eliminar
            usuarioRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_nombre\">' + usuario.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_apellido\">' + usuario.apellido.toUpperCase() + '</td>' +
                    '<td class=\"td_dni\">' + usuario.dni.toUpperCase() + '</td>' +
                    '<td class=\"td_fecha_ingreso\">' + convertirFecha(usuario.fechaIngreso) + '</td>' +
                    '<td class=\"td_calle\">' + usuario.domicilio.calle.toUpperCase() + '</td>' +
                    '<td class=\"td_numero\">' + usuario.domicilio.numero.toUpperCase() + '</td>' +
                    '<td class=\"td_localidad\">' + usuario.domicilio.localidad.toUpperCase() + '</td>' +
                    '<td class=\"td_provincia\">' + usuario.domicilio.provincia.toUpperCase() + '</td>' +
                    '<td>' + deleteButton + '</td>';

        };
        document.getElementById("loading").style.display = "none";
    })
    })

    

   

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/usuario-lista.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })

    function convertirFecha(fecha) {
      return fecha?fecha.split("-").reverse().join("/"):"?";
    }

    
    })
window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de prestadores con el método GET
      //nos devolverá un JSON con una colección de prestadores
      const url = URL_BASE + '/prestadores';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
      //recorremos la colección de prestadores del JSON
         for(prestador of data){
            //por cada prestador armaremos una fila de la tabla
            //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el prestador
            var table = document.getElementById("prestadorTable");
            var prestadorRow =table.insertRow();
            let tr_id = 'tr_' + prestador.id;
            prestadorRow.id = tr_id;

            //por cada prestador creamos un boton delete que agregaremos en cada fila para poder eliminarlo
            //dicho boton invocara a la funcion de java script deleteByKey que se encargará
            //de llamar a la API para eliminar un prestador
            let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + prestador.id + '\"' +
                                      ' type="button" onclick="deleteBy('+prestador.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

            //por cada prestador creamos un boton que muestra el id y que al hacerle clic invocará
            //a la función de java script findBy que se encargará de buscar el prestador que queremos
            //modificar y mostrar los datos del mismo en un formulario.
            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + prestador.id + '\"' +
                                      ' type="button" onclick="findBy('+prestador.id+')" class="btn btn-info btn_id">' +
                                      prestador.id +
                                      '</button>';

            //armamos cada columna de la fila
            //como primer columna pondremos el boton modificar
            //luego los datos del prestador
            //como ultima columna el boton eliminar
            prestadorRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_nombre\">' + prestador.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_apellido\">' + prestador.apellido.toUpperCase() + '</td>' +
                    '<td class=\"td_matricula\">' + prestador.matricula.toUpperCase() + '</td>' +
                    '<td>' + deleteButton + '</td>';

        };
        document.getElementById("loading").style.display = "none";
        })
   
  })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/prestador-lista.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })


    })
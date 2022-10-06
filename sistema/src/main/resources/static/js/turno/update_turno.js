window.addEventListener('load', function () {


    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado del turno
    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        //creamos un JSON que tendrá los datos del turno
        //a diferencia de un turno nuevo en este caso enviamos el id
        //para poder identificarlo y modificarlo para no cargarlo como nuevo
        const formData = {
            id: document.querySelector('#turno_id').value,
            usuario: { id: document.querySelector('#usuario-id').value },
            prestador: { id: document.querySelector('#prestador-id').value },
            fechaHora: document.querySelector('#fecha').value 

        };

        //invocamos utilizando la función fetch la API turnos con el método PUT que modificará
        //el turno que enviaremos en formato JSON
        const url = URL_BASE + '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        let notOk = false;
        fetch(url,settings)
        .then(res => {
                notOk = !res.ok;
            return res.json()
        })
        .then(data =>{
            if (notOk) {
                //Si hay algun error se muestra un mensaje diciendo que el turno
                //no se pudo guardar y se intente nuevamente

                Swal.fire("Maldición!", data, "error");
            }
            else{
                 //Si no hay ningun error se muestra un mensaje diciendo que el turno
                 //se agrego bien
                
                 Swal.fire(
                    "Correcto!",
                    "Ya actualizamos los datos del <strong>turno</strong>!",
                    "success"
                  ).then((result) => {
                    if (result.isConfirmed) {
                        location.reload()
                    }
                  })
                  
            }
        })

    })
 })

    //Es la funcion que se invoca cuando se hace click sobre el id de un turno del listado
    //se encarga de llenar el formulario con los datos del turno
    //que se desea modificar
    function findBy(id) {
          const url = URL_BASE + '/turnos'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let turno = data;
              document.querySelector('#turno_id').value = turno.id;
              document.querySelector('#usuario-id').value = turno.usuario.id;
              document.querySelector('#prestador-id').value = turno.prestador.id;
              document.querySelector('#fecha').value = turno.fechaHora;
              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_turno_updating').style.display = "block";
          }).catch(error => {
            Swal.fire("Maldición!", error, "error");        })
      }



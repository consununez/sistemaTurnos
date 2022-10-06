window.addEventListener('load', function () {


    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado del usuario
    const formulario = document.querySelector('#update_usuario_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        
        //creamos un JSON que tendrá los datos del usuario
        //a diferencia de un usuario nuevo en este caso enviamos el id
        //para poder identificarlo y modificarlo para no cargarlo como nuevo
        const formData = {
            id: document.querySelector('#usuario_id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            dni: document.querySelector('#dni').value,
            fechaIngreso: convertirFecha(document.querySelector("#fecha-ingreso").value),
            domicilio: {
                calle: document.querySelector('#calle').value,
                numero: document.querySelector('#numero').value,
                localidad: document.querySelector('#localidad').value,
                provincia: document.querySelector('#provincia').value
            }

        };

        //invocamos utilizando la función fetch la API usuarios con el método PUT que modificará
        //el usuario que enviaremos en formato JSON
        const url = URL_BASE + '/usuarios';
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
                //Si hay algun error se muestra un mensaje diciendo que el odontologo
                //no se pudo guardar y se intente nuevamente

                Swal.fire("Maldición!", data, "error");
            }
            else{
                 //Si no hay ningun error se muestra un mensaje diciendo que el odontologo
                 //se agrego bien
                
                 Swal.fire(
                    "Correcto!",
                    "Ya actualizamos los datos de <strong>" + data.nombre + " " + data.apellido + "</strong>!",
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

    //Es la funcion que se invoca cuando se hace click sobre el id de un usuario del listado
    //se encarga de llenar el formulario con los datos del usuario
    //que se desea modificar
    function findBy(id) {
          const url = URL_BASE + '/usuarios'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let usuario = data;
              document.querySelector('#usuario_id').value = usuario.id;
              document.querySelector('#nombre').value = usuario.nombre;
              document.querySelector('#apellido').value = usuario.apellido;
              document.querySelector('#dni').value = usuario.dni;
             document.querySelector('#fecha-ingreso').value = desConvertirFecha(usuario.fechaIngreso);
              document.querySelector('#calle').value = usuario.domicilio.calle;
              document.querySelector('#numero').value = usuario.domicilio.numero;
              document.querySelector('#localidad').value = usuario.domicilio.localidad;
              document.querySelector('#provincia').value = usuario.domicilio.provincia;
              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_usuario_updating').style.display = "block";
          }).catch(error => {
              Swal.fire("Maldición!", error, "error");
          })
      }

      function convertirFecha(fecha) {
        return fecha.split("/").reverse().join("-");
      }

      function desConvertirFecha(fecha) {
        return fecha.split("-").reverse().join("/");
      }

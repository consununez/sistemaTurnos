window.addEventListener("load", function () {

  //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
  //los datos que el usuario cargará del nuevo usuario
  const formulario = document.querySelector("#add_new_usuario");

  document.querySelector("#fecha-ingreso").value = fechaActual();

  //Ante un submit del formulario se ejecutará la siguiente funcion
  formulario.addEventListener("submit", function (event) {
    event.preventDefault();

    //creamos un JSON que tendrá los datos del nuevo usuario
    const formData = {
      nombre: document.querySelector("#nombre").value,
      apellido: document.querySelector("#apellido").value,
      dni: document.querySelector("#dni").value,
      fechaIngreso: convertirFecha(document.querySelector("#fecha-ingreso").value),
      domicilio: {
        calle: document.querySelector("#calle").value,
        numero: document.querySelector("#numero").value,
        localidad: document.querySelector("#localidad").value,
        provincia: document.querySelector("#provincia").value
      }
    };
    
    //invocamos utilizando la función fetch la API usuarios con el método POST que guardará
    //el usuario que enviaremos en formato JSON
    const url = URL_BASE + '/usuarios';
    const settings = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    };

    let notOk = false;
    fetch(url,settings)
    .then(res => {
        notOk = !res.ok;
        return res.json()
    })
    .then(data =>{
        if (notOk) {
            //Si hay algun error se muestra un mensaje diciendo que el usuario
            //no se pudo guardar y se intente nuevamente

            Swal.fire("Maldición!", data, "error");
        }
        else{
             //Si no hay ningun error se muestra un mensaje diciendo que el usuario
             //se agrego bien

             Swal.fire(
                "Correcto!",
                "Ya registramos a <strong>" + data.nombre + " " + data.apellido + "</strong> como nuevo <strong>usuario</strong>!",
                "success"
              );
             resetUploadForm();               
        }
    })

        .catch(error => {
                //Si hay algun error se muestra un mensaje diciendo que el usuario
                //no se pudo guardar y se intente nuevamente

                  Swal.fire("Maldición!", "Hubo un problema inesperado!", "error");
            }
        )
  });

  function fechaActual() {
    let fecha = new Date();
    const dd = String(fecha.getDate()).padStart(2, "0");
    const mm = String(fecha.getMonth() + 1).padStart(2, "0"); //January is 0!
    const yyyy = fecha.getFullYear();

    fecha = dd + "/" + mm + "/" + yyyy;
    return fecha;
  }



  function convertirFecha(fecha) {
    return fecha.split("/").reverse().join("-");
  }

  function resetUploadForm() {
    document.querySelector("#nombre").value = "";
    document.querySelector("#apellido").value = "";
    document.querySelector("#dni").value = "";
    document.querySelector("#fecha-ingreso").value = fechaActual();
    document.querySelector("#calle").value = "";
    document.querySelector("#numero").value = "";
    document.querySelector("#localidad").value = "";
    document.querySelector("#provincia").value = "";
  }

  (function () {
    let pathname = window.location.pathname;
    if (pathname === "/") {
      document.querySelector(".nav .nav-item a:first").addClass("active");
    } else if (pathname == "/usuario-lista.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
    }
  })();
});

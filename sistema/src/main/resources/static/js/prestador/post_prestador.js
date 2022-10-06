window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará del nuevo prestador
    const formulario = document.querySelector('#add_new_prestador');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
       
        //creamos un JSON que tendrá los datos del nuevo prestador
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            matricula: document.querySelector('#matricula').value,

        };

        //invocamos utilizando la función fetch la API prestadores con el método POST que guardará
        //el prestador que enviaremos en formato JSON
        const url = URL_BASE + '/prestadores';
        const settings = {
            method: 'POST',
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
                //Si hay algun error se muestra un mensaje diciendo que el prestador
                //no se pudo guardar y se intente nuevamente

                Swal.fire("Maldición!", data, "error");
            }
            else{
                 //Si no hay ningun error se muestra un mensaje diciendo que el prestador
                 //se agrego bien

                 Swal.fire(
                    "Correcto!",
                    "Ya registramos a <strong>" + data.nombre + " " + data.apellido + "</strong> como nuevo <strong>prestador</strong>!",
                    "success"
                  );
                 resetUploadForm();               
            }
        })

            .catch(error => {
                    //Si hay algun error se muestra un mensaje diciendo que el prestador
                    //no se pudo guardar y se intente nuevamente

                      Swal.fire("Maldición!", "Hubo un problema inesperado!", "error");
                }
            )
    });


    function resetUploadForm(){
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
         document.querySelector('#matricula').value = "";

    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/prestador-lista.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});
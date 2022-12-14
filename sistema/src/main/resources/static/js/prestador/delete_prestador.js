function deleteBy(id) {
  Swal.fire({
    title: "Estás seguro?",
    text: "Se va a eliminar el prestador con Id " + id,
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Claro que sí!",
    cancelButtonText: "No, mejor no",
  }).then((result) => {
    if (result.isConfirmed) {
      //con fetch invocamos a la API de prestadores con el método DELETE
      //pasandole el id en la URL
      const url = URL_BASE + "/prestadores/" + id;
      const settings = {
        method: "DELETE",
      };
      fetch(url, settings).then((response) => response.json())
      .then(data => {

        //borrar la fila del prestador eliminado
        let row_id = "#tr_" + id;

        document.querySelector(row_id).remove();
          Swal.fire({
            title: "Eliminado!",
            text: data,
            icon: "success",
            confirmButtonText: "Perfecto!"
          });
   })
   .catch(error => {
           //Si hay algun error se muestra un mensaje diciendo que el prestador
           //no se pudo borrar y se intente nuevamente

             Swal.fire("Maldición!", "Hubo un problema!", "error");
    });

    }
  });
}

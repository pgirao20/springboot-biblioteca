// libro-areas.js
document.addEventListener("DOMContentLoaded", () => {

  // ------ FILTRO DE BÚSQUEDA ------
  const buscador = document.getElementById("buscadorAreas");
  const filas = document.querySelectorAll("#tablaAreas tbody tr");

  if (buscador && filas.length > 0) {
    buscador.addEventListener("keyup", () => {
      const texto = buscador.value.toLowerCase().trim();
      filas.forEach(fila => {
        const coincide = fila.textContent.toLowerCase().includes(texto);
        fila.style.display = coincide ? "" : "none";
      });
    });
  }

  // ------ ALERTAS SWEETALERT (usa variables Thymeleaf mediante script inline) ------
  if (window.alertas) {
    const { ok, warn, error } = window.alertas;

    if (ok) {
      Swal.fire({
        icon: 'success',
        title: 'Éxito',
        text: ok,
        showConfirmButton: false,
        timer: 1000,
        timerProgressBar: true
      });
    }

    if (warn) {
      Swal.fire({
        icon: 'warning',
        title: 'Atención',
        text: warn,
        confirmButtonText: 'Entendido'
      });
    }

    if (error) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: error,
        confirmButtonColor: '#d33'
      });
    }
  }

});

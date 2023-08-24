$(document).ready(function() {
   // on ready
});


async function registrarHuesped() {
  let huesped = {};
  let usuario = {};
  
  huesped.nombre = document.getElementById('txtNombre').value;
  huesped.apellido = document.getElementById('txtApellido').value;
  huesped.fechaDeNacimiento = document.getElementById('txtFechaDeNacimiento').value;
  huesped.nacionalidad = document.getElementById('txtNacionalidad').value;
  huesped.phone = document.getElementById('txtPhone').value;

  usuario.usuario = document.getElementById('txtUsuario').value;
  usuario.password = document.getElementById('txtPassword').value;

  let repetirPassword = document.getElementById('txtRepetirPassword').value;

  if (repetirPassword != datos.password) {
    alert('La contraseña que escribiste es diferente.');
    return;
  }

  const requestHuesped = await fetch('api/huespedes', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(huesped)
  });

  const requestUsuario = await fetch('api/usuarios', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(usuario)
  });

  alert("La cuenta fue creada con exito!");
  window.location.href = 'login.html'

}

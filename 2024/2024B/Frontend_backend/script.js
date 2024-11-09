const apiURL = "http://localhost:8080/ApiUsuarios/";

async function listarUsuarios(){

    //invocar método de la API de forma asíncrona:
    //usar la ruta que corresponde al método "listar todos" o "select all"
    //fetch usa de forma implícita un método "GET"
    const response = await fetch(apiURL);
    //obtener resultdado del "response" en formato json
    const usuarios = response.json();
    //elemento HTML en el cual se va a renderizar:
    const listaUsuarios = document.querySelector("#usuarios-table tbody");

    //Pendiente: Validar si usuarios es != null
    usuarios.array.forEach(usuario => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${usuario.id}</td>
            <td>${usuario.nombre}</td>
            <td>${usuario.email}</td>
        `;
        listaUsuarios.appendChild(fila);
    });
}

async function crear_editar_Usuario(params) {

    //capturar información de elementos HTML:
    const id = document.getElementById("usuario-id").value;
    const nombre = document.getElementById("nombre").value;
    const email = document.getElementById("email").value;

    //se crea un objeto JSON que tiene la info que se va a enviar
    const usuario = { nombre, email};
    //El método es "POST" cuando se va a crear el registro por primera vez:
    let method = "POST";
    let url = apiURL;

    //se cambia al método "PUT" cuando se va a actualizar:
    if(id)
    {
        method = "PUT";
        url = `${apiURL}/${id}`;
    }

    const response = await fetch(url,
        {
            method,
            headers: {'Content-type':'application/json'},
            body: JSON.stringify(usuario)
        }
    );

    if(response.ok)
    {
        document.getElementById("usuario-id").value = '';
        document.getElementById("email").value = '';
        document.getElementById("nombre").value = '';
        listarUsuarios();
    }

    
}

function editarUsuario(id, nombre, email){

}

async function delete_usuario(params) {
    
}

document.addEventListener("DOMContentLoaded",listarUsuarios);


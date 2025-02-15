
//variable global para lista de las tareas:
let tasks = [];

//agregar una tarea nueva con info del formulario:
document.getElementById("taskForm").addEventListener("submit", function(event){

    event.preventDefault(); //evitar envío del formulario de forma automática

    //obtener valores del formulario:
    const title = document.getElementById("title").value;
    const date = document.getElementById("date").value;
    const status = document.getElementById("status").value;

    //crear tarea como un diccionario o registro y agregar a la lista:
    var newTask = {title, date, status};
    tasks.push(newTask);
    //limpiar formulario:
    this.reset();
    showTasks(); 

 });


//mostrar tareas creadas:
function showTasks()
{
    const taskListElement = document.getElementById("taskList");
    taskListElement.innerHTML = "";

    tasks.forEach( ( task, i) => {  

        //por cada elemento de la lista de tareas, crear un nuevo elemento html:
        const newItem = document.createElement("div");
        newItem.innerHTML = `
                <h3>${task.title}</h3>
                <p><strong>Fecha</strong>${task.date}</p>
                <p><strong>Estado:</strong>${task.status}</p>
                `;
        taskListElement.appendChild(newItem);

      }   );

}

//eliminar una tarea seleccionada:
function deleteTask()
{
    
}

//editar una tarea seleccionada:
function editTask()
{

}

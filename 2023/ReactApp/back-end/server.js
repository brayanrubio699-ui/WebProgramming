const mysql = require("mysql")
const express = require("express");
const { listen } = require("express/lib/application");

const app = express();
const PORT = 8001

const myConnection = mysql.createConnection(
    {
        host: '127.0.0.1',
        port:'3308',
        user: 'root',
        password:'admin',
        database:'prog_web'
    }
);

myConnection.connect  ( (err) =>
    {
        if(err)   {
            console.error("Error conectándose a la BD");
            return;
        }
        console.error("Conectado a la BD MySQL");
    }
);

app.listen(PORT, function() 
    {
        console.log("Aplicación funcionando en el puerto: " + PORT);
    }
);

//Rutas para las distintas operaciones del CRUD:

//( R ) Consultar todos los registros:
//con el método get manejamos el listado de todos los regitros de la tabla
// tiene dos objetos el req (request), y el resp (response):

//127.0.0.1:5000/list

app.get(    "/list",  (req, resp)  => {

    myConnection.query("select * from task"  , (err, results) =>
    {
        if(err)
        {
            console.error("Error conectándose a la base de datos",err);
            resp.status(500).send("Error consultando los registros")
        }
        resp.send(results);
    });
});

app.get(    "/list/:id",  (req, resp)  => {

    const id = req.params.id;

    myConnection.query("select * from task where id = ?",[id] , (err, results) =>
    {
        if(err)
        {
            console.error("Error conectándose a la base de datos",err);
            resp.status(500).send("Error consultando los registros")
        }
        resp.send(results);
    });
});

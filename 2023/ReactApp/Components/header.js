import React from "react"
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "../../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"

export default function Header(props)
{
    return(
        <div class="btn-group">
            <button type="button" class="btn btn-primary">Home</button>
            <div class="btn-group">
                <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown">Libroteca</button>
                <ul className="dropdown-menu">
                    <li><a class="dropdown-item" href="#">Prosa</a></li>
                    <li><a class="dropdown-item" href="#">Poesía</a></li>
                    <li><a class="dropdown-item" href="#">Ensayo</a></li>
                </ul>
            </div>
            <button type="button" class="btn btn-primary">Conócenos</button>            
        </div>
    );
}

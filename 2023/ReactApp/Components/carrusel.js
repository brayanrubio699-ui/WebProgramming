import React from "react"
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "../../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"
import image1 from "../img/imagen1.jpg"
import image2 from "../img/imagen2.jpg"
import image3 from "../img/imagen3.jpg"

export default function Carrusel(props)
{
    return(  
        <div id="demo" class="carousel slide" data-bs-ride="carousel">  
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
        </div>  
        <div class="carousel-inner">
            <div class="carousel-item active">
            <img src={image1} alt="" class="d-block w-100"  />
            </div>
            <div class="carousel-item">
            <img src={image2} alt="" class="d-block w-100"  />
            </div>
            <div class="carousel-item">
            <img src={image3} alt="" class="d-block w-100"  />
            </div>
        </div>


        <button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
        <span class="carousel-control-prev-icon"></span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
        <span class="carousel-control-next-icon"></span>
        </button>
        <br></br>
        </div>
    );
}

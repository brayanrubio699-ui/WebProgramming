const express = require("express")
const router = express.Router();
const tasksController = require("../controller/tasks.controller.js")

router
    .get("/", tasksController.get)
    .get("/:id", tasksController.getById)
    .post("/",tasksController.create)
    .put("/:id", tasksController.update)
    .delete("/:id",tasksController.delete)

module.exports = router;

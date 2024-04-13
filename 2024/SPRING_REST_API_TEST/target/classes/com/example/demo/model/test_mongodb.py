#Ejecutar previamente:
# pip install pymongo
import pymongo

# Establece la conexión con el servidor MongoDB (asegúrate de tener MongoDB en ejecución)
connString = "mongodb+srv://user_rs:BUofhv1dYD6sHMGQ@cluster0.8h8yu.mongodb.net/"
client = pymongo.MongoClient(connString)

# Selecciona la base de datos
dbName = "RecommendationSystem"
db = client[dbName]

# Selecciona la colección
collName = "CoursesRatings"
collection = db[collName]

#OPERACIONES BÁSICAS EN MONGODB














# pipeline = db.tutores.aggregate([
#     // Etapa 1: Filtrar el tutor por su ID
#     { $match: { _id: tutorId } },
#     // Etapa 2: Descomponer el array "cursos" en documentos separados
#     { $unwind: "$cursos" },
#     // Etapa 3: Filtrar el curso por su ID
#     { $match: { "cursos._id": cursoId } },
#     // Etapa 4: Opcional - Proyectar solo los campos necesarios del curso
#     { $project: { _id: "$cursos._id", nombre: "$cursos.nombre" } }
# ]);
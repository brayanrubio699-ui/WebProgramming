import { Model, DataTypes, MySQL } from "mysql";

const TASK_TABLE = 'tasks';

class Task extends Model
{
    static config(MySQL){
        return{
            MySQL,
            tableName: TASK_TABLE,
            modelName: Task,
            timeStamps: true
        }
    }
}
const TaskSchema = {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    },
    description:{
        type: DataTypes.STRING,
        primaryKey: false,
        allowNull: false,
        field: 'description'
    },
    responsible:{
        field: 'responsible',
        type: DataTypes.STRING,
        allowNull: false
    },
    completed:{
        field: 'completed',
        type: DataTypes.INTEGER,
        allowNull: false
    },
    priority:
    {
        field: 'priority',
        type: DataTypes.INTEGER,
        allowNull: false
    }
};

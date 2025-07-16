import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Scenario } from '../scenarios/scenario.schema';

@Injectable()
export class ScenarioService {
  constructor(
    @InjectModel(Scenario.name) private scenarioModel: Model<Scenario>,
  ) {}

  // Consultar todos los escenarios
  async findAll(): Promise<Scenario[]> {
    //la operación find es de mongo
    //select *
    return this.scenarioModel.find().exec();
  }

  // Consulta filtrada (por campo específico)
  async findByField(field: string, value: string): Promise<Scenario[]> {
    const query = {};
    query[field] = value;
    return this.scenarioModel.find(query).exec();
  }

  // Insertar un nuevo escenario
  async create(scenarioData: Partial<Scenario>): Promise<Scenario> {
    const scenario = new this.scenarioModel(scenarioData);
    return scenario.save();
  }

  // Actualizar un escenario existente por scenarioId
  async update(scenarioId: string, updateData: Partial<Scenario>): Promise<Scenario> {
    const updated = await this.scenarioModel.findOneAndUpdate(
      { scenarioId },
      updateData,
      { new: true },
    );
    if (!updated) throw new NotFoundException('Escenario no encontrado');
    return updated;
  }
}

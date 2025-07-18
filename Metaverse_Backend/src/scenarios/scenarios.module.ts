import { Module } from '@nestjs/common';

import { MongooseModule } from '@nestjs/mongoose';

//define el modelo de datos:
import { Scenario, ScenarioSchema } from '../scenarios/scenario.schema';
//
import { ScenarioService } from '../scenarios/scenarios.service';
import { ScenarioController } from '../scenarios/scenarios.controller';

@Module({
  imports: [    
    //se registra el modelo de datos
    MongooseModule.forFeature([{ name: Scenario.name, schema: ScenarioSchema }])
  ],
  //registra cuáles son las clases que resuelven dependencias
  providers: [ScenarioService],
  controllers: [ScenarioController],
})
export class ScenarioModule {}

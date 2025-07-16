import { Controller, Get, Post, Patch, Body, Param, Query } from '@nestjs/common';
import { ScenarioService } from '../scenarios/scenarios.service';

@Controller('scenarios')
export class ScenarioController {
  constructor(private readonly scenarioService: ScenarioService) {}

  // GET /scenarios
  @Get()
  getAllScenarios() {
    //select *
    return this.scenarioService.findAll();
  }

  // GET /scenarios/search?field=name&value=Laboratorio
  @Get('search')
  searchScenarios(
    @Query('field') field: string,
    @Query('value') value: string,
  ) {
    return this.scenarioService.findByField(field, value);
  }

  // POST /scenarios
  @Post()
  createScenario(@Body() data: any) {
    return this.scenarioService.create(data);
  }

  // PATCH /scenarios/:scenarioId
  @Patch(':scenarioId')
  updateScenario(
    @Param('scenarioId') scenarioId: string,
    @Body() data: any,
  ) {
    return this.scenarioService.update(scenarioId, data);
  }
}

import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

@Schema({ timestamps: true })
export class Scenario extends Document {
  @Prop({ required: true, unique: true })
  scenarioId: string;

  @Prop({ required: true })
  name: string;

  @Prop()
  description: string;

  @Prop()
  maxScore: number;

  @Prop({ default: 'medio' })
  difficultyLevel: string;

  @Prop({ default: true })
  isActive: boolean;
}

export const ScenarioSchema = SchemaFactory.createForClass(Scenario);

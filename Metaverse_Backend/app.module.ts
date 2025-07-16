import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UsersModule } from './users/users.module';
import { ScenarioModule } from './scenarios/scenarios.module';
import { MongooseModule } from '@nestjs/mongoose';

@Module({
  imports: [
    //MongooseModule.forRoot('mongodb+srv://usc_metaverse:tBCFjDfVm2KaPmJU@cluster0.8h8yu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0'),
    MongooseModule.forRoot('mongodb+srv://usc_metaverse:BsciGGvEEHObvGPk@cluster0.8h8yu.mongodb.net/USC_Metaverse?retryWrites=true&w=majority'),
    //MongooseModule.forRoot('mongodb+srv://<usuario>:<password>>@cluster0.8h8yu.mongodb.net/<BD>?retryWrites=true&w=majority'),
    UsersModule, 
    ScenarioModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DataComponent} from "./data/data.component";
import {StatisticsComponent} from "./statistics/statistics.component";

//mapping
const routes: Routes = [
  {path: 'statistics', component: StatisticsComponent},
  {path: '**', component: DataComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

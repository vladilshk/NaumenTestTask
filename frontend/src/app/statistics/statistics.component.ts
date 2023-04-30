import { Component } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {tsCastToAny} from "@angular/compiler-cli/src/ngtsc/typecheck/src/ts_util";

interface Statistics {
  name: string;
  age: number;
}

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  maxAgeName = "";
  maxAge = 0;
  statisticsPairs: any[] | undefined;

  statisticIsChosen = false;
  maxAgeIsChosen = false;

  maxAgeText = "Показать самый большой возраст";
  statisticsText = "Посмотреть статистику";

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {

  }

  getMaxAge(): void {
    if (!this.maxAgeIsChosen) {
      this.http.get<JSON>('http://localhost:8080/api/name/maxAge').subscribe(
        {
          next: ((response: any) => {
            /* console.log(response);*/
            console.log(response.data);
            this.maxAgeName = response.name;
            this.maxAge = response.age;
          }),

          error: (error => {
            console.log(error);
          })
        }
      );
      this.maxAgeIsChosen = true;
      this.maxAgeText = "Скрыть самый большой возраст";
    } else {
      this.maxAgeIsChosen = false;
      this.maxAgeText = "Показать самый большой возраст";

    }
  }



  getStatistics(): void{
    if (!this.statisticIsChosen) {
      this.http.get<Statistics[]>('http://localhost:8080/api/name/statistics').subscribe(
        {
          next: ((response: any) => {
            /* console.log(response);*/
            console.log(response);
            console.log(response.age);
            this.statisticsPairs = response;
            console.log(this.statisticsPairs)

          }),

          error: (error => {
            console.log(error);
          })
        }
      );
      this.statisticIsChosen = true;
      this.statisticsText = "Скрыть статистику";
    } else {
      this.statisticIsChosen = false;
      this.statisticsText = "Посмотреть статистику";

    }
  }

  openDataPage(): void{
    this.router.navigate(["/"]);
  }
}

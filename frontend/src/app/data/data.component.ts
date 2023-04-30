import {Component} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.css']
})

export class DataComponent{
  name = "";
  chosenName = ""
  age = 0;
  isChosen = false;

  constructor(
    private http: HttpClient,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {

  }

  /*
    ngOnInit(): void {
      /!*this.http.get<any>("http://localhost:8080/user/test").subscribe(
        {
          next: ((response: any) => {
            console.log(response);
            console.log(response.data)
          }),

          error: (error => {
            console.log(error);

          })
        }
      );*!/

     /!* const data = {email: "test@gmail.com"}
      const body = JSON.stringify(data);

      this.http.post<any>("http://localhost:8080/user/data",
        body, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).subscribe(
        {
          next: ((response: any) => {
            console.log(response);
            console.log(response.data)
          }),

          error: (error => {
            console.log(error);

          })
        }
      );*!/

      this.activatedRoute.queryParams.subscribe(params => {
        this.email = params['email'];
      });
    }

    changeName(): void {

      const data = {email: "test@gmail.com"}
      const body = JSON.stringify(data);

      this.http.post<any>("http://localhost:8080/user/data",
        body, {
          headers: {
            'Content-Type': 'application/json'
          }
        }).subscribe(
        {
          next: ((response: any) => {
            console.log(response);
            console.log(response.data)

            this.data = response.data;
          }),

          error: (error => {
            console.log(error);

          })
        }
      );
    }
  */

  getAgeOfTheName(): void {
    if (this.name == "vova"){
      this.age = 10;
    }
    this.age=55;
    this.chosenName = this.name;
    this.isChosen=true;
  }

  changeName(): void {

    let param = new HttpParams().set('name',  this.name)
    this.http.get<JSON>('http://localhost:8080/api/name/', {params: param}).subscribe(
      {
        next: ((response: any) => {
         /* console.log(response);*/
          console.log(response.data);
          this.age = response.age;
          this.chosenName = this.name;
        }),

        error: (error => {
          console.log(error);
        })
      }
    );
    this.isChosen = true;
  }

  openStatisticsPage(){
    this.router.navigate(["/statistics"]);
  }
}

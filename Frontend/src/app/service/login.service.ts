import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from "src/app/model/user.model";
import { Observable, Subject } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
    
  }

  validateLoginDetails(user: User) {
    console.log(user);
    window.sessionStorage.setItem("userdetails",JSON.stringify(user));
    console.log(window.sessionStorage.getItem("userdetails"));
    return this.http.get(environment.rooturl + "/api/login", { observe: 'response',withCredentials: true });
  }

}
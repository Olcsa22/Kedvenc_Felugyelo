import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from "src/app/model/user.model";
import { Observable, Subject } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginService } from './login.service';


@Injectable({
    providedIn: 'root'
  })
  export class RegisterService {

    constructor(private http: HttpClient, loginservice:LoginService) {
      
    }
  
    register(user: User) {
      let httpheader = new HttpHeaders();
      return this.http.post(environment.rooturl + "/api/user/add",user, { observe: 'response',withCredentials: false,
    headers:new HttpHeaders({'Authorization':'null'}) });
    }
  
  }
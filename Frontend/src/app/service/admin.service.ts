import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { User } from '../model/user.model';
import { Dog } from '../model/dog.model';
import { Cat } from '../model/cat.model';
import { Hamster } from '../model/hamster.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }

  getAllUsers()
  {
    return this.http.get(environment.rooturl + "/api/admin/getallusers",{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
  }

  getAllDogs()
  {
    return this.http.get(environment.rooturl + "/api/admin/animals/getalldogs",{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
  }

  getAllCats()
  {
    return this.http.get(environment.rooturl + "/api/admin/animals/getallcats",{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
  }

  getAllHamsters()
  {
    return this.http.get(environment.rooturl + "/api/admin/animals/getallhamsters",{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
  }

}

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
  export class HomeService {

    constructor(private http:HttpClient)
    {}


    //-------------------------------------Felhasználói állatok lekérdezése--------------------------------------------
    getUserDogs()
    {
      const httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        withCredentials:true
      };
      //params:new HttpParams().set('user',JSON.stringify(user))
        return this.http.get(environment.rooturl + "/api/profile/animals/mydogs",{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    getUserCats()
    {
      const httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        withCredentials:true
      };
      //params:new HttpParams().set('user',JSON.stringify(user))
        return this.http.get(environment.rooturl + "/api/profile/animals/mycats",{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    getUserHamsters()
    {
      const httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        withCredentials:true
      };
      //params:new HttpParams().set('user',JSON.stringify(user))
        return this.http.get(environment.rooturl + "/api/profile/animals/myhamsters",{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    //-------------------------------------Állatok hozzáadása--------------------------------------------

    addUserDog(dog: Dog)
    {
      return this.http.post(environment.rooturl + "/api/add/adddog",dog,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    addUserCat(cat: Cat)
    {
      return this.http.post(environment.rooturl + "/api/add/addcat",cat,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    addUserHamster(hamster: Hamster)
    {
      return this.http.post(environment.rooturl + "/api/add/addhamster",hamster,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }


    //-------------------------------------Állatok törlése--------------------------------------------

    deleteUserDog(dogid:number)
    {
      return this.http.post(environment.rooturl + "/api/profile/animals/deletedog",dogid,{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    deleteUserCat(catid:number)
    {
      return this.http.post(environment.rooturl + "/api/profile/animals/deletecat",catid,{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    deleteUserHamster(hamsterid:number)
    {
      return this.http.post(environment.rooturl + "/api/profile/animals/deletehamster",hamsterid,{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    deleteUser(userid:number)
    {
      return this.http.post(environment.rooturl + "/api/admin/deleteuser",userid,{observe:'body', withCredentials:true});
    }

    //-------------------------------------Állat állapotainak lekérése--------------------------------------------


    getDogNeeds(dogid:Number)
    {
      return this.http.post(environment.rooturl + "/api/profile/animals/getdogneeds",dogid,{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    getCatNeeds(catid:Number)
    {
      return this.http.post(environment.rooturl + "/api/profile/animals/getcatneeds",catid,{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    getHamsterNeeds(hamsterid:Number)
    {
      return this.http.post(environment.rooturl + "/api/profile/animals/gethamsterneeds",hamsterid,{observe:'body', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})});
    }

    //-------------------------------------Állat állapotainak frissítése--------------------------------------------

    updateDogFeeding(dogid,data)
    {
      let params = {
        "dogid": dogid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/feeddog",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }

    updateCatFeeding(catid,data)
    {
      let params = {
        "catid": catid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/feedcat",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }

    updateHamsterFeeding(hamsterid,data)
    {
      let params = {
        "hamsterid": hamsterid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/feedhamster",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }

    updateDogVaccinating(dogid,data)
    {
      let params = {
        "dogid": dogid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/vaccinatedog",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }

    updateCatVaccinating(catid,data)
    {
      let params = {
        "catid": catid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/vaccinatecat",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }

    updateDogWalking(dogid,data)
    {
      let params = {
        "dogid": dogid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/walkdog",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }

    updateHamsterCleaning(hamsterid,data)
    {
      let params = {
        "hamsterid": hamsterid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/cleanhamster",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }

    updateHamsterToothWearer(hamsterid,data)
    {
      let params = {
        "hamsterid": hamsterid,
        "data": data
      };
        console.log(data);
      return this.http.post(environment.rooturl + "/api/profile/animals/changetoothwearer",params,{observe:'response', withCredentials:true,
      headers: new HttpHeaders({'Content-Type':'application/json'})})
    }



  }
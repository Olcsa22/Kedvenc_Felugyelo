import { Component, OnInit } from '@angular/core';
import { Dog } from 'src/app/model/dog.model';
import { Cat } from 'src/app/model/cat.model';
import { Hamster } from 'src/app/model/hamster.model';
import { User } from 'src/app/model/user.model';
import { HomeService } from 'src/app/service/home.service';
import { DogUpdateDialogComponent } from '../dog-update-dialog/dog-update-dialog.component';
import { NeedsDialogComponent } from '../needs-dialog/needs-dialog.component';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, NgForm } from '@angular/forms';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { Router } from '@angular/router';
import { AdminService } from 'src/app/service/admin.service';
import { HamsterUpdateDialogComponent } from '../hamster-update-dialog/hamster-update-dialog.component';
import { CatUpdateDialogComponent } from '../cat-update-dialog/cat-update-dialog.component';
import { ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  user = new User();
  allDogs = new Array<Dog>();
  allCats = new Array<Cat>();
  allHamsters = new Array<Hamster>();
  allusers = new Array<User>();

  constructor(private adminservice: AdminService, private homeService : HomeService, public matdialog:MatDialog) { }

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    console.log(this.user);
    console.log(this.user.roles![0]['name']);
    this.getAllUsers();
    this.getAllDogs();
    this.getAllCats();
    this.getAllHamsters();
  }
  
  //---------------------------------Lekérések-----------------------------

  getAllUsers()
  {
    if(this.user){
      this.adminservice.getAllUsers().subscribe
      (
        (responseData:any) => {
          var array = new Array();
          this.allusers = responseData;
          console.log(this.allusers);
        }
      )
     
    
    }
  }

  getAllDogs()
  {
    if(this.user){
      this.adminservice.getAllDogs().subscribe
      (
        (responseData:any) => {
          var array = new Array<Dog>();
          array=responseData;
          for(let i=0; i<array.length;i++)
          {
            array[i].url=this.randomDogIcon();
            array[i].needs=this.getDogNeeds(array[i].id);
          }
          this.allDogs = array;
        }
      )
     
    
    }
  }

  getAllCats()
  {
    if(this.user){
      this.adminservice.getAllCats().subscribe
      (
        (responseData:any) => {
          var array = new Array<Cat>();
          array=responseData;
          for(let i=0; i<array.length;i++)
          {
            array[i].url=this.randomCatIcon();
            array[i].needs=this.getCatNeeds(array[i].id);
          }
          this.allCats = array;
        }
      )
     
    
    }
  }

  getAllHamsters()
  {
    if(this.user){
      this.adminservice.getAllHamsters().subscribe
      (
        (responseData:any) => {
          var array = new Array<Hamster>();
          array=responseData;
          for(let i=0; i<array.length;i++)
          {
            array[i].url=this.randomHamsterIcon();
            array[i].needs=this.getHamsterNeeds(array[i].id);
          }
          this.allHamsters = array;
        }
      )
     
    
    }
  }

  deleteUser(userid:any)
  {
    this.homeService.deleteUser(Number(userid)).subscribe
      (
        sesponsedata =>
        {
          console.log(sesponsedata);
        }
      )
      window.location.reload();
      
  }

  deleteDog(dogid:any)
  {
      console.log(dogid);
      this.homeService.deleteUserDog(Number(dogid)).subscribe
      (
        sesponsedata =>
        {

        }
      )
      window.location.reload();
  }

  

  getDogNeeds(dogid:any) :String[] 
  {
     let needs=this.homeService.getDogNeeds(Number(dogid));
     let needArray = new Array<String>();
     needs.forEach
     (
       (need:any)=>
       {
        console.log("Need logging:");
        console.log(need);
        console.log("Sentences");
        need.forEach
        (
          
          (sentence:any)=>
          {
            
            console.log(sentence);
            needArray.push(<String>sentence);
          }
        )
          
       }
       )
       this.allDogs.forEach(function (value)
       {
         if(value.id==dogid)
         {
           value.needs=needArray;
         }
       }
       )
       console.log("Need array logging:");
       console.log(needArray);
       return needArray;
  }

  openCatDialog(catneeds:any): void {
    

    this.matdialog.open(NeedsDialogComponent, {data:catneeds,width: '500px',
    height: '500px'});
  }

  openCatOptionsDialog(dogid:any): void {
    
    let dog= new Dog();
    for(let i=0;i<this.allDogs.length;i++)
    {
      console.log(i);
      if(this.allDogs[i].id===dogid)
      {
        dog=this.allDogs[i];
        break;
      }
    }

    this.matdialog.open(DogUpdateDialogComponent, {data:dog,width: '600px',
    height: '550px'});
  }

  deleteHamster(hamsterid:any)
  {
    console.log(hamsterid);
    this.homeService.deleteUserHamster(Number(hamsterid)).subscribe
    (
      sesponsedata =>
      {

      }
    )
    window.location.reload();
  }

  getHamsterNeeds(hamsterid:any) :String[] 
  {
     let needs=this.homeService.getHamsterNeeds(Number(hamsterid));
     let needArray = new Array<String>();
     needs.forEach
     (
       (need:any)=>
       {
          needArray.push(<String>need);
       }
       )
       this.allHamsters.forEach(function (value)
       {
         if(value.id==hamsterid)
         {
           value.needs=needArray;
         }
       }
       )
       console.log(needArray);
       return needArray;
  }

  openHamsterDialog(hamsterneeds:any): void {
    

    this.matdialog.open(NeedsDialogComponent, {data:hamsterneeds,width: '500px',
    height: '500px'});
  }

  openHamsterOptionsDialog(hamsterid:any): void {
    
    let hamster= new Hamster();
    for(let i=0;i<this.allHamsters.length;i++)
    {
      console.log(i);
      if(this.allHamsters[i].id===hamsterid)
      {
        hamster=this.allHamsters[i];
        break;
      }
    }

    this.matdialog.open(HamsterUpdateDialogComponent, {data:hamster,width: '600px',
    height: '550px'});
  }

  deleteCat(catid:any)
  {
    console.log(catid);
      this.homeService.deleteUserCat(Number(catid)).subscribe
      (
        (responseData:any)=>
        {
        }
      )
      window.location.reload();
  }

  getCatNeeds(catid:any) :String[] 
  {
     let needs=this.homeService.getCatNeeds(Number(catid));
     let needArray = new Array<String>();
     needs.forEach
     (
       (need:any)=>
       {
          needArray.push(<String>need);
       }
       )
       this.allCats.forEach(function (value)
       {
         if(value.id==catid)
         {
           value.needs=needArray;
         }
       }
       )
       console.log(needArray);
       return needArray;
  }
     

  openDialog(dogneeds:any): void {
    

    this.matdialog.open(NeedsDialogComponent, {data:dogneeds,width: '500px',
    height: '500px'});
  }

  openOptionsDialog(catid:any): void {
    
    let cat= new Cat();
    for(let i=0;i<this.allCats.length;i++)
    {
      console.log(i);
      if(this.allCats[i].id===catid)
      {
        cat=this.allCats[i];
        break;
      }
    }

    this.matdialog.open(CatUpdateDialogComponent, {data:cat,width: '600px',
    height: '550px'});


    
  }

  randomCatIcon(): String
  {
    
    const rndInt = Math.floor(Math.random() * 5) + 1
     let url = "../../../assets/caticon"+rndInt+".webp";
     console.log(url);
     return url;
  }

  randomDogIcon(): String
  {
    
    const rndInt = Math.floor(Math.random() * 5) + 1
     let url = "../../../assets/dogicon"+rndInt+".webp";
     console.log(url);
     return url;
  }

  randomHamsterIcon(): String
  {
    
    const rndInt = Math.floor(Math.random() * 5) + 1
     let url = "../../../assets/hamstericon"+rndInt+".webp";
     console.log(url);
     return url;
  }

}



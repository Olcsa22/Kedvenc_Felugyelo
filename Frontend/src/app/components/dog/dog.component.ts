import { HttpResponse } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm } from '@angular/forms';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { Router } from '@angular/router';
import { Dog } from 'src/app/model/dog.model';
import { User } from 'src/app/model/user.model';
import { HomeService } from 'src/app/service/home.service';
import { DogUpdateDialogComponent } from '../dog-update-dialog/dog-update-dialog.component';
import { NeedsDialogComponent } from '../needs-dialog/needs-dialog.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dog',
  templateUrl: './dog.component.html',
  styleUrls: ['./dog.component.css']
})
export class DogComponent implements OnInit {
  dogs = Array<Dog>();
  user = new User();
  model = new Dog();
  addForm! :FormGroup;

  constructor(private homeService: HomeService, @Inject(FormBuilder) private builder:FormBuilder, public matdialog:MatDialog,
  private _snackBar: MatSnackBar) { }

  createForm() : void {
    this.addForm = this.builder.group({
        dName: [''],
        dLastFeeding: [''],
        dFeedingInterval: [''],
        dVaccinated: [''],
        dLastWalking:['']
    });
}
  
  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    this.getAllDogs();
  }

  getAllDogs()
  {
    
    if(this.user){
      this.homeService.getUserDogs().subscribe
      (
        (responseData:any) => {
          var array = new Array<Dog>();
          array=responseData;
          for(let i=0; i<array.length;i++)
          {
            array[i].url=this.randomIcon();
            array[i].needs=this.getDogNeeds(array[i].id);
            console.log(array[i].needs);
          }
          this.dogs = array;
        }
      )
     
    
    }
    
  }

  addDog(ngForm: NgForm)
  {
    if(this.user)
    {
      var inputvalue = (document.getElementById('feedingInterval') as HTMLInputElement).value;
      if(!(document.getElementById('feedingInterval') as HTMLInputElement).value || !(document.getElementById('animalname') as HTMLInputElement).value 
      || !(document.getElementById('lastFeeding') as HTMLInputElement).value || !(document.getElementById('vaccinated') as HTMLInputElement).value 
      || !(document.getElementById('lastWalking') as HTMLInputElement).value)
      {
        this._snackBar.open("Minden mezőt tölts ki!", "Bezárás");
      }
      else if(isNaN(parseInt(inputvalue)) ||parseInt(inputvalue)<1)
      {
        this._snackBar.open("Az etetési intervallum pozitív egész szám kell legyen!","Bezárás");
      }
      else
      {
      this.model.owner=this.user.id;
      let newDog;
      this.homeService.addUserDog(this.model).subscribe
      (
        (responseData:any)=>
        {
          newDog = <any> responseData.body;
        }
      )
        this.dogs.push(newDog);
        window.location.reload();
      }
    
    }
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
       this.dogs.forEach(function (value)
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

  openDialog(dog:String[]): void {
    

    this.matdialog.open(NeedsDialogComponent, {data:dog,width: '500px',
    height: '500px'});
  }

  openOptionsDialog(dogid:any): void {
    
    let dog= new Dog();
    for(let i=0;i<this.dogs.length;i++)
    {
      console.log(i);
      if(this.dogs[i].id===dogid)
      {
        dog=this.dogs[i];
        break;
      }
    }

    this.matdialog.open(DogUpdateDialogComponent, {data:dog,width: '600px',
    height: '550px'});


    
  }

  randomIcon(): String
  {
    
    const rndInt = Math.floor(Math.random() * 5) + 1
     let url = "../../../assets/dogicon"+rndInt+".webp";
     console.log(url);
     return url;
  }
}

  




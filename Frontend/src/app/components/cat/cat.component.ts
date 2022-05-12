import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Cat } from 'src/app/model/cat.model';
import { User } from 'src/app/model/user.model';
import { HomeService } from 'src/app/service/home.service';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { NeedsDialogComponent } from '../needs-dialog/needs-dialog.component';
import { CatUpdateDialogComponent } from '../cat-update-dialog/cat-update-dialog.component';
import { ViewEncapsulation} from '@angular/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-cat',
  templateUrl: './cat.component.html',
  styleUrls: ['./cat.component.css'],
  
})
export class CatComponent implements OnInit {

  cats = Array();
  user = new User();
  model = new Cat();

  constructor(private homeService: HomeService, public matdialog:MatDialog, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    this.getAllCats();
  }

  getAllCats()
  {
    
    if(this.user){
      this.homeService.getUserCats().subscribe
      (
        (responseData:any) => {
          console.log(responseData);
          var array = new Array<Cat>();
          array=responseData;
          for(let i=0; i<array.length;i++)
          {
            array[i].url=this.randomIcon();
            array[i].needs=this.getCatNeeds(array[i].id);
          }
          this.cats = array;
        }
      )
      
    }
    
  }

  addCat(ngForm: NgForm)
  {
    if(this.user)
    {
      var inputvalue = (document.getElementById('feedingInterval') as HTMLInputElement).value;
      if(!(document.getElementById('feedingInterval') as HTMLInputElement).value || !(document.getElementById('animalname') as HTMLInputElement).value 
      || !(document.getElementById('lastFeeding') as HTMLInputElement).value || !(document.getElementById('vaccinated') as HTMLInputElement).value )
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
      let newCat;
      this.homeService.addUserCat(this.model).subscribe
      (
        (responseData:any)=>
        {
          console.log(responseData);
          newCat = <any> responseData.body;
        }
      )
      this.cats.push(newCat);
      window.location.reload();
      }
    }
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
      this.cats.forEach(function (value)
      {
        if(value.id==catid)
        {
          value.needs=needArray;
        }
      }
      )
      console.log("Need array logging:");
      console.log(needArray);
      return needArray;
  }
     

  openDialog(cat:String[]): void {
    

    this.matdialog.open(NeedsDialogComponent, {data:cat,width: '500px',
    height: '500px'});
  }

  openOptionsDialog(catid:any): void {
    
    let cat= new Cat();
    for(let i=0;i<this.cats.length;i++)
    {
      console.log(i);
      if(this.cats[i].id===catid)
      {
        cat=this.cats[i];
        break;
      }
    }

    this.matdialog.open(CatUpdateDialogComponent, {data:cat,width: '600px',
    height: '550px'});


    
  }

  randomIcon(): String
  {
    
    const rndInt = Math.floor(Math.random() * 5) + 1
     let url = "../../../assets/caticon"+rndInt+".webp";
     console.log(url);
     return url;
  }

}

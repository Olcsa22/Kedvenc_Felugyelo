import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Hamster } from 'src/app/model/hamster.model';
import { User } from 'src/app/model/user.model';
import { HomeService } from 'src/app/service/home.service';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { NeedsDialogComponent } from '../needs-dialog/needs-dialog.component';
import { HamsterUpdateDialogComponent } from '../hamster-update-dialog/hamster-update-dialog.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-hamster',
  templateUrl: './hamster.component.html',
  styleUrls: ['./hamster.component.css']
})
export class HamsterComponent implements OnInit {

  hamsters = Array();
  user = new User();
  model = new Hamster();

  constructor(private homeService: HomeService, public matdialog:MatDialog,  private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    this.getAllHamsters();
  }


  getAllHamsters()
  {
    
    if(this.user){
      this.homeService.getUserHamsters().subscribe
      (
        (responseData:any) => {
          var array = new Array<Hamster>();
          array=responseData;
          for(let i=0; i<array.length;i++)
          {
            array[i].url=this.randomIcon();
            array[i].needs=this.getHamsterNeeds(array[i].id);
          }
          this.hamsters=array;
        }
      )
      
    }
  }

  randomIcon(): String
  {
    
    const rndInt = Math.floor(Math.random() * 5) + 1
     let url = "../../../assets/hamstericon"+rndInt+".webp";
     console.log(url);
     return url;
  }

  addHamster(ngForm: NgForm)
  {
    if(this.user)
    {
      var inputvalue = (document.getElementById('feedingInterval') as HTMLInputElement).value;
      if(!(document.getElementById('feedingInterval') as HTMLInputElement).value || !(document.getElementById('animalname') as HTMLInputElement).value 
      || !(document.getElementById('lastFeeding') as HTMLInputElement).value || !(document.getElementById('tooth') as HTMLInputElement).value
      || !(document.getElementById('lastCleaning') as HTMLInputElement).value )
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
      let newHamster;
      this.homeService.addUserHamster(this.model).subscribe
      (
        (responseData:any)=>
        {
          newHamster = <Hamster> responseData.body;
        }
      )
      this.hamsters.push(newHamster);
      window.location.reload();
    }
  }
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
      this.hamsters.forEach(function (value)
      {
        if(value.id==hamsterid)
        {
          value.needs=needArray;
        }
      }
      )
      console.log("Need array logging:");
      console.log(needArray);
      return needArray;
  }

  openDialog(hamster:String[]): void {
    

    this.matdialog.open(NeedsDialogComponent, {data:hamster,width: '500px',
    height: '500px',panelClass:'update-dialog'});
  }

  openOptionsDialog(hamsterid:any): void {
    
    let hamster= new Hamster();
    for(let i=0;i<this.hamsters.length;i++)
    {
      console.log(i);
      if(this.hamsters[i].id===hamsterid)
      {
        hamster=this.hamsters[i];
        break;
      }
    }

    this.matdialog.open(HamsterUpdateDialogComponent, {data:hamster,panelClass: 'dialog-container-custom'});


    
  }


}

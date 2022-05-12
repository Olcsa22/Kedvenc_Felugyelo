import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MatDialogRef} from "@angular/material/dialog";
import { Dog } from 'src/app/model/dog.model';
import { HomeService } from 'src/app/service/home.service';
import {MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-dog-update-dialog',
  templateUrl: './dog-update-dialog.component.html',
  styleUrls: ['./dog-update-dialog.component.css']
})
export class DogUpdateDialogComponent implements OnInit {

  model=new Dog();

  constructor(private dialogRef: MatDialogRef<DogUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) data, public service: HomeService
  , private _snackBar: MatSnackBar) { this.model=data;}

  ngOnInit(): void {
    
  }

  close() {
    
    window.location.reload();
  }

  updateLastFeeding(dogid:number, data:any)
  {
    this.service.updateDogFeeding(dogid,data).subscribe
    (
      responseData =>
      {
        this._snackBar.open("Sikeres frissítés!","Bezárás");
      }, (err : HttpErrorResponse) =>
      {
        this._snackBar.open(err.error,"Bezárás");
      }
    )
  }

  updatelastWalking(dogid:number,data:any)
  {
    this.service.updateDogWalking(dogid,data).subscribe
    (
      responseData =>
      {
        this._snackBar.open("Sikeres frissítés!","Bezárás");
      }, (err : HttpErrorResponse) =>
      {
        this._snackBar.open(err.error,"Bezárás");
      }
    )
  }

  updatelastVaccinating(dogid:number,data:any)
  {
    this.service.updateDogVaccinating(dogid,data).subscribe
    (
      responseData =>
      {
        this._snackBar.open("Sikeres frissítés!","Bezárás");
      }, (err : HttpErrorResponse) =>
      {
        this._snackBar.open(err.error,"Bezárás");
      }
    )
  }
}

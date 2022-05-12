import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MatDialogRef} from "@angular/material/dialog";
import { Cat } from 'src/app/model/cat.model';
import { HomeService } from 'src/app/service/home.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-cat-update-dialog',
  templateUrl: './cat-update-dialog.component.html',
  styleUrls: ['./cat-update-dialog.component.css']
})
export class CatUpdateDialogComponent implements OnInit {

  model=new Cat();

  constructor(private dialogRef: MatDialogRef<CatUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) data, public service: HomeService,
  private _snackBar:MatSnackBar) { this.model=data;}

  ngOnInit(): void {
    
  }

  close() {
    
    window.location.reload();
  }

  updateLastFeeding(catid:number, data:any)
  {
    this.service.updateCatFeeding(catid,data).subscribe
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

  updatelastVaccinating(catid:number,data:any)
  {
    this.service.updateCatVaccinating(catid,data).subscribe
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

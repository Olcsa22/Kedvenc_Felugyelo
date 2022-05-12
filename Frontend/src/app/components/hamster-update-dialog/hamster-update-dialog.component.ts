import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MatDialogRef} from "@angular/material/dialog";
import { Hamster } from 'src/app/model/hamster.model';
import { HomeService } from 'src/app/service/home.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-hamster-update-dialog',
  templateUrl: './hamster-update-dialog.component.html',
  styleUrls: ['./hamster-update-dialog.component.css']
})
export class HamsterUpdateDialogComponent implements OnInit {

  model=new Hamster();

  constructor(private dialogRef: MatDialogRef<HamsterUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) data, public service: HomeService,
  private _snackBar: MatSnackBar) { this.model=data;}

  ngOnInit(): void {
    this.dialogRef.addPanelClass('dialog-container-custom');
  }

  close() {
    
    window.location.reload();
  }

  updateLastFeeding(hamsterid:number, data:any)
  {
    this.service.updateHamsterFeeding(hamsterid,data).subscribe
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

  updatelastCleaning(hamsterid:number,data:any)
  {
    this.service.updateHamsterCleaning(hamsterid,data).subscribe
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

  updatelToothwearer(hamsterid:number,data:any)
  {
    this.service.updateHamsterToothWearer(hamsterid,data).subscribe
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

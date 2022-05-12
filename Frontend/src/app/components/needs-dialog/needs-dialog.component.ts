import { Component, Inject, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-needs-dialog',
  templateUrl: './needs-dialog.component.html',
  styleUrls: ['./needs-dialog.component.css']
})
export class NeedsDialogComponent implements OnInit {

  needs!: String[];

  constructor( private dialogRef: MatDialogRef<NeedsDialogComponent>, @Inject(MAT_DIALOG_DATA) data) 
  {
    console.log(data);
    this.needs=data;
  }

  ngOnInit(): void {
  }

  close() {
    
    this.dialogRef.close();
  }

}

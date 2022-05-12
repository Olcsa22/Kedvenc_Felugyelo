import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {

  user = new User();

  constructor(private http: HttpClient, private jwtHelper: JwtHelperService) { }

  ngOnInit(): void {
    this.user=JSON.parse(sessionStorage.getItem('userdetails')!);
    let rolearray = new Array<String>();
    for(let i=0; i<this.user?.roles!.length;i++)
    {
      rolearray.push(this.user?.roles![i]['name']);
    }
    this.user.roles=rolearray;
    console.log('A rangok: '+rolearray);
  }
}

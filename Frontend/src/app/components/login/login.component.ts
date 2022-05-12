import { Component, OnInit } from '@angular/core';
import { User } from "src/app/model/user.model";
import { NgForm } from '@angular/forms';
import { LoginService } from 'src/app/service/login.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  authStatus?: string;
  model = new User();

  constructor(private loginService: LoginService, private router: Router) {

   }

  ngOnInit(): void {

  }

  validateUser(loginForm: NgForm) {
    this.loginService.validateLoginDetails(this.model).subscribe(
      responseData => {
        window.sessionStorage.setItem("Authorization",responseData.headers.get('Authorization')!);
        this.model = <any> responseData.body;
        this.model.authStatus = 'AUTH';
        window.sessionStorage.setItem("userdetails",JSON.stringify(this.model));
        this.router.navigate(['home']);
      }, error => {
        console.log(error);
        window.sessionStorage.removeItem("Authorization");
        window.sessionStorage.removeItem("userdetails");
        this.router.navigate(['login']);
      });

  }

  getCookie(name: any) {
    let cookie = new Map();
    document.cookie.split(';').forEach(function(el) {
      let [k,v] = el.split('=');
      let name = k.trim();
      cookie.set(k.trim(),v);
    })
    return cookie.get(name);
  }


}
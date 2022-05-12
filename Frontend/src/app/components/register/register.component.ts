import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';
import { RegisterService } from 'src/app/service/register.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  model = new User();

  constructor(private loginService: LoginService, private router: Router, private registerservice: RegisterService) { }

  ngOnInit(): void {
  }

  register(register: NgForm)
  {
    console.log(this.model);
    this.registerservice.register(this.model).subscribe
    (
      responseData =>
      {
        if(responseData.status==200)
        {
        this.router.navigate(['login']);
        }
        else
        {
          console.log("Sikertelen regisztráció");
        }
      }

    )
    
  }

}

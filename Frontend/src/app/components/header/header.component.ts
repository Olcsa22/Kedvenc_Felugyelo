import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  
  user = new User();

  constructor() {
    
  }

  ngOnInit() {
    let userDetails = JSON.parse(sessionStorage.getItem('userdetails')!);
    console.log(userDetails);
    if(userDetails!=null && userDetails!='{}'){
      this.user = JSON.parse(sessionStorage.getItem('userdetails')||'{}');
      
      
    }
    else{
        
    }
  }

}
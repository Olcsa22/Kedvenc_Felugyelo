import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot,Router } from '@angular/router';
import { User } from '../model/user.model';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable()
export class AuthActivateRouteGuard implements CanActivate {
    user = new User();
    
    constructor(private router: Router, private jwtHelper: JwtHelperService){

    }

    canActivate(route:ActivatedRouteSnapshot, state:RouterStateSnapshot){
        this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
        try{
        if(!this.user || !sessionStorage.getItem('userdetails')|| !sessionStorage.getItem('Authorization')|| this.jwtHelper.isTokenExpired())
        {
            window.sessionStorage.removeItem('userdetails');
            window.sessionStorage.removeItem('Authorization');
            window.sessionStorage.setItem("XSRF-TOKEN","null");
            this.router.navigate(['login']);
            
        }
    }catch(e)
    {
        window.sessionStorage.removeItem('userdetails');
        window.sessionStorage.removeItem('Authorization');
        this.router.navigate(['login']);
        return false;
    }
        return this.user?true:false;
    }
}
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from '../service/login/login.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private login: LoginService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
    const auth=localStorage.getItem('token');
    const user = this.login.interpretToken(auth);
    
   
    if (auth) {
      if (user.userRole === 'ADMIN' && state.url.startsWith('/user')) {
        return this.router.parseUrl('/login');
      } else if (user.userRole === 'USER' && state.url.startsWith('/admin')) {
        return this.router.parseUrl('/login'); 
      }else 
      return true;
    } else {
      return this.router.parseUrl('/login');
    }
  }
}

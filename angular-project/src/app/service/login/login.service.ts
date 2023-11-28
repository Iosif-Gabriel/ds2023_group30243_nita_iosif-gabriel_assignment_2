import { Injectable } from '@angular/core';
import { AuthenticationRequest } from 'src/app/dtos/AuthenticationRequest';
import { AuthenticationResponse } from 'src/app/dtos/AuthenticationResponse';
import { UserDetailsDTO } from 'src/app/dtos/user-details-dto';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

 

  private baseUrl = 'http://localhost:8080/api/auth/authenticate';
  req:AuthenticationRequest
  constructor(private http: HttpClient,private toastr: ToastrService,private router: Router) { }

  private isAuthenticatedFlag = false;

  private userSubject: BehaviorSubject<UserDetailsDTO | null> = new BehaviorSubject<UserDetailsDTO | null>(null);
  user$: Observable<UserDetailsDTO | null> = this.userSubject.asObservable();


  authenticate(username: string, password: string): Observable<any> {
    const requestBody = { username, password };
    return this.http.post(this.baseUrl, requestBody);

  }

  interpretToken(token: string): UserDetailsDTO {

    if (token){
      const tokenParts = token.split('.');
      
      if (tokenParts.length === 3) {
        const payload = JSON.parse(atob(tokenParts[1]));
        let user = {id: payload.userId, username: payload.sub, password: payload.password ,userRole: payload.role[0].authority};
        
        
        localStorage.setItem("token",token);
        
        this.userSubject.next(user);
        
        return user; 
      }
    }

    return  {id: '0', username: '', password:" ",userRole:" "}
  }

  
  


  isAuthenticated(): boolean {
    return this.isAuthenticatedFlag;
  }


  

  getUser(): any  | null {
    const token= localStorage.getItem("token")
    const user = this.interpretToken(token);
    if (user!=null) {
      return user;
    }
    return null; 
  }

  logout() {
    
    this.isAuthenticatedFlag = false;
    localStorage.clear();


    this.router.navigate(['/login']);
  }
}

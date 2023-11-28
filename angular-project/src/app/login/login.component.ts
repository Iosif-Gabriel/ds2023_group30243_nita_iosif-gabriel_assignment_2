import { Component, OnInit } from '@angular/core';
import { LoginService } from '../service/login/login.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  username: string = '';
  password: string = '';

  isUser=false;

  constructor(private loginService:LoginService,private router:Router,private toastr: ToastrService){
    
  }

  ngOnInit() { 
    localStorage.clear();
    window.addEventListener('popstate', (event) => {
      
      localStorage.clear();
    });
  }  
 
  onSubmit() {
    console.log("onSubmit called");
    this.authenticateUser();
  
  }

  
  isAuthenticatedUser(): boolean {
    return this.isUser;
  }


  
  authenticateUser() {
    this.loginService.authenticate(this.username, this.password).subscribe(
      (response: any) => {
        const token = response.token;
        const user = this.loginService.interpretToken(token);
        
        if (user.userRole === 'ADMIN') {
          this.showToaster('success',"Admin logged in!");
          this.router.navigateByUrl("/admin-page");
          
        } else {
          this.showToaster('success',"User logged in!");
          this.router.navigateByUrl("/user-page");
          this.isUser = true;
        }
      },
      (error) => {
        this.showToaster('error', "Nume sau parola gresita!!");
        console.log('Eroare:', error);
      }
    );
  }


  showToaster(messageType: string,message:string) {
    switch (messageType) {
      case 'success':
        this.toastr.success(message);
        break;
      case 'info':
        this.toastr.info(message);
        break;
      case 'warning':
        this.toastr.warning(message);
        break;
      case 'error':
        this.toastr.error(message);
        break;
      default:
        this.toastr.info(message);
        break;
    }
  }
  

  
  



  
}

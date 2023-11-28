import { Component } from '@angular/core';

import { LoginService } from './service/login/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Proiect SD';
  isAdmin: boolean = false;
  
  constructor(private authService: LoginService) {
    
   
  }
  

 
}

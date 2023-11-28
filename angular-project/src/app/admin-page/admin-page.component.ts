import { Component } from '@angular/core';
import { LoginService } from '../service/login/login.service';
import { ToastrService } from 'ngx-toastr';
import { UserListComponent } from '../lists/user-list/user-list.component';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent {

  
 
  constructor(private login:LoginService){

  }

  logout(){
    this.login.logout();
  }





}

import { Component, Output, EventEmitter } from '@angular/core';
import { UserDetailsDTO } from '../dtos/user-details-dto';
import { UserService } from '../service/user/user.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-user-details-form',
  templateUrl: './user-details-form.component.html',
  styleUrls: ['./user-details-form.component.css']
})
export class UserDetailsFormComponent {

  userDTO:UserDetailsDTO=new UserDetailsDTO();

  @Output() closeForm = new EventEmitter<void>();

  isFormSubmitted = false;

  constructor(private userService:UserService,private toastr:ToastrService){

  }

  saveUser() {
    this.userService.createUser(this.userDTO).subscribe(
      data => {
      
        this.isFormSubmitted = true; 
        this.showToaster('success',"User created!");
        setTimeout(() => {
          
          
          location.reload();
        }, 2000); 
      },
      
      error => {console.log(error)
        this.showToaster('error',"Error!");
      }

    );
  }



  onSubmit(){
    
    console.log(this.userDTO);
    this.saveUser();
    
  }

  
  onCloseForm() {
    this.closeForm.emit();
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

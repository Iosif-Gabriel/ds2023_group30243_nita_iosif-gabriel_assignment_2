import { Component, OnInit, EventEmitter,Output } from '@angular/core';
import { UserDetailsDTO } from '../../dtos/user-details-dto';
import { UserService } from '../../service/user/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent  implements OnInit{ 


  userList: UserDetailsDTO[];

  userDTO: UserDetailsDTO;

  @Output() closeForm = new EventEmitter<void>();

  constructor(private userService:UserService,private toastr:ToastrService){

  }

 
  isEditing = false;

  ngOnInit(): void {
   
    this.getUsers();
  }

  private getUsers(){
  
    this.userService.getUserList().subscribe(data =>{
      this.userList=data;
    });
  }

  editUser(user: UserDetailsDTO) {
   
    this.userDTO = user;
    this.isEditing = true;
  }

  saveUser(user: UserDetailsDTO) {
    
    this.isEditing = false;
    console.log(user);
    this.userService.updateUser(user.id,user).subscribe(
      () => {
        this.showToaster('success',"User updated succsefully!");
        console.log("yeyeSAVE");
        setTimeout(() => {
            
          location.reload();
        }, 2000);
      },
      (error) => {
        this.showToaster('error',"Error update!");
        console.error('Error update user:', error);
      }
    );

  }

  resetUser(user: UserDetailsDTO) {
    this.isEditing = false;
  }

  deleteUser(user: UserDetailsDTO){
    this.isEditing = false;
    this.userService.deleteUser(user.id).subscribe(
      () => {
        this.showToaster('success',"User deleted succsefully!");
        console.log("yeyeDEL");
        setTimeout(() => {
            
          location.reload();
        }, 2000);
      },
      (error) => {
        this.showToaster('error',"Error delete!");
        console.error('Error deleting user:', error);
      }
    );
  }


  isUserDetailsFormOpen=false;
 

  openUserDetailsForm() {
    this.isUserDetailsFormOpen = true;
  }

  closeUserDetailsForm() {
    this.isUserDetailsFormOpen = false;
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

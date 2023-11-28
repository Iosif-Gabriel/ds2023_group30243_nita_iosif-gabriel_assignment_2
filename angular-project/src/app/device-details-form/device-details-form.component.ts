import { Component, Output, EventEmitter } from '@angular/core';
import { DeviceDTO } from '../dtos/device-dto';
import { DeviceService } from '../service/device/device.service';
import { UserService } from '../service/user/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-device-details-form',
  templateUrl: './device-details-form.component.html',
  styleUrls: ['./device-details-form.component.css']
})
export class DeviceDetailsFormComponent {

  deviceDTO:DeviceDTO=new DeviceDTO();
  @Output() closeForm = new EventEmitter<void>();

  username: string = '';

  constructor(private deviceService:DeviceService,private userService:UserService,private toastr:ToastrService){
    
  }
  isFormSubmitted = false;
  onSubmit() {
  
    this.saveDevice();
  }

  onCloseForm() {
    this.closeForm.emit();
  }
  saveDevice() {
    this.userService.getUserByUsername(this.username).subscribe((data: any) => {
      this.deviceDTO.ownerId = data;
  
      this.deviceService.createDevice(this.deviceDTO).subscribe(
        data => {
          this.isFormSubmitted = true;
          this.showToaster('success','Device created!');
          setTimeout(() => {
            
            location.reload();
          }, 2000);
        },
        error => {
          this.showToaster('error',"Error!");
          console.log(error)}
      );
    });
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

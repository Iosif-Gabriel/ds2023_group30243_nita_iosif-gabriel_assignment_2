import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { UserListComponent } from './lists/user-list/user-list.component';
import { DeviceListComponent } from './lists/device-list/device-list.component';
import { AdminGuard } from './guard/admin.guard';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { UserPageComponent } from './user-page/user-page.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'admin-page',
    canActivate: [AdminGuard],
    children: [
      {
        path: '', 
        component: AdminPageComponent,
      },
      {
        path: 'devices',
        component: DeviceListComponent,
      }, 
      {
        path: 'users',
        component:UserListComponent,
      }
    ]
  },
  {
    path: 'user-page',
    canActivate: [AdminGuard],
    component: UserPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

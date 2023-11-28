import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserDetailsDTO } from 'src/app/dtos/user-details-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private getUser = "http://localhost:8080/user/getAllUsers";
  private createUserURL = "http://localhost:8080/user/createUser";


  constructor(private httpClient: HttpClient) {}

  getUserList(): Observable<UserDetailsDTO[]> {
    
    const storedToken = localStorage.getItem('token');

    if (storedToken) {
      
      const headers = new HttpHeaders({
        Authorization: `Bearer ${storedToken}`
      });

      
      return this.httpClient.get<UserDetailsDTO[]>(this.getUser, { headers });
    } else {
      
      return new Observable<UserDetailsDTO[]>();
    }
  }

  createUser(userDTO: UserDetailsDTO): Observable<any> {
    userDTO.userRole = userDTO.userRole.toUpperCase();

    
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`
    });

    
    return this.httpClient.post(this.createUserURL, userDTO, { headers });
  }


  deleteUser(userId: string): Observable<any> {
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`
    });
  
    const deleteUserURL = `http://localhost:8080/user/deleteUser/${userId}`;
  
    return this.httpClient.delete(deleteUserURL, { headers });
  }


  updateUser(userId: string,userDTO:UserDetailsDTO): Observable<any> {
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`
    });
  
   
    const putUserURL = `http://localhost:8080/user/update/${userId}`;
  
    return this.httpClient.put(putUserURL,userDTO, { headers });
  }


  getUserByUsername(username:string): Observable<any>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`
    });
  
   
    const getUserURL = `http://localhost:8080/user/getUserByUser/${username}`;
  
    return this.httpClient.get(getUserURL, { headers });
  }


  getUserByID(userId: string): Observable<any> {
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`
    });
  
    const getUserURL = `http://localhost:8080/user/getById/${userId}`;
  
    return this.httpClient.get(getUserURL, { headers });
  }
}

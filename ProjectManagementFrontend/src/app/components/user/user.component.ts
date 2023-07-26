import {  Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { IUser } from 'src/app/interface/task.interface';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SharedDataService } from 'src/app/services/sharedData/shared-data.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  @Output() refreshEmitter = new EventEmitter<boolean>();

  users!: IUser[];
  constructor(
    private userService: UserService, 
    public dialog: MatDialog,
    private sharedDataService: SharedDataService,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService) {}

  ngOnInit(): void {
    this.getUser();
  }

  getUser() {this.userService.getUserDetails().subscribe(data => {
      this.users = data;
    });
  }
  logOut() {
    this.authService.logout();
    this.sharedDataService.setUserDetails('');
    // this.sharedDataService.setUserRole('');
    this.toastr.info('You are logged out', 'Log out successful');
    this.router.navigate(['/home']);
  }


  // onOpenDialog(user: IUser) {
  //   const dialogRef = this.dialog.open(UserComponent, {
  //     data: user,
  //   });

  //   dialogRef.afterClosed().subscribe((result) => {
  //     this.getUser();
  //     this.refreshEmitter.emit(true);
  //   });
  // }
}

import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  @ViewChild("username") username: string;
  @ViewChild("password") password: string;

  constructor(private router: Router, private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  onLogin() {
    console.log(this.username['nativeElement'].value, this.password['nativeElement'].value);

    this.authService.login(this.username['nativeElement'].value, this.password['nativeElement'].value);

    this.router.navigate(['/layout']).then(r => console.log('Navigating to home'));
  }

}

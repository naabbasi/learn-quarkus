import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    console.log("user logged-in", this.authService.isUserLoggedIn())
  }

  logout() {
    this.authService.logoutUser();
    console.log("user logged-in", this.authService.isUserLoggedIn())
    window.location.href = '/';
  }
}

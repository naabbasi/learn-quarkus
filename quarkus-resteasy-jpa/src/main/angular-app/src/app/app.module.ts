import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MaterialModule} from "./MaterialModule";
import {RouterModule} from "@angular/router";

import {CanActivateTeam, UserToken, Permissions} from "./auth/auth.guard";
import {LoginComponent} from "./components/login/login.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";

@NgModule({
  declarations: [
    AppComponent, LoginComponent, PageNotFoundComponent
  ],
  imports: [
      BrowserModule,
      RouterModule,
      AppRoutingModule,
      BrowserAnimationsModule,
      MaterialModule
  ],
  providers: [CanActivateTeam, UserToken, Permissions],
  bootstrap: [AppComponent]
})
export class AppModule { }

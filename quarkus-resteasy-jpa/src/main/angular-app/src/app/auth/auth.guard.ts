import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "../services/auth.service";

export class UserToken {
  username: string = 'noman.ali.abbasi'
}

@Injectable()
export class Permissions {
  constructor(private authService: AuthService) {
  }

  canActivate(user: UserToken, id?: string): boolean {
    return this.authService.isUserLoggedIn();
  }
}

@Injectable({providedIn: "root"})
export class CanActivateTeam implements CanActivate {
  constructor(private permissions: Permissions, private currentUser: UserToken) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
    return this.permissions.canActivate(this.currentUser, route.params.id);
  }
}

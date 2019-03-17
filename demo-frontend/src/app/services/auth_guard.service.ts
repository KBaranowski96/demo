import {Injectable} from '@angular/core';
import {UserInfoService} from './user-info.service';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {LoginService} from "./login.service";

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private router: Router,
              private loginService: LoginService,
              private userInfoService: UserInfoService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let url: string = state.url;
    return this.checkLogin(url);
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.canActivate(route, state);
  }

  checkLogin(url: string): boolean {
    if (this.userInfoService.isLoggedIn()) {
      return true;
    }
    console.log("User is not logged - This routing guard prevents redirection to any routes that needs logging.");
    this.loginService.landingPage = url;
    this.router.navigate(['home']);
    return false;
  }
}

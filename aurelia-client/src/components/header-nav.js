import {Router} from 'aurelia-router';

export class HeaderNav {
  static inject() { return [Router]; }

  constructor(router){
    this.router = router;
  }

  aboutPage() {
  	 this.router.navigate("about");
  }

  logout() {
  	 window.localStorage.removeItem("X-AUTH-TOKEN");
  	 //this.router.navigate("welcome");
  	 window.location.replace(window.location.protocol + "//" + window.location.host + "/login.html");
  }

}

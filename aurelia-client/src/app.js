import 'bootstrap';
import 'bootstrap/css/bootstrap.css!';

import {Redirect} from 'aurelia-router';

export class App {


  configureRouter(config, router){
    config.title = 'Aurelia';
    config.addPipelineStep('authorize', AuthorizeStep); // Add a route filter to the authorize extensibility point.
    config.map([

      { route: ['','welcome'],  moduleId: './pages/welcome',      nav: true, title:'Welcome', settings: {icon:'fa-tachometer' } },
      
      { route: 'login',        moduleId: './pages/login',       nav: false, settings: { icon:'fa-sign-in' } },
      { route: 'child-router',  moduleId: './pages/child-router', nav: true, title:'Child Router', settings: { icon:'fa-cube' } },
      { route: 'userlist',      moduleId: './pages/authuser/authUserList', nav: true, title:'Liste utilisateurs', settings: { icon:'fa-users' }, auth: true },
      { route: 'useradd/:id',   moduleId: './pages/authuser/authUserAdd', name: "useradd", auth: true },
      { route: 'passwordedit/:id',   moduleId: './pages/authuser/passwordEdit', name: "passwordedit", auth: true },
      { route: 'about',   moduleId: './pages/about/about', name: "about", auth: true, nav:false },
      { route: 'licence',   moduleId: './pages/about/licence', name: "licence", auth: true, nav:false }
      
    ]);

    this.router = router;
  }
}

class AuthorizeStep {
  run(routingContext, next) {
    // Check if the route has an "auth" key
    // The reason for using `nextInstructions` is because
    // this includes child routes.
    if (routingContext.nextInstructions.some(i => i.config.auth)) {
      var isLoggedIn = window.localStorage.getItem("X-AUTH-TOKEN") != null;
        
      if (!isLoggedIn) {
        window.location.replace(window.location.protocol + "//" + window.location.host + "/login.html");
        //return next.cancel(new Redirect('login'));
      }
    }

    return next();
  }
}
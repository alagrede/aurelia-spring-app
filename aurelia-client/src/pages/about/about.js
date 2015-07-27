//import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';
//import {HttpClient} from 'aurelia-http-client';

export class About {

  static inject() { return [Router]; }

  constructor(router){
    this.router = router;
  }

  licencePage() {
  	this.router.navigate("licence");
  }

}
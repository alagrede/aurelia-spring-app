import {Router} from 'aurelia-router';
import {WebAPI} from '../tools/web-api';
import {Util} from '../tools/util';

export class Login {

  static inject() { return [Router, WebAPI, Util]; }

  constructor(router, http, util){
    this.router = router;
    this.http = http;
    this.util = util;
  }

  url = "login";
  loginError = "";

  credentials = {
    "username": "",
    "password": ""
  }


  login(){

    return this.http.post(this.url, this.credentials).then(response => {
		  //localStorage.setItem("X-AUTH-TOKEN", response.getResponseHeader("X-AUTH-TOKEN"));
      window.localStorage.setItem("X-AUTH-TOKEN", response.response);//FIXME : Aurelia ne sait pas lire les headers de la response.                                                          // A défaut on lit le retour directement dans le contenu de la réponse
      this.router.navigate("welcome");

    }).catch(error => {
      console.log(error.statusCode + ":" + error.response);
      this.loginError = error.response;
    });
              

  }



}
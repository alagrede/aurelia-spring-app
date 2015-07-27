//import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';
//import {HttpClient} from 'aurelia-http-client';
import {WebAPI} from '../../tools/web-api';
import {Util} from '../../tools/util';

export class Licence {

  static inject() { return [Router, WebAPI, Util]; }

  html = "";
  aboutUrl = "about/licences";

  constructor(router, http, util){
    this.router = router;
    this.http = http;
    this.util = util;
    this.loadLicencePage();
  }

  loadLicencePage() {

  	return this.http.get(this.aboutUrl).then(response => {
        
        this.html = response.content;

    }).catch(response => {
      	console.log(response.content);
    });              
  }

}
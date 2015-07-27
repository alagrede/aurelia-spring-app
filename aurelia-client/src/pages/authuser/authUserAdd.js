import {Router} from 'aurelia-router';
import {WebAPI} from '../../tools/web-api';
import {Util} from '../../tools/util';


export class AuthUserAdd{

  static inject() { return [Router, WebAPI, Util]; }


  heading = 'Ajouter un utilisateur';
  addUserUrl = 'authuser/add';
  editurl = 'authuser/edit/';
  groupscomboUrl = "authuser/groupscombo";
  permissionscomboUrl = "authuser/permissionscombo";

  
  constructor(router, http, util){
    this.router = router;
    this.http = http;
    this.util = util;
  }


  permissionscombo = [];
  groupscombo = [];

  password = "";

  user = {
    "id": "",
    "username": "",
    "password": "",
    "lastLogin": "",
    "firstname": "",
    "lastname": "",
    "email": "",
    "active": true,
    "staff": false,
    "superUser": false,
    "dateJoined": ""
  }


  activate(params, config){

    // Récupération du user existant (pour modification)
    if (params.id >= 0) {
      this.command.userId = params.id;
      
      this.http.get(this.editurl + params.id).then(response => {
          this.user = response.content;
          this.password = response.content.password;
      });                    
    } 

    //chargement des combos
    this.http.get(this.groupscomboUrl).then(response => {
        this.groupscombo = response.content;
    });                    
    this.http.get(this.permissionscomboUrl).then(response => {
      this.permissionscombo = response.content;
    });                    
 

  }


  addUser(){
    this.user.password = this.password;
    return this.http.post(this.addUserUrl, this.user).then(response => {
        
        this.util.removeErrors();
        this.router.navigate("userlist");

      }).catch(response => {
        this.util.removeErrors();
        this.util.displayErrors(response.content);
      });              

  }


 /* Modification du mot de passe */

  headingModal = 'Modifier le mot de passe';
  editPasswordUrl = 'authuser/changePassword';

  command = {
    userId: "",
    password: "",
    confirmPassword:""
  }

  showPopupPassword() {
    $('#editPasswordModal').modal('show');
  }

  editPassword() {

      return this.http.post(this.editPasswordUrl, this.command).then(response => {
          
          this.util.removeErrors();
          $('#editPasswordModal').modal('hide');

      }).catch(response => {
        this.util.removeErrors();
        this.util.displayErrors(response.content);
      });              

  }


}

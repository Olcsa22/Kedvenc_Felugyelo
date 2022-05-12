
export class User{

    public id?: number;
    public username?: string;
    public email ?: string;
    public password?: string;
    public roles ?: String[];
    public register ?: Date;
    public authStatus?: string;
  

    constructor(id?: number,name?: string, email?: string,  password?: string,role?: String[], register?:Date, authStatus?:string){
          this.id = id;
          this.username = name;
          this.email = email;
          this.password = password;
          this.roles = role;
          this.register = register;
          this.authStatus=authStatus;
    }
  
  }
  
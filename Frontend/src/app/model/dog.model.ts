
export class Dog{

    public id?: number;
    public name?: string;
    public lastFeeding?: Date;
    public feedingInterval ?: number;
    public owner ?: number;
    public indoor?: boolean;
    public vaccinated?: Date;
    public lastWalking?: Date;
    public needs?: String[];
    public url?:String;
  

    constructor(id?: number, name?: string, lastFeeding?: Date, feedingInterval ?: number, owner ?: number, indoor ?: boolean, 
        vaccinated ?: Date, lastWalking ?: Date){
          this.id=id;
          this.name=name;
          this.lastFeeding=lastFeeding;
          this.feedingInterval=feedingInterval;
          this.owner=owner;
          this.indoor=indoor;
          this.vaccinated=vaccinated;
          this.lastWalking=lastWalking;
    }
  
  }
  